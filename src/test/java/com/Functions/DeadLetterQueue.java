package com.Functions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import com.azure.messaging.servicebus.*;
import com.azure.messaging.servicebus.ServiceBusReceiverClient.*;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.azure.messaging.servicebus.models.SubQueue;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClient.*;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.QueueMessageItem;
import com.azure.storage.queue.models.QueueMessageItem.*;
import com.azure.messaging.servicebus.ServiceBusClientBuilder.*;
import com.azure.messaging.servicebus.ServiceBusClientBuilder.ServiceBusReceiverClientBuilder.*;

public class DeadLetterQueue
{

	static String connectionString = "connection String of the Service Bus which needs to be automated";
	static String topicName = "topic-name(which message needs to be sent)";
	static String queueName = "queue-name(which message needs to be sent)";
	static String subscriptionName = "subscription-name of Topic/Queue";
	
	
	
	public static  void ReceiveMessageFromDLQ() throws IOException, NullPointerException
	{
		System.out.println("We are in receive messages DLQ");
		ServiceBusReceiverClient receiver = new ServiceBusClientBuilder()
			.connectionString(connectionString)
			.receiver() // Use this for session or non-session enabled queue or topic/subscriptions
			.topicName(topicName)
			.subscriptionName(subscriptionName)
			.subQueue(SubQueue.DEAD_LETTER_QUEUE)
			.receiveMode(ServiceBusReceiveMode.RECEIVE_AND_DELETE)
			.buildClient();
		System.out.println("Reason for the Message to go to DeadLetterQueue :  ");
		System.out.println(receiver.peekMessage().getDeadLetterReason());
	   

		//Select any one option either writing message into the file or printing the message into the console
		
		
		//Writing the message into a file 
		
		/*
		FileWriter myWriter = new FileWriter("output/DLQOutput.json");
	      myWriter.write(receiver.peekMessage().getBody().toString());
	      myWriter.close();
		*/
	      
		//Printing / receiving the message into the console
	      
	   //System.out.printf(receiver.peekMessage().getBody().toString());
s
	}
	
	
	public static void main(String[] args) throws NullPointerException, IOException 
	{
		 
	}

}
