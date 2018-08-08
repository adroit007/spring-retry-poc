package com.bestarch.retry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import com.kohls.merch.poedihub.v2.bean.Response;
import com.kohls.merch.poedihub.v2.exception.EdiHubException;
import com.kohls.merch.poedihub.v2.exception.EnableExceptionHandler;

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

	/**
	 * Annotate the target method with @EnableExceptionHandler(Handler). Handler
	 * should be implemented as per business needs and other requirements. In case
	 * no handler is supplied, <code>DefaultExceptionHandler </code> will be invoked
	 * 
	 * @return Response Response is always returned whether the call is successful
	 *         or not We need to check the 'error' variable to check what has
	 *         happened to the call.
	 */
	@EnableExceptionHandler(handler = CustomHandler.class)
	@CircuitBreaker(maxAttempts = 3, openTimeout = 5000, resetTimeout = 14000, include = Exception.class)
	public Response generateRandomUserId() {
		// this.sayHi();
		if (true)
			throw new EdiHubException("oops");
		return retryTemplate.execute((context) -> {
			System.out.println("Inside doWithRetry method");
			return new Response(externalService.callExternal(), false);
		});
	}

	/**
	 * Uncomment the following method for achieving only retry behavior without
	 * using Circuit Breaker pattern
	 * 
	 * @param exp
	 * @return
	 */
	/*
	 * @Retryable(backoff=@Backoff(delay=2000), maxAttempts = 3, include =
	 * Exception.class) public String generateRandomUserId() {
	 * System.out.println("Inside doWithRetry method"); return
	 * externalService.callExternal(); }
	 */

	@Recover
	public Response defaultUserId(Exception exp) {
		System.out.println("Inside defaultUserId method");
		return new Response("123", false);
	}

	/**
	 * Annotate the target method with @EnableExceptionHandler(Handler). Handler
	 * should be implemented as per business needs and other requirements. In case
	 * no handler is supplied, <code>DefaultExceptionHandler </code> will be invoked
	 * 
	 * @return Response Response is always returned whether the call is successful
	 *         or not We need to check the 'error' variable to check what has
	 *         happened to the call.
	 */
	@EnableExceptionHandler()
	public Response test() {
		System.out.println("Inside test() method");
		if (true)
			throw new EdiHubException("oops");
		return new Response("testing-abhishek", false);
	}

}
