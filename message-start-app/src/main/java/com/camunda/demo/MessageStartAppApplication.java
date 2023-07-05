package com.camunda.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources = "classpath:message-start.bpmn")
public class MessageStartAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MessageStartAppApplication.class, args);
	}

	@Autowired
	private ZeebeClient client;

	@Override
	public void run(String... args) throws Exception {

		final Map<String, Object> variables = new HashMap<String, Object>();
		
		variables.put("name", "Praveen Kumar reddy Reddammagari");
		variables.put("age", Integer.valueOf("22"));

		String messageId = "message-1";

		client.newPublishMessageCommand().messageName("Message-Received").correlationKey(messageId).variables(variables)
				.send().exceptionally(throwable -> {
					throw new RuntimeException("Could not publish message", throwable);
				});
	}

}
