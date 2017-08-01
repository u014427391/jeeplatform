package org.muses.jeeplatform.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.muses.jeeplatform.model.entity.Operation;
import org.muses.jeeplatform.model.entity.Permission;
import org.muses.jeeplatform.model.entity.Role;
import org.muses.jeeplatform.model.entity.User;
import org.muses.jeeplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
		//待开发...
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

	/**
	 * 通过用户名查询用户信息
	 * @param username
	 * @return
	 */
	@Transactional(readOnly=true)
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}

	/**
	 * 登录验证
	 * @param username
	 * @param password
	 * @return
	 */
	@Transactional(readOnly=true)
	public User doLoginCheck(String username,String password){
		return userRepository.findByUsernameAndPassword(username,password);
	}

	/**
	 * 根据用户序号查询用户信息
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public User findByUId(int id){
		return userRepository.findById(id);
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

	/**
	 * 获取所有的菜单信息并分页显示
	 * @param pageNo
	 * 			当前页面数
	 * @param pageSize
	 * 			每一页面的页数
	 * @return
	 */
	@Transactional
	public Page<User> findAll(int pageNo, int pageSize, Sort.Direction dir, String str){
		PageRequest request = buildPageRequest(pageNo, pageSize, dir, str);
		Page<User> users = userRepository.findAll(request);
		return users;
	}

}
