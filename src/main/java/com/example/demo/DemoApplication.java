package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.service.IMailSenderService;
import com.example.demo.service.NotificationService;

@SpringBootApplication
public class DemoApplication  {

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	IMailSenderService mailSenderService;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
}
