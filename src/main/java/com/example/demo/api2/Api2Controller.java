package com.example.demo.api2;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Greeting;

@RestController
public class Api2Controller {

	private static final String template = "Hello, Api2, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/api2")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

		
		throw new IllegalStateException("aaa");
//		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}
