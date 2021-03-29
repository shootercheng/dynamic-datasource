package com.scd.cache;

import com.scd.util.JedisUtil;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author James
 */
public class MybatisRedisCache implements Cache {
    private static final Logger LOG = LoggerFactory.getLogger(MybatisRedisCache.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    private String id;


    public MybatisRedisCache(final String id){
        if(id == null){
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        LOG.info("Redis Cache id " + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        JedisUtil.set(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return JedisUtil.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return JedisUtil.del(key);
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}
