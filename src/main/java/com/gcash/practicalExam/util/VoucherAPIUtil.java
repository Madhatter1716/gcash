package com.gcash.practicalExam.util;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcash.practicalExam.responseDTO.VoucherDTO;

@Component
public class VoucherAPIUtil {

	public static String voucherURL;
	
	@Value("${app.voucherAPI}")
	public void setVoucherURL(String url) {
		voucherURL = url;
	}

	public static VoucherDTO checkVoucher() {

		RequestConfig.Builder requestBuilder = RequestConfig.custom();
		requestBuilder.setConnectionRequestTimeout(Timeout.ofMinutes(1));
		String voucherUrl = new StringBuilder().append(voucherURL).append("voucher/MYNT?key=apikey").toString();
		HttpGet httpGet = new HttpGet(voucherUrl);
		try (CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build())
				.build()) {
			try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.readValue(EntityUtils.toString(response.getEntity()), VoucherDTO.class);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
