package com.rabo.customerservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddressModel {

	@JsonIgnore
	private int id;	
	private String street;	
	private String city;	
	private String zipCode;	
	private String Country;

	public AddressModel(int id, String street, String city, String zipCode, String country) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		Country = country;
	}

	public AddressModel(String street, String city, String zipCode, String country) {
		super();
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		Country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", zipCode=" + zipCode + ", Country="
				+ Country + "]";
	}

	public AddressModel() {

		super();
	}
}
