package com.rabo.customerservice.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rabo.customerservice.exception.CustomerNotFoundException;
import com.rabo.customerservice.model.AddressModel;
import com.rabo.customerservice.model.CustomerModel;
import com.rabo.customerservice.service.CustomerServiceImpl;
import com.rabo.customerservice.util.CustomerServiceConstants;
import com.rabo.customerservice.util.ErrorCodes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Customer Service REST Endpoint")
public class CustomerServiceController {
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceController.class);

	@Autowired
	CustomerServiceImpl customerServiceImpl;

	/*
	 * Method name: addCustomer 
	 * Aim: to add customer to DB 
	 * Functionality:This methods adds a new customer to DB by taking the Customer info 
	 * Input:Customer 
	 */

	@ApiOperation(value = "New Customer registration", notes = "Add the customer to DB", response = String.class)
	@RequestMapping(value = "/customer", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> addCustomer(@RequestBody CustomerModel customer) {
		log.info(" Enters CustomerServiceController: addCustomer method"+ customer);				
		Optional<CustomerModel> customer1 = customerServiceImpl.addCustomer(customer);		
		if (customer1.isPresent()) {
			log.info("Successfully added the cstomer info=" + customer1.get());
			return new ResponseEntity<String>(CustomerServiceConstants.CUSTOMER_ADDED_SUCCESS, HttpStatus.CREATED);
		}
		log.info("Exits in CustomerServiceController: addedCustomer info is not added" + customer);		
		return new ResponseEntity<String>(CustomerServiceConstants.CUSTOMER_ADDED_FAILED, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Method name: getCustomer 
	 * Aim: to retrieve customer from DB 
	 * Functionality:This methods retrieves the customer from DB based on Customer id 
	 * Input:Customer Id
	 */
	@ApiOperation(value = "Retrieves single customer information", notes = "Based on given customer id, customer information is retrieved", response = Optional.class)
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Optional<CustomerModel>> getCustomer(@Valid @PathVariable("id") int id) {
		log.info("Enters in CustomerServiceController: getCustomer method = " +id);		
		Optional<CustomerModel> customerdto = customerServiceImpl.getCustomer(id);
		if (!customerdto.isPresent()) {
			log.error("form CustomerServiceController: getCustomer method" + ErrorCodes.GET_CUSTOMER_EXCEPTION + id);
			throw new CustomerNotFoundException(ErrorCodes.GET_CUSTOMER_EXCEPTION + id);
		}
		log.info("form CustomerServiceController: getCustomer method- cusotmer retrieved for" + id);
		return new ResponseEntity<Optional<CustomerModel>>(customerdto, HttpStatus.OK);
	}

	/*
	 * Method name: getAllCustomers 
	 * Aim: to retrieve all customer from DB
	 * Functionality:This methods retrieves all the customers from DB. 
	 * Input: not required
	 */

	@ApiOperation(value = "Retrieves all the customers information", notes = "All the customers information  available in is retrieved", response = List.class)
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Optional<CustomerModel>>> getAllCustomers() {
		log.info("form CustomerServiceController: getAllCustomers method");
		List<Optional<CustomerModel>> customerdtoList = customerServiceImpl.getAllCustomers();
		if (customerdtoList.isEmpty()) {
			log.info("form CustomerServiceController: getAllCustomers method- no customer info is avaiable to retrieve");
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Info", CustomerServiceConstants.CUSTOMER_INFO_NOT_AVAILABLE);
			return new ResponseEntity<List<Optional<CustomerModel>>>(customerdtoList, responseHeaders,
					HttpStatus.NO_CONTENT);
		}
		log.info("form CustomerServiceController: no of customers retrieved " + customerdtoList.size());
		return new ResponseEntity<List<Optional<CustomerModel>>>(customerdtoList, HttpStatus.OK);

	}

	/*
	 * Method name: searchCustomerNameBy 
	 * Aim: to retrieve customer from DB
	 * Functionality:This methods retrieves all the customers from DB based on the name combinations. 
	 * Input:Customer first name, condition and last name
	 */

	@ApiOperation(value = "Retrieves customer information by fname and/or lname", notes = "Based on given customer fname and/or lanme, customer information is retrieved", response = List.class)

	@RequestMapping(value = "/customerNames/{fname}/{lname}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Optional<CustomerModel>>> searchCustomerNameBy(@PathVariable("fname") String fname,
			@PathVariable("lname") String lname) {
		log.info("Enters CustomerServiceController: SearchCustomerName by" + fname + lname);	   
		List<Optional<CustomerModel>> customerdtoAndList = customerServiceImpl.getCustomerByFnameAndLname(fname, lname);
		if (customerdtoAndList.isEmpty()) {
			List<Optional<CustomerModel>> customerdtoOrList = customerServiceImpl.getCustomerByFnameOrLname(fname, lname);
			if (customerdtoOrList.isEmpty()) {
				log.info("form CustomerServiceController: searchCustomerNameBy customerdtoOrList method- No of customer info is avaiable for given name");
				return new ResponseEntity<List<Optional<CustomerModel>>>(customerdtoOrList, HttpStatus.NO_CONTENT);
			}
			log.info("form CustomerServiceController: searchCustomerNameBy customerdtoOrList method- customer info for given name="+ customerdtoOrList);
			return new ResponseEntity<List<Optional<CustomerModel>>>(customerdtoOrList, HttpStatus.OK);
		}
		if (customerdtoAndList.isEmpty()) {
			log.info("form CustomerServiceController: searchCustomerNameBy customerdtoAndList method- No of customer info is avaiable for given name");
			return new ResponseEntity<List<Optional<CustomerModel>>>(customerdtoAndList, HttpStatus.NO_CONTENT);
		}
		log.info("form CustomerServiceController: searchCustomerNameBy customerdtoAndList method- customer info for given name="+ customerdtoAndList);
		return new ResponseEntity<List<Optional<CustomerModel>>>(customerdtoAndList, HttpStatus.OK);
	}

	/*
	 * Method name: updateLivingAddress 
	 * Aim: to update customer address to DB 
	 * Functionality:This method updates the customer address based on the customer
	 *  id Input:Customer Id and address
	 */

	@ApiOperation(value = "Updates the customer living address", notes = "Based on given customer id and address, address is updated for the given customer id", response = String.class)
	@RequestMapping(value = "/updateAddress/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> updateLivingAddress(@Valid @PathVariable("id") int custId,
			@RequestBody AddressModel address) {
		log.info("form CustomerServiceController: updateLivingAddress");		
		Optional<CustomerModel> customerdto = customerServiceImpl.updateLivingAddress(custId, address);
		if (customerdto.isPresent()) {
			log.info("LivingAddress is updated successfullly");
			return new ResponseEntity<String>(CustomerServiceConstants.CUSTOMER_ADDERESS_UPDATE_SUCCESS, HttpStatus.ACCEPTED);
		}
		log.info("LivingAddress is not updated");
		return new ResponseEntity<String>(CustomerServiceConstants.CUSTOMER_ADDERESS_UPDATE_FAILED, HttpStatus.NOT_MODIFIED);
	}

}
