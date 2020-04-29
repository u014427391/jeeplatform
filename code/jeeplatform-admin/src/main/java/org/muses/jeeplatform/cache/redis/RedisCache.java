package org.muses.jeeplatform.cache.redis;

import org.muses.jeeplatform.cache.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
@Component("redisCache")
public class RedisCache {
	
	@Autowired
	private JedisPool jedisPool;
	
	private JedisPool getJedisPool(){
		return jedisPool;
	}
	
	public void setJedisPool(JedisPool jedisPool){
		this.jedisPool = jedisPool;
	}
	
	/**
	 * 从Redis缓存获取数据
	 * @param redisKey
	 * @return
	 */
	public Object getDataFromRedis(String redisKey){
		Jedis jedis = jedisPool.getResource();
		byte[] byteArray = jedis.get(redisKey.getBytes());
		
		if(byteArray != null){
			return SerializeUtil.unSerialize(byteArray);
		}
		return null;
	}
	
	/**
	 * 保存数据到Redis
	 * @param redisKey
	 */
	public String saveDataToRedis(String redisKey,Object obj){
		
		byte[] bytes = SerializeUtil.serialize(obj);
		
		Jedis jedis = jedisPool.getResource();
		
		String code = jedis.set(redisKey.getBytes(), bytes);
		
		return code;
	}
	

}
