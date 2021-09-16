package com.example.demo.api1;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Greeting;

@RestController
public class Api1Controller {

	private static final String template = "Hello, Api1, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/api1")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
