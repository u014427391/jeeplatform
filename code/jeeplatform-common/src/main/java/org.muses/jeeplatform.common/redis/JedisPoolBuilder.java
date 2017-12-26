package org.muses.jeeplatform.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Nicky on 2017/12/25.
 */
public class JedisPoolBuilder {

    @Autowired
    private static JedisPool jedisPool;

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void release(Jedis jedis) {
        jedisPool.returnBrokenResource(jedis);

    }

}
