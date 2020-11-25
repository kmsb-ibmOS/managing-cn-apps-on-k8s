package com.ibm.developer.frontservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FrontendController {

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping
	public String returnHelloMessage() throws InterruptedException {
		String podName = restTemplate.getForObject("http://backend-service-port:8080", String.class);
		return "Hello, " + podName;
	}
}
