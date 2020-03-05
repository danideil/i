package com.daniel.daoInterfaces;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.daniel.entities.Coupon;
import com.daniel.enums.Categories;

public interface ICouponsDAO extends CrudRepository<Coupon, Long> {

	List<Coupon> findByCompany(Long companyId);

	List<Coupon> findByCategoryAndCompany(Categories category, Long companyId);

	List<Coupon> findByPriceLessThanEqualAndCompany(float maxPrice, Long companyId);

	boolean existsByTitleAndCompanyId(String title, Long companyId);

	void deleteByCompany(Long companyId);
}
