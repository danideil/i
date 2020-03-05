package com.daniel.daoInterfaces;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.daniel.entities.Purchase;

public interface IPurchasesDAO extends CrudRepository<Purchase, Long>{

	void deleteByCoupon(long couponId);

	void deleteByCustomer(long customerId);

	List<Purchase> findByCustomerUserId(long customerId);

}
