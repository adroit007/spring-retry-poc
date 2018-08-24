package com.bestarch.retry.service;

import org.springframework.stereotype.Component;

import com.bestarch.framework.exception.bean.ErrorResponse;
import com.bestarch.framework.exception.handler.SimpleHandler;

@Component
public class CustomHandler2 implements SimpleHandler {
	
	@Override
	public void handle(Exception exception) {
		System.out.println("Inside CustomHandler");
		ErrorResponse.Error error = new ErrorResponse.Error("CUSTOM-ERR", exception.getMessage());
		ErrorResponse errResponse = new ErrorResponse();
		errResponse.setError(error);
		errResponse.setResponseCode("ERR");
	}
}
