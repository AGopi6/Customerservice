package com.rabo.customerservice.dao;

import java.util.List;
import java.util.Optional;

import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;


public interface CustomerServiceDAO {

	public Optional<CustomerModel> addCustomer(CustomerModel customer);

	Optional<CustomerModel> getCustomer(int id);

	List<CustomerModel> getAllCustomers();

	List<CustomerModel> getCustomerByFnameAndLname(String fname, String lname);

	List<CustomerModel> getCustomerByFnameOrLname(String fname, String lname);

	public CustomerModel updateLivingAddress(int custId, AddressModel address);

}
