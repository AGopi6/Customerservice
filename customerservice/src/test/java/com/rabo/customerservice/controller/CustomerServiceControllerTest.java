package com.rabo.customerservice.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;
import com.rabo.customerservice.service.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceControllerTest {

	@InjectMocks
	CustomerServiceController customerServiceController;

	@Mock
	CustomerServiceImpl customerServiceImpl;
 
	@Test
	public void testAddCustomer() {
		AddressModel address = new AddressModel(1l, "Street1", "HYD", "111111", "India");
		CustomerModel customer = new CustomerModel(1l, "Gopi", "Akula", 26, address);
		when(customerServiceImpl.addCustomer(customer)).thenReturn(Optional.of(customer));
		ResponseEntity<String> responseEntity = customerServiceController.addCustomer(customer);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
	} 

	@Test
	public void testGetCustomer() {
		AddressModel address = new AddressModel(1l, "Street1", "HYD", "111111", "India");
		CustomerModel customer = new CustomerModel(1l, "Gopi", "Akula", 26, address);
		when(customerServiceImpl.getCustomer(1l)).thenReturn(Optional.of(customer));
		ResponseEntity<Optional<CustomerModel>> responseEntity = customerServiceController.getCustomer(1l);
		CustomerModel customer1 = responseEntity.getBody().get();
		assertEquals("Gopi", customer1.getFname());
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void testGetAllCustomer() {
		List<CustomerModel> customerList = new ArrayList<CustomerModel>();
		AddressModel address = new AddressModel(1l, "Street1", "HYD", "111111", "India");
		CustomerModel customer = new CustomerModel(1l, "Gopi", "Akula", 26, address);
		CustomerModel customer1 = new CustomerModel(2l, "Gopi", "Akula", 26, address);
		customerList.add(customer);
		customerList.add(customer1);
		List<Optional<CustomerModel>> resultList = customerList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());
		when(customerServiceImpl.getAllCustomers()).thenReturn(resultList);
		ResponseEntity<List<Optional<CustomerModel>>> responseEntity = customerServiceController.getAllCustomers();
		Optional<CustomerModel> customer2 = responseEntity.getBody().get(0);
		assertEquals("Gopi", customer2.get().getFname());
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	public void testSearchCustomerByfnameAndLname() {
		List<CustomerModel> customerList = new ArrayList<CustomerModel>();
		String fname = "Gopi";
		String lname = "Akula";
		AddressModel address = new AddressModel(1l, "Street1", "HYD", "111111", "India");
		CustomerModel customer = new CustomerModel(1l, "Gopi", "Akula", 26, address);
		customerList.add(customer);
		List<Optional<CustomerModel>> resultList = customerList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());
		when(customerServiceImpl.getCustomerByFnameAndLname(fname, lname)).thenReturn(resultList);
		ResponseEntity<List<Optional<CustomerModel>>> responseEntity = customerServiceController
				.searchCustomerNameBy(fname, lname);
		Optional<CustomerModel> customer2 = responseEntity.getBody().get(0);
		assertEquals("Gopi", customer2.get().getFname());
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
 
	@Test
	public void testSearchCustomerByfnameOrLname() {
		List<CustomerModel> customerList = new ArrayList<CustomerModel>();
		String fname = "Gopi";
		String lname = "Akulas";
		AddressModel address = new AddressModel(1l, "Street1", "HYD", "111111", "India");
		CustomerModel customer = new CustomerModel(1l, "Gopi", "Raju", 26, address);
		CustomerModel customer1 = new CustomerModel(2l, "Gopi", "Akula", 26, address);
		customerList.add(customer);
		customerList.add(customer1);
		List<Optional<CustomerModel>> resultList = customerList.stream().map(Optional::ofNullable)
				.collect(Collectors.toList());
		when(customerServiceImpl.getCustomerByFnameOrLname(fname, lname)).thenReturn(resultList);
		ResponseEntity<List<Optional<CustomerModel>>> responseEntity = customerServiceController
				.searchCustomerNameBy(fname, lname);
		Optional<CustomerModel> customer2 = responseEntity.getBody().get(0);
		assertEquals("Gopi", customer2.get().getFname());
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	}
 
	@Test
	public void testUpdateLivingAddress() {
		AddressModel address = new AddressModel("Street1", "HYD", "111111", "India");
		CustomerModel customer = new CustomerModel(1l, "Gopi", "Akula", 26, address);		
		Long id = 1l;
		when(customerServiceImpl.updateLivingAddress(id, address)).thenReturn(Optional.of(customer));
		ResponseEntity<String> responseEntity = customerServiceController.updateLivingAddress(id, address);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(202);
	}

}