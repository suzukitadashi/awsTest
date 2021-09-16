package com.example.demo.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;


public class TestMailSenderService implements IMailSenderService {

//	private MailSender mailSender;
//	private JavaMailSender javaMailSender;
	
	public TestMailSenderService(MailSender mailSender, JavaMailSender javaMailSender) {
//		this.mailSender = mailSender;
//		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public void mailSender() {
		System.out.println("開発ロジック");
	}

}
