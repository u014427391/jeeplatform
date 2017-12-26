package org.muses.jeeplatform.common.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by Nicky on 2017/12/26.
 */
public class JedisPoolBuilder {
    public static Jedis getJedis() {
        return null;
    }

    public static void release(Jedis jedis) {
    }
}
