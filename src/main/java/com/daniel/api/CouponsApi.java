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
import com.daniel.data.UserLoginData;
import com.daniel.exceptions.ApplicationException;
import com.daniel.entities.Coupon;
import com.daniel.controllers.CouponsController;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {

	@Autowired
	private CouponsController couponsController;
	
	@PostMapping
	public void addCoupon(HttpServletRequest request, @RequestBody Coupon coupon) throws ApplicationException {
		//System.out.println(coupon.getCompanyId());
		System.out.println(coupon);
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		couponsController.addCoupon(coupon, userLoginData);
	}

	@PutMapping
	public void updateCoupon(HttpServletRequest request, @RequestBody Coupon coupon) throws ApplicationException {
		UserLoginData userLoginData = (UserLoginData) request.getAttribute("userLoginData");
		couponsController.updateCoupon(coupon, userLoginData);
		System.out.println("coupon " + coupon.getId() + " updated");
	}
	
	@DeleteMapping("{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		couponsController.deleteCoupon(couponId);
		System.out.println("Coupon has been deleted");
	}

	@GetMapping("{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		return couponsController.getCoupon(couponId);
	}

	@GetMapping
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return couponsController.getAllCoupons();
	}
}
