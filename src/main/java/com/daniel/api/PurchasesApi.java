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
import com.daniel.entities.Purchase;
import com.daniel.data.UserLoginData;
import com.daniel.controllers.PurchasesController;

@RestController
@RequestMapping("/purchases")
public class PurchasesApi {

	@Autowired
	private PurchasesController purchasesController;
	
	@PostMapping
	public void addPurchase(@RequestBody Purchase purchase, HttpServletRequest request) throws Exception {
		System.out.println("----------------------------START PURSCHASE: --------------------------------");
		System.out.println(purchase);
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		purchasesController.addPurchase(purchase, userData.getId());
	}

//	@PostMapping
//	public void addPurchase(@RequestBody PurchaseDetailes purchaseDetailes, HttpServletRequest request) throws Exception {
//		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
//		purchasesController.addPurchase(purchaseDetailes, userData.getId());
//	}
	
	@PutMapping
	public void updateCouponPurchase(@RequestBody Purchase purchase, HttpServletRequest request) throws Exception {
		System.out.println("LALALA PURCHASE ID IS:" + purchase.getId());
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		purchasesController.updatePurchase(purchase, userData.getId());
	}
	
	@DeleteMapping("{purchaseId}")
	public void deletePurchase(@PathVariable("purchaseId") long purchaseId) throws Exception {
		purchasesController.deletePurchase(purchaseId);
	}

	@GetMapping("{purchaseId}")
	public Purchase getCouponPurchase(@PathVariable("purchaseId") long purchaseId) throws Exception {
		return purchasesController.getPurchase(purchaseId);
	}
	
	@GetMapping
	public List<Purchase> getAllPurchases() throws Exception {
		return purchasesController.getAllPurchases();
	}
	
	@GetMapping("/mypurchases")
	public List<Purchase> getAllPurchasesByCustomerId(HttpServletRequest request) throws Exception {
		UserLoginData userData = (UserLoginData) request.getAttribute("userLoginData");
		long customerId = userData.getId();
//		System.out.println(customerId);
		return purchasesController.getAllPurchasesByCustomerUserId(customerId);
	}
}
