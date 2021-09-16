package com.example.demo.config;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.example.demo.service.IMailSenderService;
import com.example.demo.service.MailSenderService;
import com.example.demo.service.TestMailSenderService;

import io.awspring.cloud.ses.SimpleEmailServiceJavaMailSender;
import io.awspring.cloud.ses.SimpleEmailServiceMailSender;

@Configuration
public class AwsSesMailConfig {

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${cloud.aws.credentials.access-key}")
	private String awsAccessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String awsSecretKey;

	@Value("${aws.endpoint}")
	private String endPoint;
	
	@Value("${spring.profiles.active}")
	private String activeMode;

	@Bean
	public AmazonSimpleEmailService amazonSimpleEmailService() {

		var credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));

		return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(credentialsProvider).withEndpointConfiguration(new EndpointConfiguration(endPoint, region))
				.build();
	}

	@Bean
	public MailSender mailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
		return new SimpleEmailServiceMailSender(amazonSimpleEmailService);
	}

	@Bean
	public JavaMailSender javaMailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
		return new SimpleEmailServiceJavaMailSender(amazonSimpleEmailService);
	}
	
	@Bean
	
	public IMailSenderService mailSenderService(MailSender mailSender, JavaMailSender javaMailSender) {
	
		System.out.println("activeÉÇÅ[ÉhÇÕ" + activeMode + "Ç≈Ç∑");
		
		if(Objects.equals(activeMode, "dev")) {
			return new TestMailSenderService(mailSender, javaMailSender);
		} else {
			return new MailSenderService(mailSender, javaMailSender);
		}
		
	}
	
}
