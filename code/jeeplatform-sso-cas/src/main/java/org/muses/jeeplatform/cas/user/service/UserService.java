package org.muses.jeeplatform.cas.user.service;

import org.muses.jeeplatform.cas.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/26 15:26  修改内容:
 * </pre>
 */
@Service
public class UserService {

    @Autowired
    @Qualifier("jdbcTemplate")
    JdbcTemplate jdbcTemplate;


    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    @Transactional(readOnly=true)
    //@RedisCache(nameSpace = RedisCacheNamespace.SYS_USER)
    public User findByUsername(String username){
        String sql = "SELECT * FROM sys_user WHERE username = ?";
        User info = null;
        try {
            info = (User) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(User.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

}
