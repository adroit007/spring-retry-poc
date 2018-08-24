package com.bestarch.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import com.bestarch.framework.exception.EnableExceptionHandler;
import com.bestarch.framework.exception.bean.Response;
import com.bestarch.retry.service.DummyService;

/**
 * 
 * @author Abhishek Srivastava
 *
 */

@SpringBootApplication
@RestController
@EnableRetry
//@EnableAspectJAutoProxy
@EnableExceptionHandler
public class SpringRetryPocApplication {

	@Autowired
	DummyService service;

	public static void main(String[] args) {
		SpringApplication.run(SpringRetryPocApplication.class, args);

		ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.bestarch.retry");
		System.out.println(applicationContext.getId());
		DummyService transferService = applicationContext.getBean(DummyService.class);
		Response response = transferService.test();
		System.out.println(response);
	}

	@Bean
	@RequestScope
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RetryTemplate retryTemplate() {
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(3);

		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(2000); // milliseconds

		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setBackOffPolicy(backOffPolicy);
		return template;
	}

	/**
	 * How to use exception handling framework via REST call [in Spring context]
	 * 
	 * @return
	 */
	@GetMapping(value = "/userid", produces = { "text/html", "application/json", "application/xml" })
	public ResponseEntity<Response> generateRandomUserId() {
		Response response = service.generateRandomUserId();
		if (response.isError()) {
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	
	/**
	 * How to use exception handling framework via REST call [in Spring context]
	 * 
	 * @return
	 */
	@GetMapping(value = "/userid2", produces = { "text/html", "application/json", "application/xml" })
	public ResponseEntity<Response> generateRandomUserId2() {
		Response response = service.generateRandomUserId();
		if (response.isError()) {
			return new ResponseEntity<Response>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
