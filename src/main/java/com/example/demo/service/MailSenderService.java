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


public class MailSenderService implements IMailSenderService {

	private MailSender mailSender;
	private JavaMailSender javaMailSender;
	
	public MailSenderService(MailSender mailSender, JavaMailSender javaMailSender) {
		this.mailSender = mailSender;
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public void mailSender() {
		System.out.println("�ʏ탍�W�b�N");
		
		sendMailMessage();
		sendMailMessageWithAttachments();
	}

	private void sendMailMessage() {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ta-suzuki@tokyo-nii.com");
		message.setTo("ta-suzuki@tokyo-nii.com");
		message.setSubject("�e�X�g���[��");
		message.setText("����̓e�X�g���[���ł��B");

		
		this.mailSender.send(message);
        System.out.println("�ʏ탁�[�����M");
	}
	
	
    private void sendMailMessageWithAttachments() {

        this.javaMailSender.send(new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) 
                   throws Exception {
                  MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
                  helper.addTo("ta-suzuki@tokyo-nii.com");
                  helper.setFrom("ta-suzuki@tokyo-nii.com");
                  
                  InputStreamSource data = 
                           new ByteArrayResource("aaa������".getBytes());

                  helper.addAttachment("test.txt", data );
                  helper.setSubject("����͓Y�t�e�X�g���[���ł��B");
                  helper.setText("�����\n�e�X�g���[��\n�ł��B", false);
                }
            });
        
        System.out.println("�Y�t���[�����M");
     }
	
}
