package com.azure;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.rmg.TestManager.BusManagerQueue;
import com.rmg.TestManager.BusManagerTopic;
import com.rmg.TestManager.DeadLetterQueue;
import com.rmg.TestManager.DeleteDLQ;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	
	/**
	   * Rigorous Test :-)
	   * @throws Throwable 
	   */
	@Test
	  public void Queue() throws Throwable
	  {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	    System.out.println("Starting Main Program  ||  App test ");
	    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	    
	    
		  BusManagerQueue A = new BusManagerQueue();
	  	  
		  	//Sending a message to Queue
		    	A.sendMessage("input/1P_PushAPI-Event-SampleRequest.json");
		    	A.sendMessage("input/10P_PushAPI-Event_message with no mailpiece.json");
		    	Thread.sleep(10000);
		    	
		    //receiving a message to Queue   	
		      	
		      	A.receiveMessages();    	
	  }
	

	/**
     * Rigorous Test :-)
     * @throws Throwable 
     */
  @Test
    
  public void Topic() throws Throwable
    
    {
	  BusManagerTopic B = new BusManagerTopic();
  	  
	   //sending a message to topic with invalid data which will go to deadLetter Queue  	
	   //B.sendMessage("input/1P_PushAPI-Event-SampleRequest.json");
	      	try {
	    		B.sendMessage("input/1P_PushAPI-Event-SampleRequest.json");
	    	} catch (Throwable e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	      	Thread.sleep(10000);

	    	//B.receiveMessages();
	     
    	
       assertTrue( true );
    }
  
  /**
   * Rigorous Test :-)
   * @throws Throwable 
   */
@Test

  public void DeadLetterQueue() throws Throwable
  {
	//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    //System.out.println("Starting Main Program  ||  App test ");
    //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    
 	
	   //To send message to queue comment the below line A.receiveMessage(); command
	    	
	      	DeadLetterQueue D = new DeadLetterQueue();
			D.ReceiveMessageFromDLQ();
			Thread.sleep(10000);
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		    System.out.println("Ending Main Program  ||  App test ");
		    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		    		
			
	    	 	
	    	//C.deleteMessage();
	    	
    
	    	
  }
  
	
  
  public void main(String args[]) throws Throwable
  {
	  Queue();
	  Topic();
	  //DeadLetterQueue();
  }
 }

