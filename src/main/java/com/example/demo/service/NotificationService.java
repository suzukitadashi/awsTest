package com.example.demo.service;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private MailSender mailSender;
	private JavaMailSender javaMailSender;
	
	public NotificationService(MailSender mailSender, JavaMailSender javaMailSender) {
		this.mailSender = mailSender;
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMailMessage(final SimpleMailMessage simpleMailMessage) {
		this.mailSender.send(simpleMailMessage);
        System.out.println("通常メール送信");
	}
	
	
    public void sendMailMessageWithAttachments() {

        this.javaMailSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) 
                   throws Exception {
                  MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
                  helper.addTo("ta-suzuki@tokyo-nii.com");
                  helper.setFrom("ta-suzuki@tokyo-nii.com");
                  
                  InputStreamSource data = 
                           new ByteArrayResource("aaaあああ".getBytes());

                  helper.addAttachment("test.txt", data );
                  helper.setSubject("これは添付テストメールです。");
                  helper.setText("これは\nテストメール\nです。", false);
                }
            });
        
        System.out.println("添付メール送信");
     }
}
