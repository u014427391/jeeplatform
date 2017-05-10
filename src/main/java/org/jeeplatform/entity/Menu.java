package org.jeeplatform.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @description 菜单信息实体
 * @author Nicky
 * @date 2017年3月17日
 */
@Table(name="sys_menu")
@Entity
public class Menu implements Serializable {

	/** 菜单Id**/
	private int menuId;
	
	/** 标志**/
	private String identity;
	
	/** 上级Id**/
	private int parentId;
	
	/** 菜单名称**/
	private String name;
	
	/** 菜单图标**/
	private String menuIcon;
	
	/** 菜单URL**/
	private String menuUrl;
	
	/** 菜单类型**/
	private String menuType;
	
	/** 菜单排序**/
	private String menuOrder;
	
	private String target;
	
	private Menu parentMenu;
	
	private List<Menu> subMenu;
	
	private boolean hasMenu = false;
	
	private static final long serialVersionUID = 1L;

	public Menu() {
		super();
	}   
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getMenuId() {
		return this.menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}   
	
	@Column(length=100)
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Column(length=100)
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(length=100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	
	@Column(length=30)
	public String getMenuIcon() {
		return this.menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}   
	
	@Column(length=100)
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}   
	
	@Column(length=100)
	public String getMenuType() {
		return this.menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	@Column(length=10)
	public String getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}

	@Transient
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Transient
	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	@Transient
	public List<Menu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}

	@Transient
	public boolean isHasMenu() {
		return hasMenu;
	}

	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
   
}
