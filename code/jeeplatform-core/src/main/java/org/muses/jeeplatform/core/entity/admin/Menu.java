package org.muses.jeeplatform.core.entity.admin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
	
	/** 上级Id**/
	private int parentId;
	
	/** 菜单名称**/
	private String menuName;
	
	/** 菜单图标**/
	private String menuIcon;
	
	/** 菜单URL**/
	private String menuUrl;
	
	/** 菜单类型**/
	private String menuType;
	
	/** 菜单排序**/
	private String menuOrder;

	/**菜单状态**/
	private String menuStatus;

	private List<Menu> subMenu;

	private String target;

	private boolean hasSubMenu = false;

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
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(length=100)
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	@Column(length=10)
	public String getMenuStatus(){
		return menuStatus;
	}

	public void setMenuStatus(String menuStatus){
		this.menuStatus = menuStatus;
	}

	@Transient
	public List<Menu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}

	public void setTarget(String target){
		this.target = target;
	}

	@Transient
	public String getTarget(){
		return target;
	}

	public void setHasSubMenu(boolean hasSubMenu){
		this.hasSubMenu = hasSubMenu;
	}

	@Transient
	public boolean getHasSubMenu(){
		return hasSubMenu;
	}

}
