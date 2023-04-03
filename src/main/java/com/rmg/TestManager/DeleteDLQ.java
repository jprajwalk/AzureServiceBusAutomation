package com.rmg.TestManager;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.OffsetDateTime;


import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClient;
import com.azure.messaging.servicebus.administration.ServiceBusAdministrationClientBuilder;
import com.azure.messaging.servicebus.administration.models.CorrelationRuleFilter;
import com.azure.messaging.servicebus.administration.models.CreateRuleOptions;
import com.azure.messaging.servicebus.administration.models.FalseRuleFilter;
import com.azure.messaging.servicebus.administration.models.SqlRuleAction;
import com.azure.messaging.servicebus.administration.models.SqlRuleFilter;
import com.azure.messaging.servicebus.administration.models.TrueRuleFilter;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.azure.messaging.servicebus.models.SubQueue;

import reactor.core.publisher.Mono;
public class DeleteDLQ 
{
	
	static String connectionString = "Endpoint=sb://rmg-we-bigpapi-dev-sb-01.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=KfPbjWkNfcvPEEj0xA0z0aiYMawvUgjg7cfLqx0gmpg=";
	static String endpoint = "sb://rmg-we-bigpapi-dev-sb-01.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=KfPbjWkNfcvPEEj0xA0z0aiYMawvUgjg7cfLqx0gmpg=";
	static String topicName = "rmg-we-bigpapi-dev-topic";
	static String queueName = "rmg-we-bigpapi-dev-queue";
	static String subscriptionName = "rmg-we-bigpapi-dev-sub";
	static String creds = "rmg-we-bigpapi-dev-sb-01.servicebus.windows.net";
	
	
	public void deleteMessage()
	{
		System.out.println("21");
		
		Mono<Void> consumer = new ServiceBusClientBuilder()
		     .connectionString(connectionString)
		     .receiver()
		     .topicName(topicName)
		     .subscriptionName(subscriptionName)
			 .subQueue(SubQueue.DEAD_LETTER_QUEUE)
			 .receiveMode(ServiceBusReceiveMode.RECEIVE_AND_DELETE)
		     .buildAsyncClient().deadLetter(null, null);

		
		System.out.println("22");
	/*
		ServiceBusReceiverAsyncClient receiver = new ServiceBusClientBuilder().
			     .credential(,
			         new DefaultAzureCredentialBuilder().build())
			     .receiver()
			     .topicName(topicName)
			     .subscriptionName(subscriptionName)
				 .subQueue(SubQueue.DEAD_LETTER_QUEUE)
				 .receiveMode(ServiceBusReceiveMode.RECEIVE_AND_DELETE)
			     .buildAsyncClient();
	
		System.out.println("23");
		
		 Disposable subscription = receiver.receiveMessages()
			     .subscribe(message -> {
			         System.out.printf("Received Seq #: %s%n", message.getSequenceNumber());
			         System.out.printf("Contents of message as string: %s%n", message.getBody().toString());
			     }, error -> System.err.print(error));
		 */
		 System.out.println("24");
		
	}
	
	
	public static void main(String[] args) 
		{
			
		}

	 
}
