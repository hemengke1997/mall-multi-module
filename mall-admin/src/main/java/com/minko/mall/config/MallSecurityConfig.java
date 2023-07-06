package com.minko.mall.config;

import com.minko.mall.model.UmsResource;
import com.minko.mall.security.component.DynamicSecurityService;
import com.minko.mall.service.UmsAdminService;
import com.minko.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MallSecurityConfig {
    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private UmsResourceService umsResourceService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> umsAdminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> umsResourceList = umsResourceService.list();
                for (UmsResource umsResource : umsResourceList) {
                    map.put(umsResource.getUrl(), new SecurityConfig(umsResource.getId() + ":" + umsResource.getName()));
                }
                return map;
            }
        };
    }
}
