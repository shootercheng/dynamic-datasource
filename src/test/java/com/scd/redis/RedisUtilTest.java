package com.scd.redis;

import com.scd.util.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author James
 */
public class RedisUtilTest {

    @Test
    public void printAllKeyValue() {
        Jedis jedis = JedisUtil.getJedis();
        Set<String> keySet = jedis.keys("*");
        for (String key : keySet) {
            String type = jedis.type(key);
            if ("string".equals(type)) {
                System.out.println(key + " -> " + jedis.get(key));
            } else if ("set".equals(type)) {
                System.out.println(key + " -> " + jedis.smembers(key));
            } else if ("list".equals(type)) {
                System.out.println(key + " -> " + jedis.lrange(key, 0, Long.MAX_VALUE));
            } else if ("hash".equals(type)) {
                System.out.println(key + " -> " + jedis.hgetAll(key));
            } else {
                System.out.println(type);
            }
        }
    }
}
