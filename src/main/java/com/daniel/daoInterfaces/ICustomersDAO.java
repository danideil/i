package com.daniel.daoInterfaces;

import org.springframework.data.repository.CrudRepository;
import com.daniel.entities.Customer;

public interface ICustomersDAO extends CrudRepository<Customer, Long>{

//	void deleteByCustomerId(long customerId);

}
