package com.daniel.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.daniel.daoInterfaces.IPurchasesDAO;
import com.daniel.entities.Coupon;
import com.daniel.entities.Purchase;
import com.daniel.enums.ErrorTypes;
import com.daniel.exceptions.ApplicationException;
import com.daniel.entities.Customer;

@Controller
public class PurchasesController {
	
	@Autowired
	private IPurchasesDAO purchasesDao;

	@Autowired
	private CouponsController couponsController;
	
	@Autowired
	private CustomersController customersController;
	
	public PurchasesController() {
	}
	
	// ----------------- Primary controllers: ----------------- //
	
	public void addPurchase(Purchase purchase, long customerId) throws ApplicationException {
		Coupon coupon = this.couponsController.getCoupon(purchase.getCoupon().getId());
//		Purchase purchase = new Purchase();
		purchase = setPurchaseData(purchase, coupon, customerId, purchase.getQuantity());
		System.out.println(purchase);
		try {
			purchasesDao.save(purchase);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_PURCHASE, "Failed to create new purchase");
		}
	}
	

	public void updatePurchase(Purchase purchase, long customerId) throws ApplicationException {
		Coupon coupon = this.couponsController.getCoupon(purchase.getCoupon().getId());
		purchase = setPurchaseData(purchase, coupon, customerId, purchase.getQuantity());
		purchaseValidations(purchase);
		System.out.println(purchase);
		try {
			purchasesDao.save(purchase);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_UPDATE_PURCHASE, "Failed to update purchase");
		}
	}
		

	public void deletePurchase(long purchaseId) throws ApplicationException {
		try {
			purchasesDao.deleteById(purchaseId);
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_DELETE_PURCHASE, "Failed to delete purchase");
		}
	}


	public Purchase getPurchase(long purchaseId) throws ApplicationException {
		try {
			return purchasesDao.findById(purchaseId).get();
		}
		catch (Exception e) {
			if (e.getCause() == null) {
				throw new ApplicationException(e, ErrorTypes.PURCHASE_ERROR_UNKNOWN, "Failed to get purchase");
			}
			else {
				throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_PURCHASE, "Failed to get purchase");
			}
		}
	}
	
	public List<Purchase> getAllPurchases() throws ApplicationException {
		try {
			List<Purchase> purchases = (List<Purchase>) purchasesDao.findAll();
			if (purchases.isEmpty()) {
				throw new ApplicationException(ErrorTypes.PURCHASES_LIST_EMPTY, "no purchases");
			}
			return (List<Purchase>) purchasesDao.findAll();
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_PURCHASES, "Failed to get purchases list");
		}
	}
	
	public List<Purchase> getAllPurchasesByCustomerUserId(long customerId) throws ApplicationException {
		
		try {
			List<Purchase> purchases = (List<Purchase>) purchasesDao.findByCustomerUserId(customerId);
			if (purchases.isEmpty()) {
				throw new ApplicationException(ErrorTypes.PURCHASES_LIST_EMPTY, "no purchases");
			}
			return purchases;
		}
		catch (Exception e) {
			throw new ApplicationException(e, ErrorTypes.FAILED_TO_GET_PURCHASES, "Failed to get purchases list");
		}
	}
	// ----------------- Secondary controllers: ----------------- //
	private void purchaseValidations(Purchase purchase) throws ApplicationException {
		if (!isPurchaseExistById(purchase.getId())) {
			throw new ApplicationException(ErrorTypes.FAILED_TO_GET_PURCHASE, "could not find purchase");
		}
	}

	private boolean isPurchaseExistById(long id) {
		return purchasesDao.existsById(id);
	}

	private Purchase setPurchaseData(Purchase purchase, Coupon coupon, long customerId, int quantity) throws ApplicationException {
		try {
			// set the new amount of coupons left:
			this.couponsController.updateCouponAmount(coupon, quantity);
			
			// create a Customer object and set it with the specific customer who made the purchase:
			try {
				purchase.setCustomer(new Customer());
				purchase.setCustomer(customersController.getCustomer(customerId));
			}
			catch (Exception e) {
				System.out.println("Fail to set Customer");
			}
			
			
			// set purchase price by qty and coupon price:
			setPurchasePrice(purchase, coupon);
			
			// set the current date:
			setPurchaseDate(purchase);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorTypes.FAILED_TO_SET_PURCHASE, "DEFUQ");
		}
		
		return purchase;
	}
	
	private void setPurchaseDate(Purchase purchase) throws ApplicationException {
		try {
			long purchsaseDateTimeMillis = new Date().getTime();
			Timestamp purchaseDate = new Timestamp(purchsaseDateTimeMillis);
			purchase.setPurchaseDate(purchaseDate);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorTypes.FAILED_TO_SET_PURCHASE_DATE, "problem setting purchase date");
		}
		
	}

	private void setPurchasePrice(Purchase purchase, Coupon coupon) throws ApplicationException {
		try {
			float purchasePrice = purchase.getQuantity() * coupon.getPrice();
			purchase.setPurchasePrice(purchasePrice);
		}
		catch (Exception e) {
			throw new ApplicationException(ErrorTypes.FAILED_TO_SET_PURCHASE_PRICE, "problem setting purchase price");
		}
	}


}