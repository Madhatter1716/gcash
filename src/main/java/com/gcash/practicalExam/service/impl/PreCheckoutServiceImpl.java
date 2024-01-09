package com.gcash.practicalExam.service.impl;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gcash.practicalExam.exception.NullVolumeException;
import com.gcash.practicalExam.exception.OverWeightException;
import com.gcash.practicalExam.factory.PreCheckoutFactory;
import com.gcash.practicalExam.responseDTO.PreCheckoutDTO;
import com.gcash.practicalExam.responseDTO.VoucherDTO;
import com.gcash.practicalExam.service.PreCheckoutService;
import com.gcash.practicalExam.util.VoucherAPIUtil;

@Component
public class PreCheckoutServiceImpl implements PreCheckoutService {

	@Value("${app.heavyParcelPrice}")
	Double heavyParcelPrice;

	@Value("${app.smallParcelPrice}")
	Double smallParcelPrice;

	@Value("${app.mediumParcelPrice}")
	Double mediumParcelPrice;

	@Value("${app.largeParcelPrice:0.05}")
	Double largeParcelPrice;

	@Value("${app.overWeightLimit:50}")
	Double overWeightLimit;

	@Value("${app.heavyWeightLimit:10}")
	Double heavyWeightLimit;

	@Value("${app.smallVolumeLimit:1500}")
	Double smallVolumeLimit;

	@Value("${app.mediumVolumeLimit:2500}")
	Double mediumVolumeLimit;

	@Autowired
	VoucherAPIUtil voucherAPIUtil;
	
	@Autowired
	PreCheckoutFactory preCheckoutFactory;
	
	String pattern = "yyyy-MM-dd";
	
	/**
	 * Expectation for params : 1. Weight is always in kg 2. height, width and
	 * length is in cm 3. pricePerVolume will override default price for specific
	 * packageRule
	 */

	@Override
	public PreCheckoutDTO calculatePackagePrice(Double weight, Double height, Double width, Double length,
			Double pricePerVolume, String voucherCode) {
		
		Double defaultPrice = 0.0;
		Double packageVolume = 0.0;

		if (Objects.nonNull(weight) && weight > 10) {
			if (weight > overWeightLimit) {
				throw new OverWeightException("This package is rejected because it exceeds weight limit.");
			} else if (weight > heavyWeightLimit) {
				packageVolume = weight;
				defaultPrice = heavyParcelPrice;
			}
		} else {
			packageVolume = getPackageVolume(height, width, length);
			if (packageVolume < smallVolumeLimit) {
				defaultPrice = smallParcelPrice;
			} else if (packageVolume < mediumVolumeLimit) {
				defaultPrice = mediumParcelPrice;
			} else {
				defaultPrice = largeParcelPrice;
			}
		}

		if (Objects.nonNull(pricePerVolume)) {
			defaultPrice = pricePerVolume;
		}
		
		
		VoucherDTO voucherObj = VoucherAPIUtil.checkVoucher();
		Double totalPrice = calculatePrice(defaultPrice, packageVolume, voucherObj);
		return preCheckoutFactory.entityToDTO(totalPrice, voucherObj);
	}

	private Double getPackageVolume(Double height, Double width, Double length) {
		if (Objects.isNull(height) || Objects.isNull(width) || Objects.isNull(length)) {
			throw new NullVolumeException("Height, Width or Length should not be null");
		}
		return height * width * length;
	}

	/**
	 * Checks whether has voucher and voucher is still valid
	 * also voucher discount is in percentage
	 */
	private Double calculatePrice(Double price, Double parcelValue, VoucherDTO voucherDetails) {
		System.out.println(voucherDetails.getExpiry());
		Double priceWithoutDiscount = price * parcelValue;
		if(Objects.isNull(voucherDetails)) {
			return priceWithoutDiscount;
		} else {
			Instant voucherExpiry = LocalDate.parse(voucherDetails.getExpiry()).atStartOfDay().toInstant(ZoneOffset.UTC);
			Instant today = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
			if(voucherExpiry.isBefore(today)) {
				return priceWithoutDiscount;
			}
			double discount = (priceWithoutDiscount * (Double.valueOf(voucherDetails.getDiscount()) / 100));
			return priceWithoutDiscount - discount;
		}

	}

	//mocked voucherAPI since its always getting a requestTimedout response
	@Override
	public VoucherDTO getVoucherDetails(String voucherCode) {
		Instant yearFromNow = Instant.now().plus(Duration.ofDays(150));
		Instant tenDaysAgo = Instant.now().minus(Duration.ofDays(10));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		long randomSecondsSinceEpoch = ThreadLocalRandom
			      .current()
			      .nextLong(tenDaysAgo.getEpochSecond(), yearFromNow.getEpochSecond());
		String dateToString = simpleDateFormat.format(new Date(randomSecondsSinceEpoch * 1000));
		return VoucherDTO.builder().code(voucherCode).discount(new Random().nextInt(100 - 1 + 1) + 1).expiry(dateToString)
		.build();
		
	}

}
