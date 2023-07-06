package com.minko.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务接口
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符对应的资源
     */
    Map<String, ConfigAttribute> loadDataSource();
}
