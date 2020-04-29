package org.muses.jeeplatform.core.entity.admin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 角色信息实体类
 * @author Nicky
 * @date 2017年3月16日
 */
@Table(name="sys_role")
@Entity
public class Role implements Serializable{

	/** 角色Id**/
	private int roleId;

	/** 角色描述**/
	private String roleDesc;

	/** 角色名称**/
	private String roleName;

	/** 角色标志**/
	private String role;

	private Set<Permission> permissions = new HashSet<Permission>();

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	@Column(length=100)
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(length=100)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(length=100)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	//修改cascade策略为级联关系
	@OneToMany(targetEntity=Permission.class,cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinTable(name="sys_role_permission", joinColumns=@JoinColumn(name="roleId",referencedColumnName="roleId"), inverseJoinColumns=@JoinColumn(name="permissionId",referencedColumnName="id",unique=true))
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role) {
			Role role = (Role) obj;
			return this.roleId==(role.getRoleId())
					&& this.roleName.equals(role.getRoleName())
					&& this.roleDesc.equals(role.getRoleDesc())
					&& this.role.equals(role.getRole());
		}
		return super.equals(obj);
	}
}
