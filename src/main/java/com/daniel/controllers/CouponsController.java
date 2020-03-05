package com.daniel.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.daniel.entities.Company;
import com.daniel.entities.Coupon;
import com.daniel.daoInterfaces.ICouponsDAO;
import com.daniel.data.UserLoginData;
import com.daniel.enums.Categories;
import com.daniel.enums.ErrorTypes;
import com.daniel.enums.UserType;
import com.daniel.exceptions.ApplicationException;

@Controller
public class CouponsController {
	
	@Autowired
	private ICouponsDAO couponsDao;
	
//	@Autowired
//	private PurchasesController purchasesController;
	
	@Autowired
	private CompaniesController companiesController;
	
	public CouponsController() {
	}
	// ----------------- Primary controllers: ----------------- //
	
	public void addCoupon(Coupon coupon, UserLoginData userLoginData) throws ApplicationException {
		coupon.setCompany(new Company());
		coupon.getCompany().setId(userLoginData.getCompanyId());
		
		validateCreateCoupon(coupon, userLoginData);
		try {
			couponsDao.save(coupon);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COUPON_ADD_FAILED, "Fail to add coupon");
		}
	}
	
	public void updateCoupon(Coupon coupon, UserLoginData userLoginData) throws ApplicationException {
		coupon.setCompany(new Company());
		coupon.getCompany().setId(userLoginData.getCompanyId());
		
		if (!this.isCouponExistsById(coupon.getId())) {
			throw new ApplicationException(ErrorTypes.INVALID_COUPON_ID, "Coupon was not found");
		}
		
		validateCreateCoupon(coupon, userLoginData);
		try {
			couponsDao.save(coupon);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COUPON_UPDATE_FAILED, "Fail to update coupon");
		}
	}
	
	private boolean isCouponExistsById(long id) throws ApplicationException {
		try {
			return this.couponsDao.existsById(id);
		} catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.GENERAL_ERROR, "Failed to check id");
		}
	}
	public void deleteCoupon(long couponId) throws ApplicationException {
//		this.purchasesController.deletePurchasesByCouponId(couponId);
		try {
			this.couponsDao.deleteById(couponId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COUPON_DELETE_FAILED, "Fail to delete coupon");
		}
	}

	public Coupon getCoupon(long couponId) throws ApplicationException {
		try {
			return couponsDao.findById(couponId).get();
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COUPON, "Fail to get coupon");
		}
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {
		try {
			List<Coupon> coupons = (List<Coupon>) couponsDao.findAll();
			if (coupons.isEmpty()) {
				throw new ApplicationException(ErrorTypes.COUPONS_LIST_EMPTY, "Coupons list is empty");
			}
			return coupons;
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COUPON, "Fail to get coupons");
		}
	}

	// -------------------- extra primary functions -------------------- //
	
	// get all company coupons:
	public List<Coupon> getCouponsByCompanyId(Long companyId) throws ApplicationException {
		if (!this.companiesController.isCompanyExistsById(companyId)) {
			throw new ApplicationException(ErrorTypes.INVALID_COMPANY, "Company wasn't found.");
		}
		try {
			// return all company coupons:
			return this.couponsDao.findByCompany(companyId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COUPON, "Fail to get company coupons");
		}
	}
	
	// get all coupons by companyId and Category:
	public List<Coupon> getCompanyCouponsByCategory(Categories category, Long companyId) throws ApplicationException {
		try {
			return this.couponsDao.findByCategoryAndCompany(category, companyId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COUPON, "Fail to get company coupons by category");
		}
	}
	
	// get all coupons by companyId and maxPrice
	public List<Coupon> getCompanyCouponsByMaxPrice(float maxPrice, Long companyId) throws ApplicationException {
		try {
			return this.couponsDao.findByPriceLessThanEqualAndCompany(maxPrice, companyId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_COUPON, "Fail to get company coupons by max price");
		}
	}
	
//	@Transactional
//	public void deleteCouponsByCompanyId(Long companyId) throws ApplicationException {
//		List<Coupon> coupons = getCouponsByCompanyId(companyId);
//		for (Coupon coupon : coupons) {
//			// delete the coupon purchases only:
//			this.purchasesController.deletePurchasesByCouponId(coupon.getId());
//		}
//		try {
//			// delete all company coupons in 1 connection:
//			couponsDao.deleteByCompany(companyId);
//		}
//		catch (Exception e) {
//			throw new ApplicationException(e, ErrorTypes.COUPON_DELETE_LIST_FAILED, "Fail to delete company coupons");
//		}
//	}
	
	// ----------------- Secondary controllers: ----------------- //
	private void validateCreateCoupon(Coupon coupon, UserLoginData userLoginData) throws ApplicationException {
		// check if user is authorized to create coupon:
		if (!this.isUserAuthorized(userLoginData.getUserType())) {
			throw new ApplicationException(ErrorTypes.UNAUTHORIZED_USER, "User is missing the required permissions.");
		}
		
		// check if coupon exist by title:
		if (this.isCouponExistsByTitle(coupon, userLoginData)){
			throw new ApplicationException(ErrorTypes.INVALID_COUPON, "Coupon title already exists");
		}

		// check that coupon price is higher than 0:
		if (coupon.getPrice() <= 0) {
			throw new ApplicationException(ErrorTypes.INVALID_PRICE, "Price must be higher than 0.");
		}
		// check if the start date hasn't already pass
		Date today = Calendar.getInstance().getTime();
		if (coupon.getStartDate().compareTo(today) < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_START_DATE, "Start date has already passed.");
		}
		if (coupon.getEndDate().compareTo(coupon.getStartDate()) < 0) {
			throw new ApplicationException(ErrorTypes.INVALID_END_DATE, "End date cannot be previously to the start date.");
		}
	}
	
	public boolean isUserAuthorized(UserType userType) {
//		if ((!userType.equals(UserType.ADMIN)) && (!userType.equals(UserType.COMPANY))) {
		if (!userType.equals(UserType.COMPANY)) {
			return false;
		}
		return true;
	}

	private boolean isCouponExistsByTitle(Coupon coupon, UserLoginData userLoginData) throws ApplicationException {
		try {
			return couponsDao.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompany().getId());
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COMPANY_GENERAL_ERROR, "Failed to check by title and id");
		}
	}

	public void updateCouponAmount(Coupon coupon, int quantity) throws ApplicationException {
		int amount = coupon.getAmount();
		if ((amount - quantity) <= 0) {
			throw new ApplicationException(ErrorTypes.NO_COUPONS, "Not enough coupons to complete this purchase.");
		}
		coupon.setAmount(amount - quantity);
		try {
			couponsDao.save(coupon);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.COUPON_UPDATE_FAILED, "Fail to update coupon");
		}
	}
}
