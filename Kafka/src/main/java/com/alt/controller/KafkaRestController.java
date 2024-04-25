package com.alt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alt.producer.MessageProducer;
import com.alt.repository.MessageRepository;
import com.alt.utility.KafkaConsumers;

@RestController
@RequestMapping("/v1")
public class KafkaRestController {

	@Autowired
	private MessageProducer messageProducer;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired 
	private KafkaConsumers kafkaConsumers; 
	
	@GetMapping("/send")
	public String sendMsg(@RequestParam("msg")String message){
		messageProducer.sendMessage(message);
	    return message+" sent successfully!";
	}
	
	@GetMapping("/getAll")
	public String getAllMessages() {
		return messageRepository.getAllMessages();
	}
	
	@GetMapping("/getData")
	public void consumeDataFromKafka() {
		kafkaConsumers.kafkaConsumers();
	}
}
