package com.rabo.customerservice.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.rabo.customerservice.CustomerServiceApplication;
import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;

@SpringBootTest(classes = CustomerServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CustomerServiceControllerIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	HttpHeaders headers = new HttpHeaders();
  
	@Test
	@Order(1)
	public void testAddCusotmer() {
		AddressModel addressmod = new AddressModel("Street1", "HYD", "111111", "India");
		CustomerModel customermod = new CustomerModel(1l, "Gopi", "Akula", 26, addressmod);
		HttpEntity<CustomerModel> entity = new HttpEntity<CustomerModel>(customermod, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/customer"), HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(201);

	}

	@Test
	@Order(2)
	public void tstGetCustomer() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/customer/1"), HttpMethod.GET,
				entity, String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	@Order(3)
	public void tstGetCustomers() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/customers"), HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);

	}
	
	@Test
	@Order(4)
	public void tstGetCustomerByFnameAndLname() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/customerNames/Gopi/Akula"), HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);

	}
	
	@Test
	@Order(5)
	public void tstGetCustomerByFnameOrLname() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/customerNames/Gopi/abc"), HttpMethod.GET, entity,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);

	}
	
	@Test
	@Order(6)
	public void testUpdateLivingAddress() {
		AddressModel addressmod = new AddressModel(1l,"Street2", "BNGLR", "222222", "India");		
		HttpEntity<AddressModel> entity = new HttpEntity<AddressModel>(addressmod, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/updateAddress/1"), HttpMethod.PUT, entity,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(202);

	}
	
	@Test
	@Order(7)
	public void testAddCusotmer_fNameExc() {
		AddressModel addressmod = new AddressModel("Street1", "HYD", "11111", "India");
		CustomerModel customermod = new CustomerModel(2l, "Gopi", "", 26, addressmod);
		HttpEntity<CustomerModel> entity = new HttpEntity<CustomerModel>(customermod, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/customer"), HttpMethod.POST, entity,
				String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(400);

	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
