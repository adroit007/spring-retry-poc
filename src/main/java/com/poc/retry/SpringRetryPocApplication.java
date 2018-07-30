package com.poc.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

import com.poc.retry.service.DummyService;
/**
 * 
 * @author Abhishek Srivastava
 *
 */

@SpringBootApplication
@RestController
@EnableRetry
public class SpringRetryPocApplication {
	
	@Autowired
	DummyService service;

	public static void main(String[] args) {
		SpringApplication.run(SpringRetryPocApplication.class, args);
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
	
	@GetMapping(value = "/userid", produces= {"text/html", "application/json", "application/xml"})
	public ResponseEntity<String> generateRandomUserId() {
		return new ResponseEntity<String>(service.generateRandomUserId(), HttpStatus.OK);
	}

}
