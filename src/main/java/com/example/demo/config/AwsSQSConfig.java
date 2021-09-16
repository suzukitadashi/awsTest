package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AwsSQSConfig {

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${cloud.aws.credentials.access-key}")
	private String awsAccessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String awsSecretKey;

	@Value("${aws.endpoint}")
	private String endPoint;

	@Bean
	public QueueMessagingTemplate queueMessagingTemplate() {
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

	@Primary
	@Bean
	public AmazonSQSAsync amazonSQSAsync() {

		log.info("region:{}, awsAccessKey:{}, awsSecretKey:{}", region, awsAccessKey, awsSecretKey);

		var credentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey));

		AmazonSQSAsync async = AmazonSQSAsyncClientBuilder.standard().withCredentials(credentialsProvider)
				.withEndpointConfiguration(new EndpointConfiguration(endPoint, region)).build();

		return async;
	}
}
