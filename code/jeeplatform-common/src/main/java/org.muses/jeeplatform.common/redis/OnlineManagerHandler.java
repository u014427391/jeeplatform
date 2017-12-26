package org.muses.jeeplatform.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OnlineManagerHandler {
        private final static Logger log = LoggerFactory.getLogger(OnlineManagerHandler.class);
        private static final String UID_PREFIX = "online:uid:";
        private static final int EXPIRE_TIME = 200; //设置200秒做测试,按照上述思路是设置成30分钟也就是1800秒
        /**
        * 用户登录时，记录当前用户ID对应的数据到redis里面
        * 此时 在线人数增一
        * @param userId 用户ID
        * @param username 用户名
        */
        public static void login(Integer userId, String username) {
            Jedis jedis = JedisPoolBuilder.getJedis();
            jedis.setex(UID_PREFIX + userId, EXPIRE_TIME, username);
            JedisPoolBuilder.release(jedis);
        }
        /**
        * 当用户退出时，删除其在redis里面存储的数据
        * 此时 在线人数-1
        * @param userId
        */
        public static void logout(Integer userId) {
            Jedis jedis = JedisPoolBuilder.getJedis();
            jedis.del(UID_PREFIX + userId);
            JedisPoolBuilder.release(jedis);
        }
        /**
        * 当用户进行相关操作时，重置用户的在线时长
        */
        public static void refreshOnlineRecord(Integer userId) {
            Jedis jedis = JedisPoolBuilder.getJedis();
            jedis.expire(UID_PREFIX + userId, EXPIRE_TIME);//重置用户的在线时长
            JedisPoolBuilder.release(jedis);
        }
        /**
        * 获取在线人数
        * @return
        */
        public static Integer countOnline() {
            Jedis jedis = JedisPoolBuilder.getJedis();
            Set online = jedis.keys(UID_PREFIX+"*");//keys如果查询不出来返回空的set,不会返回null
            return online.size();
        }
        /**
        * 获取在线用户的username结合
        * @return
        */
        public static List<String> getOnlineUsernames() {
            Jedis jedis = JedisPoolBuilder.getJedis();
            Set online = jedis.keys(UID_PREFIX+"*");//keys如果查询不出来返回空的set,不会返回null
            return new ArrayList<String>(online);
        }
}