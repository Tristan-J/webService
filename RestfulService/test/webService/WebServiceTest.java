package webService;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Before;
import org.junit.Test;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class WebServiceTest {

// tips about web page project testing with JWebUnit plugin
    // @Before
    // public void prepare() {
    //     setBaseUrl("http://localhost:8080/RestfulService/");
    // }

	// @Test
	// public void testLogin() {
	//     beginAt("/home");
	//     clickLink("login");
	//     assertTitleEquals("Login");
	//     setTextField("username", "test");
	//     setTextField("password", "test123");
	//     submit();
	//     assertTitleEquals("Welcome, test!");
	// }

	@Test
	public void testJson_restResponse() {
		assertEquals(true, json_restResponseUnit());
	}


	private static boolean json_restResponseUnit() {
		try {
			String sendString = "{'name':'Stardust'}";
			String sendUrl = "http://localhost:8080/RestfulService/REST/restService";
	        String receiveString = "";
	        URLConnection connectionWithRest = null;

            // pass JSON File Data to REST Service
            connectionWithRest = passJsonToRest(sendUrl, sendString);

            // receive return code from restful service
            receiveString = receiveReturnCode(connectionWithRest);

            // show what's received from restful web service
            showReceived(receiveString);

            return true;
		} catch (Exception e){
			System.out.println(e);
		}
		return false;
	}


    private static URLConnection passJsonToRest(String urlString, String sendString) {
        URLConnection connection = null;
        try {
            // send json data as stream to rest service
            URL url = new URL(urlString);
            connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(sendString);
            out.close();
        } catch (Exception e) {
            System.out.println("\nError while calling REST Service");
            System.out.println(e);
        }
        return connection;
    }

    private static String receiveReturnCode(URLConnection connection) {
        String receiveString = "";
        try {
            // receive ReturnCode from rest service
            InputStreamReader inReader = new InputStreamReader(connection.getInputStream());
            BufferedReader in = new BufferedReader(inReader);
            String receiveLine;
            while ((receiveLine = in.readLine()) != null) {
                System.out.println(receiveLine);
                receiveString += (receiveLine + "\n");
            }
            in.close();
            System.out.println("\nREST Service Invoked Successfully...");
        } catch (Exception e) {
            System.out.println("\nError while receiving REST Service ReturnCode");
            System.out.println(e);
        }
        return receiveString;
    }

    private static void showReceived(String receiveString) {
        System.out.println(receiveString);
    }

}
