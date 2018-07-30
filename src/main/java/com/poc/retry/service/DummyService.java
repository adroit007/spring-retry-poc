package com.poc.retry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Abhishek Srivastava
 *
 */

@Service
public class DummyService {
	
	@Autowired
	ExternalService externalService;

	@Autowired
	RetryTemplate retryTemplate;

	@CircuitBreaker(maxAttempts = 3, openTimeout = 5000, resetTimeout = 14000, include = Exception.class)
	public String generateRandomUserId() {
		return retryTemplate.execute((context) -> {
			System.out.println("Inside doWithRetry method");
			return externalService.callExternal();
		});
	}
	
	/**
	 * Uncomment the following method for achieving only retry behavior without using Circuit Breaker pattern
	 * @param exp
	 * @return
	 */
	/*@Retryable(backoff=@Backoff(delay=2000), maxAttempts = 3, include = Exception.class)
	public String generateRandomUserId() {
		System.out.println("Inside doWithRetry method");
		return externalService.callExternal();
	}*/

	@Recover
	public String defaultUserId(Exception exp) {
		System.out.println("Inside defaultUserId method");
		return "123";
	}

}
