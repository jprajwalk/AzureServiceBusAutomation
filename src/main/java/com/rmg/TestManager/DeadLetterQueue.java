package com.rmg.TestManager;

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
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
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

	static String connectionString = "Endpoint=sb://rmg-we-bigpapi-dev-sb-01.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=oeDD369qdXEBME7glf0aEAgPa8liE9uXXb7cJ3d5ra4=";
	static String topicName = "rmg-we-bigpapi-dev-topic";
	static String queueName = "rmg-we-bigpapi-dev-queue";
	static String subscriptionName = "rmg-we-bigpapi-dev-sub";
	
	
	
	public static  void ReceiveMessageFromDLQ() throws IOException, NullPointerException
	{
		System.out.println("13");
		System.out.println("==========================================================================");
	    System.out.println("Receive Message From DLQ Block started");
	    System.out.println("==========================================================================");
	    
		System.out.println("We are in receive messages DLQ");
		ServiceBusReceiverClient receiver = new ServiceBusClientBuilder()
			.connectionString(connectionString)
			.receiver() // Use this for session or non-session enabled queue or topic/subscriptions
			.topicName(topicName)
			.subscriptionName(subscriptionName)
			.subQueue(SubQueue.DEAD_LETTER_QUEUE)
			.receiveMode(ServiceBusReceiveMode.RECEIVE_AND_DELETE)
			.buildClient();
		System.out.println("==========================================================================");
		System.out.println("**************************************************************************");
		System.out.println("Reason for the Message to go to DeadLetterQueue :  ");
		System.out.println(receiver.peekMessage().getDeadLetterReason());
	    System.out.println("**************************************************************************");
		System.out.println("==========================================================================");
	    
		
		System.out.println("14");
		
		

//Select any one option either writing message into the file or printing the message into the console
		
		
	//Writing the message into a file 
		
		/*
		FileWriter myWriter = new FileWriter("output/DLQOutput.json");
	      myWriter.write(receiver.peekMessage().getBody().toString());
	      myWriter.close();
		*/
	      
	//Printing / receiving the message into the console
	      
	   //System.out.printf(receiver.peekMessage().getBody().toString());
		
	      
	      
	    
		System.out.println("16");
		System.out.println("==========================================================================");
	    System.out.println("Receive Message From DLQ Block Ended");
	    System.out.println("==========================================================================");
	    
	}
	
	
	public static void main(String[] args) throws NullPointerException, IOException 
	{
		 
	}

}
