package com.azure;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.rmg.TestManager.BusManager;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
  @Test
    public void azurTest()
    
    {
    	System.out.println("Starting");
    	BusManager A = new BusManager();
    	//A.sendMessage("input/1P_PushAPI-Event-SampleRequest.json");
    	try {
			A.receiveMessages();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
       assertTrue( true );
    }
}
