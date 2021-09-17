package com.example.demo.sqsSend;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDateTime;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.SendObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import com.amazonaws.services.sqs.model.Message;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SqsSendController {

	private static final String TEST_QUEUE = "foo-queue.fifo";
	
	private static final String MESSAGE_GROUP_ID = "SBS_MESSAGE_GROUP";
	
	private QueueMessagingTemplate messagingTemplate;

	private ObjectMapper mapper;
	
	public SqsSendController(QueueMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
		this.mapper = new ObjectMapper();
	}
	
	@GetMapping("/sqsSend")
	public String greeting() throws JsonProcessingException {

//		LocalDateTime ldt = LocalDateTime.now();
//		
		SendObject obj = new SendObject("ABC001", "20210917000000000");
		String message = mapper.writeValueAsString(obj);
		
		Message<String> msg = MessageBuilder.withPayload(message).
//				setHeader("message-group-id", MESSAGE_GROUP_ID).
				build();
		
		//setHeaderでmessageGroupIdを設定しても、MessageGroupIdがパラメータに無いと言われる
		//Mapでheader要素を設定して渡すと正常に動く
		Map<String, Object> headers = new HashMap<>();
	    headers.put("message-group-id", MESSAGE_GROUP_ID);
		
		messagingTemplate.convertAndSend(TEST_QUEUE, msg, headers);
		
		return "success";
	}
}
