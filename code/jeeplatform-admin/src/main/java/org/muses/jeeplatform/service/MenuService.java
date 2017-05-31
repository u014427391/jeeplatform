package org.muses.jeeplatform.service;

import java.util.List;

import org.muses.jeeplatform.model.entity.Menu;
import org.muses.jeeplatform.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
	
	@Autowired
	MenuRepository menuRepository;
	
	/**
	 * 查询所有的菜单
	 * @return
	 */
	@Transactional
	public List<Menu> findAll(){
		return menuRepository.findAll();
	}
	
	/**
	 * 获取所有的上级菜单
	 * @return
	 */
	@Transactional
	public List<Menu> findAllParentMenu(){
		return menuRepository.findAllParentMenu();
	}
	
	/**
	 * 通过上级Id获取二级菜单
	 * @param id
	 * @return
	 */
	@Transactional
	public List<Menu> findSubMenuById(int id){
		return menuRepository.findSubMenuByParentId(id);
	}

}
