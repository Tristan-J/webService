package jsonPost;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
 
import org.json.JSONObject;

/* read & post json data to server */ 
public class jsonPost {
	public static void main(String[] args) {
        String string = "";
        try {
 
            // read file from fileSystem
            InputStream clientInputStream = new FileInputStream("/home/tristan/workspace/Source/IOFiles/searchBook.js");
            InputStreamReader clientReader = new InputStreamReader(clientInputStream);
            BufferedReader br = new BufferedReader(clientReader);
            String line;
            while ((line = br.readLine()) != null) {
                string += line + "\n";
            }
 
            JSONObject jsonObject = new JSONObject(string);
            System.out.println(jsonObject);
 
            // pass JSON File Data to REST Service
            try {
                URL url = new URL("http://localhost:8080/RestfulService/REST/restService");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();

                InputStreamReader inReader = new InputStreamReader(connection.getInputStream());

                BufferedReader in = new BufferedReader(inReader);
                String receivedLine;
                while ((receivedLine = in.readLine()) != null) {
                    System.out.println(receivedLine);
                }
                System.out.println("\nREST Service Invoked Successfully...");
                in.close();
            } catch (Exception e) {
                System.out.println("\nError while calling REST Service");
                System.out.println(e);
            }
 
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
