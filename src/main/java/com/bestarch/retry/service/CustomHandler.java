package com.bestarch.retry.service;

import org.springframework.stereotype.Component;

import com.kohls.merch.poedihub.v2.bean.ErrorResponse;
import com.kohls.merch.poedihub.v2.handler.ComplexHandler;

@Component
public class CustomHandler implements ComplexHandler<ErrorResponse> {
	
	@Override
	public ErrorResponse handle(Exception exception) {
		System.out.println("Inside CustomHandler");
		com.kohls.merch.poedihub.v2.bean.ErrorResponse.Error error = new com.kohls.merch.poedihub.v2.bean.ErrorResponse.Error(
				"CUSTOM-ERR", exception.getMessage());
		ErrorResponse errResponse = new ErrorResponse();
		errResponse.setError(error);
		errResponse.setResponseCode("ERR");
		return errResponse;
	}
}
