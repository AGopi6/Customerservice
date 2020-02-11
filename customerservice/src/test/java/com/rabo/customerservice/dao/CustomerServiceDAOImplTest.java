package com.rabo.customerservice.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabo.customerservice.entity.Address;
import com.rabo.customerservice.entity.Customer;
import com.rabo.customerservice.exception.CustomerNotFoundException;
import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;
import com.rabo.customerservice.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceDAOImplTest {

	@InjectMocks
	CustomerServiceDAOImpl customerServiceDAOImpl;

	@Mock
	CustomerRepository customerRepository;

	@Test
	public void testAddCustomer_CustomerAlready_Exists() {
		AddressModel addressmod = new AddressModel("Street", "HYD", "111111", "India");
		CustomerModel customermod = new CustomerModel(1, "Gopi", "Akula", 26, addressmod);

		ObjectMapper mapper = new ObjectMapper();
		Customer customer = mapper.convertValue(customermod, Customer.class);

		int id = 1;
		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

		Exception exception = Assertions.assertThrows(CustomerNotFoundException.class, () -> {
			customerServiceDAOImpl.addCustomer(customermod);
		});

		assertTrue(exception.getMessage().contains("Customer already exists"));
	}

	@Test
	public void testGetCustomer() {
		AddressModel address = new AddressModel(1, "Street1", "HYD", "111111", "India");
		CustomerModel customermod = new CustomerModel(1, "Gopi", "Akula", 26, address);
		ObjectMapper mapper = new ObjectMapper();
		Customer customer = mapper.convertValue(customermod, Customer.class);
		when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
		Optional<CustomerModel> responseEntity = customerServiceDAOImpl.getCustomer(1);
		CustomerModel customer1 = responseEntity.get();
		assertEquals("Gopi", customer1.getFname());
	}

	@Test
	public void testGetAllCustomer() {
		List<Customer> customerList = new ArrayList<Customer>();
		Address address = new Address(1, "Street1", "HYD", "111111", "India");
		Customer customer = new Customer(2, "Gopi", "Akula", 26, address);
		Customer customer1 = new Customer(3, "Gopi", "Akula", 27, address);
		customerList.add(customer);
		customerList.add(customer1);

		when(customerRepository.findAll()).thenReturn((customerList));
		List<CustomerModel> responseEntity = customerServiceDAOImpl.getAllCustomers();
		CustomerModel customer2 = responseEntity.get(0);
		assertEquals("Gopi", customer2.getFname());
	}

	@Test
	public void testSearchCustomerByfnameAndLname() {
		List<Customer> customerList = new ArrayList<Customer>();
		String fname = "Gopi";
		String lname = "Akula";
		Address address = new Address(1, "Street1", "HYD", "111111", "India");
		Customer customer = new Customer(1, "Gopi", "Akula", 26, address);
		customerList.add(customer);

		/*
		 * ObjectMapper mapper = new ObjectMapper(); List<CustomerModel> customerboList
		 * = customerList.stream().map(customer1 -> { CustomerModel customerbo =
		 * mapper.convertValue(customer, CustomerModel.class); return customerbo;
		 * }).collect(Collectors.toList());
		 */

		List<Optional<Customer>> resultList = customerList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());

		when(customerRepository.findByFnameAndLname(fname, lname)).thenReturn(resultList);
		List<CustomerModel> responseEntity = customerServiceDAOImpl.getCustomerByFnameAndLname(fname, lname);
		CustomerModel customer2 = responseEntity.get(0);
		assertEquals("Gopi", customer2.getFname());
	}

	@Test
	public void testSearchCustomerByfnameOrLname() {
		List<Customer> customerList = new ArrayList<Customer>();
		String fname = "Gopi";
		String lname = "Akula";
		Address address = new Address(1, "Street1", "HYD", "111111", "India");
		Customer customer = new Customer(1, "Gopi", "Akula", 26, address);
		Customer customer1 = new Customer(2, "Gopi", "Raju", 27, address);
		customerList.add(customer);
		customerList.add(customer1);
		List<Optional<Customer>> resultList = customerList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());
		when(customerRepository.findByFnameOrLname(fname, lname)).thenReturn(resultList);
		List<CustomerModel> responseEntity = customerServiceDAOImpl.getCustomerByFnameOrLname(fname, lname);
		CustomerModel customer2 = responseEntity.get(0);
		assertEquals("Gopi", customer2.getFname());
	}

	@Test
	public void testUpdateLivingAddress() {
		Address address = new Address(1, "Street1", "HYD", "111111", "India");
		Customer customer = new Customer(1, "Gopi", "Akula", 26, address);
		ObjectMapper mapper = new ObjectMapper();
		AddressModel addressdto = mapper.convertValue(address, AddressModel.class);
		int id = 1;
		when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);
		CustomerModel responseEntity = customerServiceDAOImpl.updateLivingAddress(id, addressdto);
		assertThat(responseEntity.getFname()).isEqualTo("Gopi");
	}

	/*
	 * @Test public void testAddCustomer_Cust_Validation() { AddressModel addressmod
	 * = new AddressModel("Street", "HYD", "111111", "India"); CustomerModel
	 * customermod = new CustomerModel(1, "", "Akula", 26, addressmod);
	 * //ObjectMapper mapper = new ObjectMapper(); //Customer customer =
	 * mapper.convertValue(customermod, Customer.class);
	 * //when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
	 * 
	 * Exception exception =
	 * Assertions.assertThrows(CustomerNotFoundException.class, () -> {
	 * customerServiceDAOImpl.addCustomer(customermod); });
	 * 
	 * Optional<CustomerModel> response=
	 * customerServiceDAOImpl.addCustomer(customermod);
	 * //assertTrue(exception.getMessage().contains("Mandatory fields"));
	 * System.out.println(response); }
	 */

}
