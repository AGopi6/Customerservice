package com.rabo.customerservice.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rabo.customerservice.util.ErrorCodes;

@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Min(1)
	private Long custId;

	@NotBlank(message = ErrorCodes.FNAME_MANDATORY)
	private String fname;

	@NotBlank(message = ErrorCodes.LNAME_MANDATORY)
	private String lname;

	@NotNull(message = ErrorCodes.AGE_MANDATORY)
	@Min(1)
	@Max(99)
	private Integer age;

	@Version
	private Long version;

	@OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "livingAddress_id", referencedColumnName = "add_id")
	@NotNull(message = ErrorCodes.ADDRESS_MANDATORY)
	private Address address;

	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Customer(Long custId, String fname, String lname, Integer age, Address address) {
		super();
		this.custId = custId;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.address = address;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

}