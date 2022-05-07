package com.educative.springreactivepubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.spring.pubsub.core.publisher.PubSubPublisherTemplate;

@RestController
public class MessageRestController {

	@Autowired
	PubSubPublisherTemplate pubSubPublisherTemplate;

	private static final String PUB_SUB_TOPIC = "spring-reactive-topic";

	@PostMapping("/messages")
	public String publish(@RequestBody Message message) {
		this.pubSubPublisherTemplate.publish(PUB_SUB_TOPIC, message.getMsg());
		return "Message published successfully";
	}
}
