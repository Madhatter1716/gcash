package com.gcash.practicalExam.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PreCheckoutDTO {
	
	private Double totalPrice;
	private VoucherDTO voucherCode;

}
