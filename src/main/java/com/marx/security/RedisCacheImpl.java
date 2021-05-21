package com.marx.security;

import com.marx.util.IocUtils;
import com.wobangkj.cache.Cacheables;
import com.wobangkj.cache.Timing;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class RedisCacheImpl implements Cacheables {
    private final StringRedisTemplate redisTemplate;

    public RedisCacheImpl(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }

    public ValueWrapper get(Object key) {
        return new SimpleValueWrapper(this.redisTemplate.opsForValue().get(key));
    }

    public void set(Object key, Object value, Timing timing) {
        this.redisTemplate.opsForValue().set(key.toString(), value.toString(), timing.getTime(), timing.getUnit());
    }

    public void del(Object key) {
        this.redisTemplate.delete(key.toString());
    }

    public void clear() {
        Set<String> keys = this.redisTemplate.keys("*");
        if (!Objects.isNull(keys) && !keys.isEmpty()) {
            this.redisTemplate.delete(keys);
        }
    }

    public Object getNativeCache() {
        return this.redisTemplate;
    }

    public String getName() {
        return "string_redis_cache";
    }
}
