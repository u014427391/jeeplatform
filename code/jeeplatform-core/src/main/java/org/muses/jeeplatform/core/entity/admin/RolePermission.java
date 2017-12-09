package org.muses.jeeplatform.core.entity.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色权限实体类
 * Created by Nicky on 2017/11/4.
 */
@Table(name="sys_role_permission")
@Entity
public class RolePermission implements Serializable{

    //表Id
    private String rpId;

    //角色Id
    private int roleId;

    //权限Id
    private int permissionId;

    @Id
    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    @Column(length = 11,nullable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(length = 11,nullable = false)
    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

}
