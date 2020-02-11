package com.rabo.customerservice.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customerservice.entity.Address;
import com.rabo.customerservice.entity.Customer;
import com.rabo.customerservice.exception.CustomerNotFoundException;
import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;
import com.rabo.customerservice.repository.CustomerRepository;
import com.rabo.customerservice.util.ErrorCodes;

@Repository
public class CustomerServiceDAOImpl implements CustomerServiceDAO {
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceDAOImpl.class);
	@Autowired
	private CustomerRepository customerRepository;
 
	@Override
	public Optional<CustomerModel> addCustomer(CustomerModel customerbo) {
		log.info("Enters CustomerServiceDAOImpl :addedCustomer info before adding" + customerbo);
		//Optional<CustomerModel> custVal = Optional.of(customerbo);		
		ObjectMapper mapper = new ObjectMapper();
		Customer customer = mapper.convertValue(customerbo, Customer.class);
		int id = customer.getCustId();
		Optional<Customer> customer1 = customerRepository.findById(id);
		if (customer1.isPresent()) {
			throw new CustomerNotFoundException("Customer already exists on the given id =" + customer.getCustId());
		}
		Customer customer2 = customerRepository.save(customer);
		CustomerModel mapCustomerbo = mapper.convertValue(customer2, CustomerModel.class);
		Optional<CustomerModel> optCustomerbo = Optional.of(mapCustomerbo);
		log.info("Exits CustomerServiceDAOImpl: after adding addCustomer as optional = " + optCustomerbo);
		return optCustomerbo;
	}

	@Override
	public Optional<CustomerModel> getCustomer(int id) {
		log.info("Enters in CustomerServiceDAOImpl: getCustomer" + id);
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent()) {
			throw new CustomerNotFoundException(ErrorCodes.GET_CUSTOMER_EXCEPTION + id);
		}
		ObjectMapper mapper = new ObjectMapper();
		CustomerModel customerbo = mapper.convertValue(customer.get(), CustomerModel.class);
		Optional<CustomerModel> optCustomerbo = Optional.of(customerbo);
		log.info("Exits in CustomerServiceDAOImpl: getCustomer" + id);
		return optCustomerbo;
	}

	@Override
	public List<CustomerModel> getAllCustomers() {
		List<Customer> customerList = customerRepository.findAll();
		log.info("getAllCustomers Customer info CustomerServiceDAOImpl: getAllCustomers" + customerList);
		ObjectMapper mapper = new ObjectMapper();
		List<CustomerModel> customerboList = customerList.stream().map(customer -> {
			CustomerModel customerbo = mapper.convertValue(customer, CustomerModel.class);
			return customerbo;
		}).collect(Collectors.toList());
		log.info("Exits getAllCustomers CustomerBO info CustomerServiceDAOImpl: getAllCustomers" + customerboList);
		return customerboList;
	}

	@Override
	public List<CustomerModel> getCustomerByFnameAndLname(String fname, String lname) {
		log.info("Enters in CustomerServiceDAOImpl: getCustomerByFnameAndLname");
		List<Optional<Customer>> customerList = customerRepository.findByFnameAndLname(fname, lname);
		ObjectMapper mapper = new ObjectMapper();
		List<CustomerModel> customerboList = customerList.stream().map(customer -> {
			CustomerModel customerbo = mapper.convertValue(customer.get(), CustomerModel.class);
			return customerbo;
		}).collect(Collectors.toList());
		log.info("Exits in CustomerServiceDAOImpl: getCustomerByFnameAndLname");
		return customerboList;
	}

	@Override
	public List<CustomerModel> getCustomerByFnameOrLname(String fname, String lname) {
		log.info("Enters in CustomerServiceDAOImpl: getCustomerByFnameOrLname");
		List<Optional<Customer>> customerList = customerRepository.findByFnameOrLname(fname, lname);
		ObjectMapper mapper = new ObjectMapper();
		List<CustomerModel> customerboList = customerList.stream().map(customer -> {
			CustomerModel customerbo = mapper.convertValue(customer.get(), CustomerModel.class);
			return customerbo;
		}).collect(Collectors.toList());
		log.info("Exits in CustomerServiceDAOImpl: getCustomerByFnameOrLname");
		return customerboList;
	}

	public CustomerModel updateLivingAddress(int custId, AddressModel addressbo) {
		log.info("Enters in CustomerServiceDAOImpl: updateLivingAddress");
		ObjectMapper mapper = new ObjectMapper();
		Address address = mapper.convertValue(addressbo, Address.class);
		Optional<Customer> customer = customerRepository.findById(custId);
		if (!customer.isPresent()) {
			log.error("form CustomerServiceController: getCustomer method" + ErrorCodes.UPDATE_ADDRESS_EXCEPTION);
			throw new CustomerNotFoundException(ErrorCodes.UPDATE_ADDRESS_EXCEPTION);
		} else if ((customer.get().getAddress().getId() != 0)) {
			address.setId(customer.get().getAddress().getId());
		}
		CustomerModel customerbo = mapper.convertValue(customer.get(), CustomerModel.class);
		if (customer.isPresent()) {
			customer.get().setAddress(address);
			Customer customer1 = customerRepository.save(customer.get());
			CustomerModel customerbo1 = mapper.convertValue(customer1, CustomerModel.class);
			log.info("Exits in CustomerServiceDAOImpl: updateLivingAddress");
			return customerbo1;
		}
		log.info("Exits in CustomerServiceDAOImpl: updateLivingAddress");
		return customerbo;
	}

}
