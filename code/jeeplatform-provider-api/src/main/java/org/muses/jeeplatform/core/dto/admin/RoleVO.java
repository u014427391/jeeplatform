package org.muses.jeeplatform.core.dto.admin;

import java.io.Serializable;

/**
 * Created by Nicky on 2017/12/2.
 */
public class RoleVO implements Serializable{

    /** 角色Id**/
    private int roleId;

    /** 角色描述**/
    private String roleDesc;

    /** 角色名称**/
    private String roleName;

    private boolean checked;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
