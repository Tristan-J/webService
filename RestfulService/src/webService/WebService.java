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
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
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
            ldapSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("ldapSearch error");
        }
        return returnCode;
    }
    
    // Search for the full name of a person whoes uid equals to "fbloggs"
    // (or we can search for the password for identity recognization)
    public static String ldapSearch() throws Exception {

        DirContext ldapContext = null;
        ldapContext = createLDAPContext();
        String returnCode = "";

        // Set the filter
        String uid = "fbloggs";
        String filter = "(&(objectClass=inetOrgPerson)(uid=" + uid + "))";

        try {
            // Set the contents that will be shown
            String[] attrPersonArray = { "uid", "userPassword", "cn", "sn", "mail" };
            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchControls.setReturningAttributes(attrPersonArray);

            // Receive result & print out
            // NamingEnumeration answer = ldapContext.search("dc=sample,dc=com", filter.toString(), searchControls);
            NamingEnumeration answer = ldapContext.search("", filter.toString(), searchControls);
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
            }
            returnCode += sb.toString();
            System.out.println("before-");
            System.out.println(returnCode);
            System.out.println("-after");
        }catch(Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("search & output error");
        }
        

        // Close the context when we're done
        closeLDAPContext(ldapContext);

        return returnCode;
    }

    // Create ldapContext
    public static DirContext createLDAPContext() {

        // Set up environment for creating initial context
        Hashtable env = new Hashtable(11);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389/dc=sample,dc=com");

        // Authenticate as S. User and password "mysecret"
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Manager,dc=sample,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "secret");

        DirContext ldapContext = null;

        try {
            // Create initial context
            ldapContext = new InitialDirContext(env);

            System.out.println(ldapContext.toString());
            System.out.println("create ldap context successfully");

        } catch (NamingException e) {
            e.printStackTrace();
        }

        return ldapContext;
    }

    // Close ldapContext
    public static void closeLDAPContext(DirContext ldapContext) {
        try {
            ldapContext.close();
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}