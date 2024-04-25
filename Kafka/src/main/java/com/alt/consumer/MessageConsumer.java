package com.alt.consumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.alt.producer.MessageProducer;
import com.alt.repository.MessageRepository;

public class MessageConsumer {

	Logger logger = LoggerFactory.getLogger(MessageProducer.class);
	
	@Autowired
    private MessageRepository messageRepository;	
	
	@KafkaListener(topics = "${myapp.kafka.topic}", groupId = "xyz")
	public void consume(String message) {
		logger.info("MESSAGE RECEIVED AT CONSUMER END -> "+ message);
		messageRepository.addMessage(message);
	}
}
