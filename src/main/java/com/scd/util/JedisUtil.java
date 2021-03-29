package com.scd.util;

import com.scd.cache.KryoSerializer;
import io.swagger.models.auth.In;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.ibatis.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Properties;

/**
 * @author James
 */
public class JedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisUtil.class);

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        try {
            Properties properties = Resources.getResourceAsProperties("templates/db.properties");
            String host = properties.getProperty("redis.host");
            int port = Integer.parseInt(properties.getProperty("redis.port"));
            int timeout = Integer.parseInt(properties.getProperty("redis.timeout"));
            String password = properties.getProperty("redis.password");
            int database = Integer.parseInt(properties.getProperty("redis.database"));
            jedisPool = new JedisPool(poolConfig, host, port, timeout, password, database);
        } catch (IOException e) {
            LOGGER.info("init thread pool error ", e);
        }
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void set(Object key, Object value) {
        try (Jedis jedis = getJedis()) {
            jedis.set(KryoSerializer.serialize(key), KryoSerializer.serialize(value));
        }
    }

    public static Object get(Object key) {
        try (Jedis jedis = getJedis()) {
            byte[] bytes = jedis.get(KryoSerializer.serialize(key));
            if (bytes == null) {
                return null;
            }
            return KryoSerializer.unserialize(bytes);
        }
    }

    public static Object del(Object key) {
        try (Jedis jedis = getJedis()) {
            return jedis.del(KryoSerializer.serialize(key));
        }
    }
}
