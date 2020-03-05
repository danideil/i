package com.daniel.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.daniel.entities.Customer;
import com.daniel.daoInterfaces.ICustomersDAO;
import com.daniel.data.UserLoginData;
import com.daniel.enums.ErrorTypes;
import com.daniel.enums.UserType;
import com.daniel.controllers.UsersController;
import com.daniel.exceptions.ApplicationException;

@Controller
public class CustomersController {
//	
	@Autowired
	private ICustomersDAO customersDao;
	
	@Autowired
	private UsersController usersController;
	
//	@Autowired
//	private PurchasesController purchasesController;
	
	public CustomersController() {
	}
	
	// ----------------- Primary controllers: ----------------- //
	
	public void addCustomer(Customer customer) throws ApplicationException {
		customer.getUser().setIsActive(true);
		customerValidations(customer);
		// hash user password:
		customer.getUser().setPassword(String.valueOf(customer.getUser().getPassword().hashCode()));
		try {
			this.customersDao.save(customer);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.CUSTOMER_ADD_FAILED, "Failed to add customer");
		}
	}
	
	
	public void deleteCustomer(long customerId) throws ApplicationException {
		//this.purchasesController.deleteCustomerPurchases(customerId);
		try {
			this.customersDao.deleteById(customerId); // we decided not to delete?
		}
		catch (Exception e){
			throw new ApplicationException(e, ErrorTypes.CUSTOMER_DELETE_FAILED, "Fail to delete customer and its purchases");
		}
	}
	
	public void updateCustomer(UserLoginData userLoginData, Customer customer) throws ApplicationException {
		customerUpdateValidtaions(customer, userLoginData);
		// hash user password:
		customer.getUser().setPassword(String.valueOf(customer.getUser().getPassword().hashCode()));
		try {
			this.customersDao.save(customer);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.CUSTOMER_UPDATE_FAILED, "Fail to update customer");
		}
	}
	
	public Customer getCustomer(long customerId) throws ApplicationException {
		try {
			return this.customersDao.findById(customerId).get();
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_CUSTOMER, "Failed to get customer");
		}
	}
	
	public List<Customer> getAllCustomers() throws ApplicationException {
		try {
			List<Customer> customers = (List<Customer>) customersDao.findAll();
			if (customers.isEmpty()) {
				throw new ApplicationException(ErrorTypes.USERS_LIST_EMPTY, "no customers");
			}
			return customers;
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_CUSTOMER, "Failed to get customers list");
		}
	}
	
//	// ----------------- Secondary controllers: ----------------- //

	private void customerValidations(Customer customer) throws ApplicationException {
		this.usersController.validateCreateUser(customer.getUser());
		//more validations for customer: maybe later...
	}
	
	private void customerUpdateValidtaions(Customer customer, UserLoginData userLoginData) throws ApplicationException {
		if(!userLoginData.getUserType().equals(UserType.ADMIN)) {
			// check if its the customer whom trying to do update, is the same one connected - type admin is always allowed;
			if (!this.isUserAuthorized(customer.getId(), userLoginData.getId())) {
				throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "User is missing the required permissions.");
			}
		}
		// check if the customer exists;
		if(!isCustomerExistById(customer.getId())) {
			throw new ApplicationException(ErrorTypes.INVALID_CUSTOMER_ID, "Customer was not found");
		}
	}
	
	private boolean isCustomerExistById(long customerId) throws ApplicationException {
		try {
			return this.customersDao.existsById(customerId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.INVALID_CUSTOMER_ID, "Failed to check if customer exists");
		}
	}


	// check if the userId is equal to the customerId who's trying to update
	private boolean isUserAuthorized(long customerIdToUpdate, long userIdFromCache) {
		if (customerIdToUpdate == userIdFromCache) {
			return true;
		}
		return false;
	}
}
