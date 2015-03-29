package webService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import webService.toJson;

@Path("/restService")
public class webService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response json_restResponse(InputStream incomingData) {
        StringBuilder jsonRest = new StringBuilder();
        JSONObject receivedJson = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String receivedString = "";
            String line = null;
            while ((line = in.readLine()) != null) {
                jsonRest.append(line);
                receivedString += line;
            }
            receivedJson = toJson.convert(receivedString);
        	// try {
        	// 	receivedJson = new JSONObject(receivedString);
        	// }
        	// catch (Exception e){
        	// 	System.out.println("Error new Json - ");
        	// }
        }
        catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        System.out.println("Data Received: " + jsonRest.toString());

        String ifExist = toJson.getAllUsersList("tristan","123");

        // return HTTP response 200 in case of success
        // return Response.status(200).entity(jsonRest.toString()).build();
        return Response.status(200).entity(ifExist).build();
    }

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello simpleRestWebService";
	}
}
