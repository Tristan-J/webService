#tomcat容器中使用JNDI访问外部openLDAP提供的目录服务

[TOC]

-------------------


##Preface
-   本smaple 使用OpenLDAP 提供目录服务，并使用Berkeley DB 作为backend
-   在之前jerseyRestfulservice&Client的基础上需要进行细微的修改
-   参考[tutorialspoint](http://www.tutorialspoint.com/jpa/jpa_tutorial.pdf)
-   本文原创，允许转载但务必贴出本文链接


##环境&工具
-   [Jersey JAX-RS 2.0 RI bundle](http://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.17/jaxrs-ri-2.17.zip) 解压后将所有.jar文件放在WebContent>WEB-INF>lib下面
-   [json.jar](http://www.java2s.com/Code/JarDownload/java-json/java-json.jar.zip) **注：因为eclipse有特殊的喜好，在web servicejar 工程中，jar包的引用根据web容器而有所不同，对于tomcat，所有引用.jar必须放在web-info/lib/下**
-   [OpenLDAP version "openldap-2.4.40"](ftp://ftp.openldap.org/pub/OpenLDAP/openldap-release/openldap-2.4.40.tgz)
-   [Berkeley DB version "db-4.7.25"](http://download.oracle.com/berkeley-db/db-4.7.25.tar.gz)
-   [bdb-dev(4.8)](http://packages.ubuntu.com/lucid/i386/libdb-dev)Ubuntu 下可直接使用"sudo apt-get install libdb-dev"(注：bdb的文档中没看到对该包的依赖)
-   java version "1.8.0_40"
-   tomcat 8.0
-   eclipse Luna
-   mysql 5.6.19


##主要参考文档
-   [OpenLDAP Software 2.4 Administrator's Guide](http://www.openldap.org/doc/admin24/) | [中文版相关概念](https://github.com/Tristan-J/webService/blob/master/Source/OpenLDAP/ldap.pdf)
-   [Configure LDAP service in tomcat](https://stackoverflow.com/questions/13040980/trying-to-configure-ldap-as-jndi-resource-in-tomcat/27127290#27127290)
-   stackoverflow
-   stackexchange
-   Q&A emails that could be found in OpenLDAP's host 


##JNDI调用外部服务基本过程说明
-   [JNDI wikipedia](http://en.wikipedia.org/wiki/Java_Naming_and_Directory_Interface)
-   [OpenLdap wikipedia](http://en.wikipedia.org/wiki/OpenLDAP)
-   基本概念：
    -   JNDI: Java Naming and Directory Interface
        -   可以由多种方式实现JNDI服务
            -   在server/context.xml 中添加resource，容器会自动添加该外部服务，然后在servlet中lookup()
            -   在serverlet 中手动添加访问配置，通过context factory生成该服务的context，然后在servlet中lookup()
            -   在ejb中注入(injection resource)
    -   ldap及openLDAP：概念颇多，需要参考[OpenLDAP Software 2.4 Administrator's Guide](http://www.openldap.org/doc/admin24/) | [中文版相关概念](https://github.com/Tristan-J/webService/blob/master/Source/OpenLDAP/ldap.pdf)


##对应修改WebService.java (为了方便使用，ldap的访问直接放在了该类中，看起来有些不舒服，实际应用中可以单独分开)

```java
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
```

##OpenLDAP 配置过程及文件
-   安装Berkeley DB
    -   现在对bdb的支持不算很好，可以选择其他方案
    -   添加bdb-dev包
    -   cd build_unix
    -   ../dist/configure
    -   make
    -   make install
    -   环境变量的设置：
        -   env CPPFLAGS="-I/usr/local/BerkeleyDB.4.7/include" LDFLAGS="-L/usr/local/BerkeleyDB.4.7/lib"
-   安装OpenLDAP
    -   文档中有openLDAP的安装步骤，不过看网上讨论以及亲自实践，还是要准备好不断调试的心情。
    -   以下是从各个帖子中搜集到的测试手段（或者也可直接翻api）：
        -   查看bdb中的现有目录：ldapsearch -x -b '' -s base '(objectclass=*)' namingContexts
        -   尝试添加目录（测试manager登录）：ldapadd -x -D 'cn=Manager,dc=localhost,dc=com' -W
        -   测试配置文件正确性：sudo slaptest
        -   以debug模式启动slapd：sudo slapd -d 256（通过JNDI使用slapd的时候也可以从这里看到输出）
    -   其他的一些tips：
        -   ./configure --prefix=/usr/local/openldap --enable-bdb=yes 配置时可以随便选prefix路径，但最好还是加上enable-bdb(虽然默认会enable该backend，但最好还是显式配置)
        -   prefix路径并不是配置的路径，配置文件会在/etc/ldap/下，并且install之后需要rm -rf /etc/ldap/slapd.d/*来删除ldap给的example，不然之后不会被新配置覆盖的
        -   配置文件slapd.conf中的配置项前不可有空格，配置项和值之间最好使用tab
        -   slaptest -f /etc/ldap/slapd.conf -F /etc/ldap/slapd.d 将自己的配置文件slapds.conf生成对应格式的真正配置文件
        -   因为很多都是手动修改生成的文件，所以很多文件的所有人和所有组都不是openldap，相关文件访问权限需要修改
    -   [配置文件及一个从bdb读出的sample](https://github.com/Tristan-J/webService/tree/master/Source/OpenLDAP)
