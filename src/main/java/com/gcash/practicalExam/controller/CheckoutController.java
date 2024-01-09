package com.gcash.practicalExam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcash.practicalExam.service.PreCheckoutService;

@RestController	
@RequestMapping("/api")
public class CheckoutController {
	
	@Autowired
	PreCheckoutService preCheckoutService;
	
	
	@GetMapping("/preCheckout")
	public ResponseEntity<Object> getPackageCost(
			@RequestParam(value="weight", required = false) Double weight,
			@RequestParam(value="height", required = false) Double height,
			@RequestParam(value="width", required = false) Double width,
			@RequestParam(value="length", required = false) Double length,
			@RequestParam(value="price", required = false) Double pricePerVolume,
			@RequestParam(value="voucherCode", required = false) String voucherCode){
			
		return ResponseEntity.ok(preCheckoutService.calculatePackagePrice(weight, height, width, length, pricePerVolume, voucherCode));
	}
}
