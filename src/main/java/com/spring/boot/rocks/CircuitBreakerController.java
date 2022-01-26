package com.spring.boot.rocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CircuitBreakerController {

	Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/getInvoiceCircuitBreaker")
	@CircuitBreaker(name = "getInvoiceCB", fallbackMethod = "getInvoiceFallback")
	public String getInvoiceCB() {
		logger.info("getInvoice() call starts here");
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8000/api/appUsers", String.class);
		logger.info("Response :" + entity.getStatusCode());
		return entity.getBody();
	}

	public String getInvoiceFallback(Exception e) {
		logger.info("---RESPONSE FROM FALLBACK METHOD---");
		return "SERVICE IS DOWN, PLEASE TRY AFTER SOMETIME !!!";
	}
}
