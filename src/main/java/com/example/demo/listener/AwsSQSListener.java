package com.example.demo.listener;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Greeting;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AwsSQSListener {

	private final RestTemplate restTemplate;

	public AwsSQSListener(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
	
	@SqsListener("foo-queue")
    public void loadMessageFromSQS(String message)  {
        log.info("message from SQS Queue {}",message);
        
		Greeting response1 = restTemplate.getForObject("http://localhost:8080/api1?name="+message, Greeting.class);
		log.info(response1.toString());
		
		Greeting response2 = restTemplate.getForObject("http://localhost:8080/api2", Greeting.class);
		log.info(response2.toString());

        
    }
	
}
