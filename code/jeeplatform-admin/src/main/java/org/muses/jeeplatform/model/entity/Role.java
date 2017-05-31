package org.muses.jeeplatform.model.entity;

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
	private String desc;
	 
	/** 角色名称**/
	private String name;
	
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
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length=100)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@OneToMany(targetEntity=Permission.class,cascade=CascadeType.MERGE,fetch=FetchType.EAGER)
	@JoinTable(name="sys_role_permission", joinColumns=@JoinColumn(name="roleId",referencedColumnName="roleId"), inverseJoinColumns=@JoinColumn(name="permissionId",referencedColumnName="id",unique=true))
	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
