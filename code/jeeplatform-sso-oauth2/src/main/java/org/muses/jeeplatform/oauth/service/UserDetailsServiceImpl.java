package org.muses.jeeplatform.oauth.service;

import lombok.extern.slf4j.Slf4j;
import org.muses.jeeplatform.oauth.dto.UserDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * <pre>
 * @author mazq
 * 修改记录
 *    修改后版本:     修改人：  修改日期: 2020/04/30 15:15  修改内容:
 * </pre>
 */
@Slf4j
@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = new UserDto();
//        if(user == null){
//            log.info("登录用户[{}]没注册!",username);
//            throw new UsernameNotFoundException("登录用户["+username + "]没注册!");
//        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        //return Arrays.asList(new SimpleGrantedAuthority("admin"));
        return Arrays.asList(Collections.emptyList());
    }
}
