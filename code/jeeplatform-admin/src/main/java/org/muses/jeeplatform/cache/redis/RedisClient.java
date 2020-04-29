package org.muses.jeeplatform.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Component
public class RedisClient {

    @Autowired
    private JedisPool jedisPool;

    public void setValue(String key, String value)throws Exception{
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(key,value);
        }finally {
            jedis.close();
        }
    }

    public String getValue(String key)throws Exception{
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }finally {
            jedis.close();
        }
    }


}
