package org.muses.jeeplatform.core.entity.admin;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity implementation class for Entity: Operation
 *
 */
@Table(name="sys_operation")
@Entity
public class Operation implements Serializable {

	
	private int id;
	private String odesc;//修改，不能为desc命名，和数据库关键字命名一样
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

	public String getOdesc() {
		return odesc;
	}

	public void setOdesc(String odesc) {
		this.odesc = odesc;
	}

	public void setId(int id) {
		this.id = id;
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
