package webService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;  
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/* receive GET & POST requests from http://localhost:8080/RestfulService/API/restService */
@Path("/restService")
public class WebService {

    /* receive json data & search in mysql with it */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response json_restResponse(InputStream incomingData) {
        // json receiving variables
        String receivedString = "";
        JSONObject receivedJson = null;


        // temp variables
        // returnCode will be send to client and be present in the console view
        String returnCode = "SEARCH PROGRESS & RESULTS:";

        // receive the json data as receivedJson(JSONObject)
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                receivedString += line;
            }
        	try {
                // convert data stream to json
        		receivedJson = new JSONObject(receivedString);
                returnCode += "\n\n- receive json data successfully...";

                // call jpa method
                List<String> title = JpaHandle.getTitle();
                System.out.println(receivedJson.toString());
                for(String e:title) {
                    returnCode += ("\n\ntitle:\t" + e);
                    if (e == receivedJson.getString("title")) {
                        returnCode += "\t-that's it!";
                    } else {
                        returnCode += ("\t-not the one I'm finding..." + e);
                    }
                }
        	} catch (JSONException e){
        		System.out.println(e);
        	}
        } catch (Exception e) {
            System.out.println(e);
        }

        return Response.status(200).entity(returnCode).build();
    }

    /* receive the GET requests & test whether the server is on */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
        String returnCode = "this is a test~";

        try {
			testSearch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            System.out.println("testSearch error");
		}
		return returnCode;
	}
	
    // Search for fbloggs in openldap server
	public void testSearch() throws Exception {

        // Initialize context
        Context initialContext = null;
        LdapContext ldapContext = null;
        try {
            initialContext = new InitialContext();
            ldapContext = (LdapContext) initialContext.lookup("java:comp/env/ldap/LdapResource");
        } catch (NamingException e) {
            e.printStackTrace();
            System.out.println("initialContext error");
            return;
        }

	    // Set the filter
	    String uid = "fbloggs";
	    String filter = "(&(objectClass=inetOrgPerson)(uid=" + uid + "))";

	    // Set the contents that will be shown
	    String[] attrPersonArray = { "uid", "userPassword", "cn", "sn", "mail" };
	    SearchControls searchControls = new SearchControls();
	    searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    searchControls.setReturningAttributes(attrPersonArray);

	    // Receive result & print out
        // NamingEnumeration<SearchResult> answer = ldapContext.search("dc=sample,dc=cn", filter.toString(), searchControls);
	    NamingEnumeration answer = ldapContext.search("dc=sample,dc=cn", filter.toString(), searchControls);
        StringBuffer sb = new StringBuffer();
	    while (answer.hasMore()) {
	        SearchResult result = (SearchResult) answer.next();
	        Attributes attrs = result.getAttributes();
            if (answer.hasMore()){
                sb.append(attrs.get("cn").toString().substring(4) + ",");
            }
            else{
                sb.append(attrs.get("cn").toString().substring(4));
            }
	        System.out.println("\n");
	    }
	}
}