package com.rabo.customerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;

@Service
public interface CustomerService {

	public Optional<CustomerModel> addCustomer(CustomerModel customer);

	Optional<CustomerModel> getCustomer(int id);

	List<Optional<CustomerModel>> getAllCustomers();

	List<Optional<CustomerModel>> getCustomerByFnameAndLname(String fname, String lname);

	List<Optional<CustomerModel>> getCustomerByFnameOrLname(String fname, String lname);

	Optional<CustomerModel> updateLivingAddress(int custId, AddressModel addressdto);

}
