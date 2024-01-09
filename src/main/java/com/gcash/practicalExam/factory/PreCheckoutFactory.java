package com.gcash.practicalExam.factory;

import org.springframework.stereotype.Component;

import com.gcash.practicalExam.responseDTO.PreCheckoutDTO;
import com.gcash.practicalExam.responseDTO.VoucherDTO;

@Component
public class PreCheckoutFactory {
	
	public PreCheckoutDTO entityToDTO(Double totalPrice, VoucherDTO voucherDTO) {
		return PreCheckoutDTO.builder().totalPrice(totalPrice).voucherCode(voucherDTO).build();
	}

}
