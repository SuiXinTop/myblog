package com.spring.blog.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author STARS
 */
@Slf4j
@Component
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Get object.
     *
     * @param key the key
     * @return the object
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * Set boolean.
     *
     * @param key   the key
     * @param value the value
     * @return the boolean
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Sets .
     *
     * @param key    the key
     * @param value  the value
     * @param expire Expire
     * @return the
     */
    public boolean setExpire(String key, Object value, long expire) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Expire boolean.
     *
     * @param key    the key
     * @param expire Expire
     * @return the boolean
     */
    public boolean expire(String key, long expire) {
        try {//这儿没有ops什么的是因为每种数据类型都能设置过期时间
            redisTemplate.expire(key, expire, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Ttl long.
     *
     * @param key key
     * @return 获取key的过期时间 long
     */
    public long selectExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * Del.
     *
     * @param keys 删除key 可变参数
     */
    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(keys));
        }
    }

    /**
     * Incr by long.
     *
     * @param key  key
     * @param step 传入正数 就是加多少 传入负数就是减多少
     * @return int long
     */
    public long incrBy(String key, long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * Sets .
     *
     * @param key   key
     * @param value value
     * @return 如果该key存在就返回false 设置不成功 key不存在就返回ture设置成功
     */
    public boolean setNx(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
    }

    /**
     * 原子操作
     *
     * @param key    the key
     * @param value  the value
     * @param expire 在上面方法加上过期时间设置
     * @return and expire
     */
    public boolean setNxAndExpire(String key, Object value, long expire) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS));
    }

    /**
     * Gets and set.
     *
     * @param key   the key
     * @param value the value
     * @return 如果该key存在就返回之前的value 不存在就返回null
     */
    public Object getAndSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * Has key boolean.
     *
     * @param key the key
     * @return 判断key是否存在 boolean
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
