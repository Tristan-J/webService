package jsonPost;

import static org.junit.Assert.*;

import java.net.URLConnection;

import org.junit.Test;

public class jsonPostTest {

	@Test
	public void testInputJson() {
		String testFile = "/home/tristan/workspace/Source/IOFiles/junitTest_inputJson";
		assertEquals("A test\n", jsonPost.inputJson(testFile));
	}

	@Test
	public void testCheckJson() {
		// fail("Not yet implemented");
		String testJson = "{'name':'tristan'}";
		try {
			assertEquals(true, jsonPost.checkJson(testJson));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testPassJsonToRest() {
		assertEquals(true, passJsonToRestUnit());
	}

	@Test
	public void testReceiveReturnCode() {
		assertEquals(true, receiveReturnCodeUnit());
	}

	@Test
	public void testShowReceived() {
	}

	private boolean passJsonToRestUnit() {
		String sendFile = "/home/tristan/workspace/Source/IOFiles/searchBook.js";
        String sendUrl = "http://localhost:8080/RestfulService/REST/restService";
        String sendString = "";
        String receiveString = "";
        URLConnection connectionWithRest = null;

        try {
            // input the json file
            sendString = jsonPost.inputJson(sendFile);

            // check if the data is of json type
            jsonPost.checkJson(sendString);

            // pass JSON File Data to REST Service
            connectionWithRest = jsonPost.passJsonToRest(sendUrl, sendString);

            return true;
        } catch(Exception e) {
            System.out.println(e);
        }
        return false;
	}


	private boolean receiveReturnCodeUnit() {
		String sendFile = "/home/tristan/workspace/Source/IOFiles/searchBook.js";
        String sendUrl = "http://localhost:8080/RestfulService/REST/restService";
        String sendString = "";
        String receiveString = "";
        URLConnection connectionWithRest = null;

        try {
            // input the json file
            sendString = jsonPost.inputJson(sendFile);

            // check if the data is of json type
            jsonPost.checkJson(sendString);

            // pass JSON File Data to REST Service
            connectionWithRest = jsonPost.passJsonToRest(sendUrl, sendString);

            // receive return code from restful service
            receiveString = jsonPost.receiveReturnCode(connectionWithRest);

            return true;
        } catch(Exception e) {
            System.out.println(e);
        }
        return false;
	}
}
