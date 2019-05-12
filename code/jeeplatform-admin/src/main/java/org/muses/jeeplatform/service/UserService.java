package org.muses.jeeplatform.service;

import org.muses.jeeplatform.core.dao.repository.admin.UserRepository;
import org.muses.jeeplatform.core.entity.admin.Operation;
import org.muses.jeeplatform.core.entity.admin.Permission;
import org.muses.jeeplatform.core.entity.admin.Role;
import org.muses.jeeplatform.core.entity.admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	 */
	public int saveIP(Map<String,String> map) {
		String loginIp = map.get("loginIp");
		String username = map.get("username");
		int code = userRepository.updateLoginIpById(loginIp,username);
		return code;
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
	//@RedisCache(nameSpace = RedisCacheNamespace.SYS_USER)
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
	//@RedisCache(nameSpace = RedisCacheNamespace.SYS_USER)
	//@RedisCache
	public User doLoginCheck(String username,String password){
		return userRepository.findByUsernameAndPassword(username,password);
	}

	/**
	 * 根据用户序号查询用户信息
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	//@RedisCache(nameSpace = RedisCacheNamespace.SYS_USER)
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
	 * 获取所有的用户信息并分页显示
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

	/**
	 * 根据关键字和日期查询
	 * @param pageNo
	 * 			当前页面数
	 * @param pageSize
	 * 			每一页面的页数
	 * @param dir
	 * @param str
	 * @param keyword
	 * 			关键字
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Transactional
	public Page<User> searchU(int pageNo, int pageSize, Sort.Direction dir, String str,String keyword,Date startDate,Date endDate){
		PageRequest request = buildPageRequest(pageNo, pageSize, dir, str);
		Page<User> users = userRepository.searchU(startDate,endDate,request);
		return users;
	}

	/**
	 * 保存用户信息
	 * @param user
	 */
	public void saveU(User user){
		userRepository.save(user);
	}

	/**
	 * 更新用户信息
	 * @param user
	 */
	public int updateU(User user) {
		return userRepository.updatePasswordById(user.getPassword(),user.getUsername());
	}

}
