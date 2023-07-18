package com.minko.mall.common.service;

import java.util.List;

public interface RedisService {
    void set(String key, Object value, long time);

    void set(String key, Object value);

    Object get(String key);

    Boolean del(String key);

    Long del(List<String> keys);

    void delAll();

    Boolean expire(String key, long time);

    /**
     * 按delta递增
     */
    Long incr(String key, long delta);
}
