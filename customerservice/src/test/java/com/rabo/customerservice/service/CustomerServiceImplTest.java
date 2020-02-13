package com.rabo.customerservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rabo.customerservice.dao.CustomerServiceDAO;
import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	@Mock
	CustomerServiceDAO customerServiceDAO;

	static AddressModel addressmod;
	static CustomerModel customermod;
	static AddressModel addressmodChange;
	static CustomerModel customermodChange;

	public static void intializeCustomer() {
		addressmod = new AddressModel("Street", "HYD", "111111", "India");
		customermod = new CustomerModel(1l, "Gopi", "Akula", 26, addressmod);

		addressmodChange = new AddressModel("Street2", "BLR", "536689", "India");
		customermodChange = new CustomerModel(1l, "Gopi", "Akula", 26, addressmodChange);
	}

	@BeforeAll
	public static void defaultInitialization() {
		intializeCustomer();
	}

	@Test
	public void testAddCustomer_CustomerAlready_Exists() {
		when(customerServiceDAO.addCustomer(customermod)).thenReturn(Optional.of(customermod));
		Optional<CustomerModel> custOptional = customerServiceImpl.addCustomer(customermod);
		assertThat(custOptional.get().getFname()).isEqualTo(customermod.getFname());
	}

	@Test
	public void testGetCustomer() {
		when(customerServiceDAO.getCustomer(1l)).thenReturn(Optional.of(customermod));
		Optional<CustomerModel> customerbo = customerServiceImpl.getCustomer(1l);
		assertThat(customerbo.get().getFname()).isEqualTo(customermod.getFname());
	}

	@Test
	public void testUpdateLivingAddress() {
		when(customerServiceDAO.updateLivingAddress(customermod.getCustId(), addressmod)).thenReturn(customermodChange);
		Optional<CustomerModel> customerOptional = customerServiceImpl.updateLivingAddress(customermod.getCustId(),
				addressmod);
		assertThat(customerOptional.get().getAddress().getCity()).isEqualTo(addressmodChange.getCity());
	}
}
