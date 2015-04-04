package jsonPost;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

/* read & post json data to server */ 
public class jsonPost {

    public static String inputJson(String file) {

        String sendString = "";

        try {
            // read file from fileSystem
            InputStream clientInputStream = new FileInputStream(file);
            InputStreamReader clientReader = new InputStreamReader(clientInputStream);
            BufferedReader br = new BufferedReader(clientReader);
            String line;

            while ((line = br.readLine()) != null) {
                sendString += (line + "\n");
            }
            br.close();
        } catch (Exception e) {
            System.out.println("\nError while reading the file");
            System.out.println(e);
        }
        return sendString;
    }

    public static void checkJson(String jsonString) throws Exception {
        if (StringUtils.isBlank(jsonString)) {
            throw new Exception("Not in json format - BLANK");
        }
        try {
            new JSONObject(jsonString);
        } catch (Exception e) {
            throw new Exception("Not in json format - ERROR", e);
        }
    }

    public static URLConnection passJsonToRest(String urlString, String sendString) {

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

    public static String receiveReturnCode(URLConnection connection) {

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

    public static void showReceived(String receiveString) {

        System.out.println(receiveString);
    }

	public static void main(String[] args) {

        String sendFile = "/home/tristan/workspace/Source/IOFiles/searchBook.js";
        String sendUrl = "http://localhost:8080/RestfulService/REST/restService";
        String sendString = "";
        String receiveString = "";
        URLConnection connectionWithRest = null;

        try {
            // input the json file
            sendString = inputJson(sendFile);

            // check if the data is of json type
            checkJson(sendString);

            // pass JSON File Data to REST Service
            connectionWithRest = passJsonToRest(sendUrl, sendString);

            // receive return code from restful service
            receiveString = receiveReturnCode(connectionWithRest);

            // show what's received from restful web service
            showReceived(receiveString);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}