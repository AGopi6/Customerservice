package com.rabo.customerservice.util;

public interface ErrorCodes {

	static final String GET_CUSTOMER_EXCEPTION = "Customer infomation is not avaiable for the id = ";
	static final String GET_ALL_CUSTOMERS_NO_INFO_AVAILABLE = "No Customer information is available ";
	static final String UPDATE_ADDRESS_EXCEPTION = "No Customer info available to update for the given Address";
	static final String UPDATE_ADDRESS_ID_EXCEPTION = "Address Id is mandatory and should be valid as per Address table which is mapped with customer Id";
	static final String CUST_ID_MANDATORY ="Cust Id is mandatory and must be greater than or equal to 1";
	static final String FNAME_MANDATORY ="Fname name can't be blank";
	static final String LNAME_MANDATORY ="Lname name can't be blank";
	static final String AGE_MANDATORY ="Age can't be blank";
	static final String AGE_MANDATORY_MIN ="Age must be greater than or equal to 1";
	static final String AGE_MANDATORY_MAX ="Age must be less than or equal to 99";
	static final String ADDRESS_MANDATORY ="Address name can't be blank";
	static final String CITY_MANDATORY ="city name can't be blank";
	static final String ZIPCODE_MANDATORY ="zipCode name can't be blank";
	static final String COUNTRY_MANDATORY ="Country name can't be blank";
	static final String METHOD_ARG_NOT_VALID ="Validation Failed";
	static final String CONSTRAINT_VALID_FAILED = "ConstraintViolationException Validation Failed";
	static final String FNAME_AND_OR_LNAME_EMPTY = "First and/or last name can't be empty";

}
