package com.gcash.practicalExam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcash.practicalExam.service.PreCheckoutService;

@RestController	
public class VoucherController {
	
	@Autowired
	PreCheckoutService preCheckoutService;
	
	
	@GetMapping("/voucher/{voucherCode}")
	public ResponseEntity<Object> mockVoucherAPI(
			@PathVariable String voucherCode,
			@RequestParam(value="key") String apiKey){
			
		return ResponseEntity.ok(preCheckoutService.getVoucherDetails(voucherCode));
	}

}
