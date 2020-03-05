package com.daniel.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.daniel.entities.Customer;
import com.daniel.controllers.CustomersController;
import com.daniel.data.UserLoginData;
import com.daniel.exceptions.ApplicationException;

@RestController
@RequestMapping("/customers")
public class CustomersApi {

	@Autowired
	private CustomersController customersController;
	
	@PostMapping
	public void addCustomer(@RequestBody Customer customer) throws ApplicationException {
		this.customersController.addCustomer(customer);
	}
	
	@PutMapping
	public void updateCustomer(HttpServletRequest request, @RequestBody Customer customer) throws ApplicationException{
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		this.customersController.updateCustomer(userLoginData, customer);
	}
	
	@DeleteMapping("{customerId}")
	public void deleteCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		this.customersController.deleteCustomer(customerId);
	}

//	@GetMapping("{customerId}")
//	public Customer getCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
//		return this.customersController.getCustomer(customerId);
//	}
	
	@GetMapping("/Profile")
	public Customer getCustomer(HttpServletRequest request) throws ApplicationException {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		long customerId = userData.getId();
		return this.customersController.getCustomer(customerId);
	}
	
	@GetMapping
	public List<Customer> getAllCustomers() throws ApplicationException {
		return this.customersController.getAllCustomers();
	}
}
