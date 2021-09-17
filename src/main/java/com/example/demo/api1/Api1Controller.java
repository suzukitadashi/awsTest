package com.example.demo.api1;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Greeting;
import com.example.demo.SendObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class Api1Controller {

	private static final String template = "Hello, Api1, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@PostMapping(value="/api1", consumes = "application/json", produces = "application/json")
	public Greeting greeting(@RequestBody SendObject sendObj) {
		
		log.info("受信パラメータ:{}", sendObj);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new Greeting(counter.incrementAndGet(), String.format(template, sendObj.getKinoId()));
	}
}
