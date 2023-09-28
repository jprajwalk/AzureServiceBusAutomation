package com.rmg.TestManager;
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

public class BusManagerTopic 
{
	static String connectionString = "Endpoint=sb://rmg-we-bigpapi-dev-sb-01.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=r7arJdyC7bU4ygzMqAdbsddPH3sTnySyLMUZzX4kG/E=";
	static String topicName = "rmg-we-bigpapi-dev-intersoft-topic-01";
	static String subscriptionName = "rmg-we-bigpapi-dev-intersoft-sub-01";
	
	
	/**
     * Rigorous Test :-)
     * @throws Throwable 
     */
  @Test 
	public void sendMessage(String filePath) throws Throwable  
	{
		System.out.println("1.2");
		System.out.println("==========================================================================");
	    System.out.println("Send Message Block of Topic start");
	    System.out.println("==========================================================================");
	    // create a Service Bus Sender client for the queue 
	    ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
	            .connectionString(connectionString)
	            .sender()
	            .topicName(topicName)
	            .buildClient();

	    	File fl = new File(filePath);
	    	
	    	try {
				senderClient.sendMessage(new ServiceBusMessage((Files.readFile(fl))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} System.out.println("2.2");
	    
	    System.out.println("==========================================================================");
	    System.out.println("Send Message Block of Topic ended");
	    System.out.println("==========================================================================");
	    System.out.println("Sent a single message to the topic: " + topicName);        
	}
	
	

	private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) 
	{
		System.out.println("11.2");
		System.out.println("Process Error Message Block of Topic started");
		
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
	    System.out.println("12.2");
	    System.out.println("==========================================================================");
	    System.out.println("Process Error Message Block of Topic Ended");
	    System.out.println("==========================================================================");
	    
	}  
	
	
	public static void main( String[] args )
    {
        
        
    }
}
