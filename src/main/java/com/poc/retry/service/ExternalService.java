package com.poc.retry.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Abhishek Srivastava
 *
 */

@Service
public class ExternalService {

	@Autowired
	RestTemplate restTemplate;

	public String callExternal() {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		List<MediaType> list = new ArrayList<>();
		list.add(MediaType.APPLICATION_JSON);
		headers.setAccept(list);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate
				.exchange("http://localhost:8081/number/random", HttpMethod.GET, entity, String.class);
		return response.getBody();

	}

}
