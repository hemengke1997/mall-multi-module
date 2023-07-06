package com.minko.mall.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    /**
     * 根据claims生成token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 根据账号密码生成token
     */
    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, user.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());

        return generateToken(claims);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从String token中获取paylold
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 从token中获取用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claimsFromToken = getClaimsFromToken(token);
            username = claimsFromToken.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * token是否过期
     */
    boolean isTokenExpired(String token) {
        Date expiredDateFromToken = getExpiredDateFromToken(token);
        return expiredDateFromToken.before(new Date());
    }

    /**
     * 验证token是否有效
     */
    public boolean valiateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String getTokenFromAuthorization(String authorization) {
        if (StrUtil.isEmpty(authorization) || authorization.length() < tokenHead.length()) {
            return null;
        }
        String token = authorization.substring(tokenHead.length());
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        return token;
    }

    /**
     * 刷新token（老token在有效期内才可以刷新）
     */
    public String refreshToken(String token) {
        if (StrUtil.isEmpty(token)) return null;

        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }

        if (isTokenExpired(token)) {
            return null;
        }

        // 如果token在30分钟内刚刷新过，返回老token
        if (tokenRefreshJustBefore(token, 30 * 60)) {
            return token;
        }

        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time))) {
            return true;
        }
        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        token = getTokenFromAuthorization(token);
        return token;
    }
}
