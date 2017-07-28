package org.muses.jeeplatform.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.muses.jeeplatform.model.entity.Operation;
import org.muses.jeeplatform.model.entity.Permission;
import org.muses.jeeplatform.model.entity.Role;
import org.muses.jeeplatform.model.entity.User;
import org.muses.jeeplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description 用户信息管理的业务类
 * @author Nicky 
 * @date 2017年3月6日
 */
@Service
public class UserService {

	@Autowired
    UserRepository userRepository;
	
	/**
	 * 存储登录时的ip
	 * @param map
	 */
	public void saveIP(Map<String, String> map) {
		
	}
	
	
	/**
	 * 获取用户角色
	 * @param username
	 * @return
	 */
	public Set<String> getRoles(String username){
		User user = userRepository.findByUsername(username);
		Set<Role> roles = user.getRoles();
		//创建一个HashSet来存放用户角色信息
		Set<String> roleStrs = new HashSet<String>();
		for(Role r:roles){
			roleStrs.add(r.getRole());
		}
		return roleStrs;
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public Set<String> getPermissions(String username){
		User user = userRepository.findByUsername(username);
		Set<Role> roles = user.getRoles();
		//创建一个HashSet来存放角色权限信息
		Set<String> permissionStrs = new HashSet<String>();
		for(Role r:roles){
			for(Permission p:r.getPermissions())
				for(Operation ope:p.getOperations()){
					permissionStrs.add(ope.getOperation());
				}
		}
		return permissionStrs;
	}
	
	@Transactional(readOnly=true)
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	@Transactional(readOnly=true)
	public User doLoginCheck(String username,String password){
		return userRepository.findByUsernameAndPassword(username,password);
	}

	/**
	 * 构建PageRequest对象
	 * @param num
	 * @param size
	 * @param asc
	 * @param string
	 * @return
	 */
	private PageRequest buildPageRequest(int num, int size, Sort.Direction asc,
										 String string) {
		return new PageRequest(num-1, size,null,string);
	}

	//@Transactional(readOnly = true)


}
