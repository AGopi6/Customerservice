package com.rabo.customerservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabo.customerservice.dao.CustomerServiceDAO;
import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;
import com.rabo.customerservice.model.FnameLnameValidation;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerServiceDAO customerServiceDAO;

	@Autowired
	FnameLnameValidation nameValidation; 

	@Override
	public Optional<CustomerModel> addCustomer(CustomerModel customer) {
		log.info("Enters in CustomerServiceImpl: addCustomer");		
		Optional<CustomerModel> Optcustomerbo = customerServiceDAO.addCustomer(customer);
		log.info("addedCustomer info after adding from Service" + Optcustomerbo);
		log.info("Exits addedCustomer info after adding to optional from Service" + Optcustomerbo);
		return Optcustomerbo;
	}

	@Override
	public Optional<CustomerModel> getCustomer(int id) {
		log.info("Enters in CustomerServiceImpl: getCustomer");
		Optional<CustomerModel> customerbo = customerServiceDAO.getCustomer(id);
		log.info("Retrieved cusotmer info = " + customerbo);
		log.info("Exits in CustomerServiceImpl: getCustomer");
		return customerbo;
	}

	@Override
	public List<Optional<CustomerModel>> getAllCustomers() {
		log.info("Enters in CustomerServiceImpl: getAllCustomers");
		List<CustomerModel> customerboList = customerServiceDAO.getAllCustomers();
		List<Optional<CustomerModel>> resultList = customerboList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());
		log.info("Retrieved All cusotmer info = " + resultList);
		log.info("Exits in CustomerServiceImpl: getAllCustomers");
		return resultList;
	}

	@Override
	public List<Optional<CustomerModel>> getCustomerByFnameAndLname(String fname, String lname) {
		log.info("Enters in CustomerServiceImpl: getCustomerByFnameAndLname");
		boolean flag =false;		
		flag = nameValidation.vaidateFnameLnameRegex(fname, lname);
		List<CustomerModel> customerboList = new ArrayList<CustomerModel>();
		if (flag) {
			customerboList = customerServiceDAO.getCustomerByFnameAndLname(fname, lname);
		}
		List<Optional<CustomerModel>> resultList = customerboList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());
		log.info("Exits Retrieved cusotmers info by CustomerByFnameAndLname = " + resultList);
		return resultList;
	}

	@Override
	public List<Optional<CustomerModel>> getCustomerByFnameOrLname(String fname, String lname) {
		log.info("Enters in CustomerServiceImpl: getCustomerByFnameOrLname");
		boolean flag =false;		
		flag = nameValidation.vaidateFnameLnameRegex(fname, lname);
		List<CustomerModel> customerboList = new ArrayList<CustomerModel>();
		if (flag) {
			customerboList = customerServiceDAO.getCustomerByFnameOrLname(fname, lname);
		}
		List<Optional<CustomerModel>> resultList = customerboList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());
		log.info("Exits Retrieved cusotmers info by CustomerByFnameOrLname = " + resultList);
		return resultList;
	}

	public Optional<CustomerModel> updateLivingAddress(int custId, AddressModel addressdto) {
		log.info("Enters in CustomerServiceImpl: updateLivingAddress");
		CustomerModel CustomerModel = customerServiceDAO.updateLivingAddress(custId, addressdto);
		Optional<CustomerModel> Optcustomerdto = Optional.of(CustomerModel);
		log.info("Exits in CustomerServiceImpl: updateLivingAddress");
		return Optcustomerdto;

	}

}
