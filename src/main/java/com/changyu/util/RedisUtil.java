package com.changyu.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    // 指定缓存失效时间
    public boolean expire(String key, long time) {
        try {
            if(time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 判断缓存是否存在
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除缓存
    public void del(String key) {
        if (key != null) {
                redisTemplate.delete(key);
        }
    }

    // 获取缓存
    public Object get(String key) {return key == null ? null : redisTemplate.opsForValue().get(key); }

    // 放入缓存
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 放入缓存并设置时间
    public boolean set(String key, Object value, long time) {
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
