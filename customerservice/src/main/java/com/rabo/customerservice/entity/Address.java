package com.rabo.customerservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.rabo.customerservice.util.ErrorCodes;

@Entity
@Table(name = "ADDRESS")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "add_id", unique = true)	
	private Long id;
	private String street;	
	
	@NotBlank(message = ErrorCodes.CITY_MANDATORY)
	private String city;	
	
	@NotBlank(message = ErrorCodes.ZIPCODE_MANDATORY)	
	private String zipCode;
	
	@NotBlank(message = ErrorCodes.COUNTRY_MANDATORY)	
	private String Country;

	public Address(Long id, String street, String city, String zipCode, String country) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		Country = country;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Address() {

		super();
	}
}
