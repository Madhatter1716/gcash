package com.gcash.practicalExam.service;

import com.gcash.practicalExam.responseDTO.PreCheckoutDTO;
import com.gcash.practicalExam.responseDTO.VoucherDTO;

public interface PreCheckoutService {
	
	PreCheckoutDTO calculatePackagePrice(Double weight,Double height, 
										Double width, Double length,
										Double pricePerVolume, String voucherCode);
	VoucherDTO getVoucherDetails(String voucherCode);

}
