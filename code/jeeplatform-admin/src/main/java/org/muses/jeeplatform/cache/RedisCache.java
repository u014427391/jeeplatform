package org.muses.jeeplatform.cache;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
	public void saveDataToRedis(String redisKey,Object obj){
		
		byte[] bytes = SerializeUtil.serialize(obj);
		
		Jedis jedis = jedisPool.getResource();
		
		String code = jedis.set(redisKey.getBytes(), bytes);
		
		if(code.equals("OK")){
			System.out.println("成功保存到数据库!");
		}
	}
	

}
