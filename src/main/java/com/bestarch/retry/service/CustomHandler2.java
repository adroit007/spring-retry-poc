package com.bestarch.retry.service;

import org.springframework.stereotype.Component;

import com.kohls.merch.poedihub.v2.bean.ErrorResponse;
import com.kohls.merch.poedihub.v2.handler.SimpleHandler;

@Component
public class CustomHandler2 implements SimpleHandler {
	
	@Override
	public void handle(Exception exception) {
		System.out.println("Inside CustomHandler");
		com.kohls.merch.poedihub.v2.bean.ErrorResponse.Error error = new com.kohls.merch.poedihub.v2.bean.ErrorResponse.Error("CUSTOM-ERR", exception.getMessage());
		ErrorResponse errResponse = new ErrorResponse();
		errResponse.setError(error);
		errResponse.setResponseCode("ERR");
	}
}
