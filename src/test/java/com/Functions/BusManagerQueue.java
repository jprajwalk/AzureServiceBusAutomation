package com.Functions;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;
import org.testng.reporters.Files;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

public class BusManagerQueue 
{
	static String connectionString = "connection String of the Service Bus which needs to be automated";
	static String topicName = "topic-name(which message needs to be sent)";
	static String queueName = "queue-name(which message needs to be sent)";
	static String subscriptionName = "subscription-name of Topic/Queue";
	
	
	/**
     * Rigorous Test :-)
     * @throws Throwable 
     */
  @Test  
	public void sendMessage(String filePath) throws InterruptedException
	{
		 // create a Service Bus Sender client for the queue 
	    ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
	            .connectionString(connectionString)
	            .sender()
	          //.topicName(topicName)
	            .topicName(queueName)
	            .buildClient();
	    	File fl = new File(filePath);
	    
	    	try 
			{
				senderClient.sendMessage(new ServiceBusMessage((Files.readFile(fl))));
				Thread.sleep(10000);
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	        System.out.println("Sent a single message to the topic: " + topicName);       
	}
	
	
	
	public List<ServiceBusMessage> createMessages()
	{
		System.out.println("3.1");
		System.out.println("==========================================================================");
	    System.out.println("Send Message List Block of Queue start");
	    System.out.println("==========================================================================");
	    // create a list of messages and return it to the caller
	    ServiceBusMessage[] messages = {
	    		new ServiceBusMessage("First message"),
	    		new ServiceBusMessage("Second message"),
	    		new ServiceBusMessage("Third message")
	    };
	    System.out.println("4.1");
	    System.out.println("==========================================================================");
	    System.out.println("Send Message List Block of Queue Ended");
	    System.out.println("==========================================================================");
	    
	    return Arrays.asList(messages);
	    
	    
	}
	
	public void sendMessageBatch()
	{
		System.out.println("Send Message Batch Block of Queue start");
		
	    // create a Service Bus Sender client for the queue 
	    ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
	            .connectionString(connectionString)
	            .sender()
	          //.topicName(topicName)
	            .queueName(queueName)
	            .buildClient();

	    // Creates an ServiceBusMessageBatch where the ServiceBus.
	    ServiceBusMessageBatch messageBatch = senderClient.createMessageBatch();        

		// create a list of messages
	    List<ServiceBusMessage> listOfMessages = createMessages();

	    // We try to add as many messages as a batch can fit based on the maximum size and send to Service Bus when
	    // the batch can hold no more messages. Create a new batch for next set of messages and repeat until all
	    // messages are sent.        
	    for (ServiceBusMessage message : listOfMessages) {
	        if (messageBatch.tryAddMessage(message)) {
	            continue;
	        }

	        // The batch is full, so we create a new batch and send the batch.
	        senderClient.sendMessages(messageBatch);
	        System.out.println("Sent a batch of messages to the queue: " + queueName);

	        // create a new batch
	        messageBatch = senderClient.createMessageBatch();

	        // Add that message that we couldn't before.
	        if (!messageBatch.tryAddMessage(message)) {
	            System.err.printf("Message is too large for an empty batch. Skipping. Max size: %s.", messageBatch.getMaxSizeInBytes());
	        }
	    }

	    if (messageBatch.getCount() > 0) {
	        senderClient.sendMessages(messageBatch);
	        System.out.println("Sent a batch of messages to the queue: " + queueName);
	    }
	    System.out.println("Send Message Batch Block of Queue ended");
	    
	    //close the client
	    senderClient.close();
	}
	
	// handles received messages
	public static void receiveMessages() throws InterruptedException
	{
		CountDownLatch countdownLatch = new CountDownLatch(1);

	    // Create an instance of the processor through the ServiceBusClientBuilder
	    ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
	        .connectionString(connectionString)
	        .processor()
	      //.topicName(topicName)
	        .queueName(queueName)
	        .subscriptionName(subscriptionName)
	        .processMessage(t -> {
				try {
					processMessage(t);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			})
	        .processError(context -> processError(context, countdownLatch))
	        .buildProcessorClient();
	     
	   
	    System.out.println("Starting the processor");
	    processorClient.start();

	    TimeUnit.SECONDS.sleep(10);
	    System.out.println("Stopping and closing the processor");
	    processorClient.close(); 
	    
	}   
	
	private static void processMessage(ServiceBusReceivedMessageContext context) throws IOException 
	{
		ServiceBusReceivedMessage message = context.getMessage();
    
	    System.out.printf("Processing message. Session: %s, Sequence #: %s. Contents: %s%n", message.getMessageId(),
	        message.getSequenceNumber(), message.getBody());
	       
		//Writing message into file ReceiveOutput.json
	      FileWriter myWriter = new FileWriter("output/ReceiveOutput.json");
	      myWriter.write(context.getMessage().getBody().toString());
	      myWriter.close();
	      System.out.println("The Queue Message has been stored in file");  

	}

	private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) 
	{
		
		System.out.println("Process Error Message Block of Queue started");
		
	    System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
	        context.getFullyQualifiedNamespace(), context.getEntityPath());

	    if (!(context.getException() instanceof ServiceBusException)) {
	        System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
	        return;
	    }

	    ServiceBusException exception = (ServiceBusException) context.getException();
	    ServiceBusFailureReason reason = exception.getReason();

	    if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
	        || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
	        || reason == ServiceBusFailureReason.UNAUTHORIZED) {
	        System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
	            reason, exception.getMessage());

	        countdownLatch.countDown();
	    } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
	        System.out.printf("Message lock lost for message: %s%n", context.getException());
	    } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
	        try {
	            // Choosing an arbitrary amount of time to wait until trying again.
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	            System.err.println("Unable to sleep for period of time");
	        }
	    } else {
	        System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
	            reason, context.getException());
	    }
	   
	    System.out.println("Process Error Message Block of Queue Ended");
	}  
	
	
	public static void main( String[] args )
    {
        
        
    }
}
