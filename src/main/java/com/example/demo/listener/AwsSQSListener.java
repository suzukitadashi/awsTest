package com.example.demo.listener;

import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Greeting;
import com.example.demo.Queue;
import com.example.demo.SendObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AwsSQSListener {

	private final RestTemplate restTemplate;
	
	private ObjectMapper mapper;

	public AwsSQSListener(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.mapper = new ObjectMapper();
    }
	
	@SqsListener("foo-queue")
    public void loadMessageFromSQS(String message)  {
        log.info("message from SQS Queue {}",message);
        
		Greeting response1 = restTemplate.getForObject("http://localhost:8080/api1?name="+message, Greeting.class);
		log.info(response1.toString());
		
		Greeting response2 = restTemplate.getForObject("http://localhost:8080/api2", Greeting.class);
		log.info(response2.toString());

        
    }

//	@SqsListener("foo-queue.fifo")
	@SqsListener(Queue.TEST)
    public void loadMessageFromSQS2(String message) throws JsonMappingException, JsonProcessingException  {
        log.info("fifo message from SQS Queue {}",message);
        
        Map<String, Object> jsonMap = mapper.readValue(message, new TypeReference<Map<String, Object>>(){});
        
        log.info("message to map:{}", jsonMap);
        log.info("map(payload):{}", jsonMap.get("payload"));

        Map<String, Object> sendObj = mapper.readValue((String)jsonMap.get("payload"), new TypeReference<Map<String, Object>>(){});
        String kinoId = (String)sendObj.get("kinoId");
        String nyuryokuId = (String)sendObj.get("nyuryokuId");
        String soshinNichiji = (String)sendObj.get("soshinNichiji");
        log.info("map(payload).kinoId:{}", kinoId);
        log.info("map(payload).nyuryokuId:{}", nyuryokuId);
        log.info("map(payload).soshinNichiji:{}", soshinNichiji);
        
        SendObject sendObject = mapper.readValue((String)jsonMap.get("payload"), SendObject.class);
        log.info("SendObject:{}", sendObject);
        
        try {
    		Greeting response1 = restTemplate.postForObject("http://localhost:8080/api1", sendObject, Greeting.class);
//    		Greeting response1 = restTemplate.getForObject("http://localhost:8080/api1?name=aaa", Greeting.class);
    		log.info(response1.toString());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
		try {
			Greeting response2 = restTemplate.getForObject("http://localhost:8080/api2", Greeting.class);
			log.info(response2.toString());
		} catch (RestClientException e) {
			log.error("RestClientExceptionが発生", e);
		} catch (Exception e) {
			log.error("想定外のエラーが発生", e);
		}

        
    }

}
