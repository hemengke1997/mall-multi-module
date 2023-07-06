package com.minko.mall.common.util;

import cn.hutool.json.JSONUtil;
import com.minko.mall.common.api.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class ResponseUtil {
    public static void out(HttpServletResponse response, Result result) {
        PrintWriter out = null;
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            out = response.getWriter();
            out.println(JSONUtil.parse(result));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
