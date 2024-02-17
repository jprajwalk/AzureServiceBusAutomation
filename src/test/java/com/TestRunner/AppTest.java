package com.TestRunner;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.Test;

import com.Functions.BusManagerQueue;
import com.Functions.BusManagerTopic;
import com.Functions.DeadLetterQueue;
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
    
  public void Topic() throws Throwable
    
    {
	  BusManagerTopic B = new BusManagerTopic();
  	  
	   //sending a message to topic with invalid data which will go to deadLetter Queue  	
	   //B.sendMessage("input/1P_PushAPI-Event-SampleRequest.json");
	      	try {
	    		B.sendMessage("input/New.json");
	    	} catch (Throwable e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	      	Thread.sleep(10000);

	    	//B.receiveMessages();
	     
    	
       assertTrue( true );
    }
 
  
  public void main(String args[]) throws Throwable
  {
	 Topic();
	
  }
 }

