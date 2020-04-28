package org.muses.jeeplatform.core.dto.admin;

import java.io.Serializable;

import java.util.List;

/**
 * @description 菜单信息实体
 * @author Nicky
 * @date 2017年3月17日
 */
public class SysMenu implements Serializable {

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

    private List<SysMenu> subMenu;

    public SysMenu() {
        super();
    }

    public int getMenuId() {
        return this.menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return this.menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuUrl() {
        return this.menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuType() {
        return this.menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(String menuOrder) {
        this.menuOrder = menuOrder;
    }

    public List<SysMenu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<SysMenu> subMenu) {
        this.subMenu = subMenu;
    }

}
