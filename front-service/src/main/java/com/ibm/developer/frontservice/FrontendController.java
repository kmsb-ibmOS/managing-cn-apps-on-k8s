package com.ibm.developer.frontservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FrontendController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FrontendController.class);
	private ApplicationContext context;
	private RestTemplate restTemplate = new RestTemplate();
	public FrontendController(ApplicationContext context) {
		this.context = context;
	}

	@GetMapping
	public String returnHelloMessage() throws InterruptedException {
		String podName = restTemplate.getForObject("http://backend-service-port:8080", String.class);
		return "Hello, " + podName;
	}

	@GetMapping("/setLivenessToFalse")
	public String setLivenessToFalse() {
		AvailabilityChangeEvent.publish(context, LivenessState.BROKEN);
		return "Application is now broken";
	}

	@GetMapping("/setReadinessToFalse")
	public String setReadinessToFalse() {
		LOGGER.info("In helloWorld");
		AvailabilityChangeEvent.publish(context, ReadinessState.REFUSING_TRAFFIC);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.currentThread().sleep(5000);
					AvailabilityChangeEvent.publish(context, ReadinessState.ACCEPTING_TRAFFIC);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		return "Application is now refusing traffic";
	}
}
