package com.ibm.developer.backendservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

	private String podName;

	public BackendController(@Value("${MY_POD_NAME}") String podName) {
		this.podName = podName;
	}

	@GetMapping
	public String getPodName() throws InterruptedException {
		return podName;
	}

}
