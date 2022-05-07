package com.educative.springreactivepubsub;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.cloud.spring.pubsub.reactive.PubSubReactiveFactory;
import com.google.cloud.spring.pubsub.support.AcknowledgeablePubsubMessage;

import reactor.core.publisher.Flux;

@Controller
@ResponseBody
public class MessageReactiveController {

	@Autowired
	PubSubReactiveFactory reactiveFactory;
	
	private static final String PUB_SUB_SUBSCRIPTION = "spring-reactive-subscription";

	@GetMapping(value = "/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> messages() {
		Flux<AcknowledgeablePubsubMessage> flux = this.reactiveFactory.poll(PUB_SUB_SUBSCRIPTION, 1000);
		return flux.doOnNext(message -> {
			System.out.println("Message received successfully and a Message ID is: " + message.getPubsubMessage().getMessageId());
			message.ack();
		}).map(message -> new String(message.getPubsubMessage().getData().toByteArray(), Charset.defaultCharset()));
	}
}
