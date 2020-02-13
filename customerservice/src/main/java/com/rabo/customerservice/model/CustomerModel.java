package com.rabo.customerservice.model;

public class CustomerModel {

	private Long custId;
	private String fname;
	private String lname;
	private Integer age;

	private AddressModel address;

	public CustomerModel() {
		super();
	}

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

	public AddressModel getAddress() {
		return address;
	}

	public void setAddress(AddressModel address) {
		this.address = address;
	}

	public CustomerModel(Long custId, String fname, String lname, Integer age, AddressModel address) {
		super();
		this.custId = custId;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.address = address;
	}

}