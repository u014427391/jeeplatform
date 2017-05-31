package org.muses.jeeplatform.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Operation
 *
 */
@Table(name="sys_operation")
@Entity
public class Operation implements Serializable {

	
	private int id;
	private String desc;
	private String name;
	private String operation;
	private static final long serialVersionUID = 1L;

	public Operation() {
		super();
	}   
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}   
	
	@Column(unique=true)
	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
   
}
