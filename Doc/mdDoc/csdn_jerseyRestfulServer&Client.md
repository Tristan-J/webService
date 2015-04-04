#一个包含Jersey库的简单Web Service以及一个发送Json数据的Java客户端

[TOC]

-------------------


##Preface
-   想写这篇tutorial的理由很简单，之前学web service的时候发现很多资料上的实例基本上都是凑起来的，也就是说看上去功能结构很完整，复制粘贴的也很爽，但是一些细节上的配置之类的可能会有错误，当时因此耽误了很长时间
-   本sample基本是[crunchify](http://crunchify.com/create-very-simple-jersey-rest-service-and-send-json-data-from-java-client/)的翻译版，不过里面对于web.xml的配置有些不同，crunchify的那个貌似是错的（至少我当时没能用他的跑通）
-   本文原创，允许转载但务必贴出本文链接


##环境&工具
-   [Jersey JAX-RS 2.0 RI bundle](http://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.17/jaxrs-ri-2.17.zip) 解压后将所有.jar文件放在WebContent>WEB-INF>lib下面
-   [json.jar](http://www.java2s.com/Code/JarDownload/java-json/java-json.jar.zip)
-   java version "1.8.0_40"
-   tomcat 8.0
-   eclipse Luna


##建立Dynamic Web Project
-   name = "simpleRestWebService"
-   创建web.xml，内容如下：

```XML
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
    <display-name>simpleRestWebService</display-name>
    <servlet>
        <servlet-name>Jersey REST Service</servlet-name>
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class> 
        <init-param>
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>simpleRestWebService</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>Jersey REST Service</servlet-name>
        <url-pattern>/api/*</url-pattern>
    </servlet-mapping>
</web-app>
```

> 本sample web.xml的作用主要是将*WEBROOT/api/*下的所有请求，映射到simpleRestWebService.RestService类中


##创建你的RestService类
内容如下：

```java
package simpleRestWebService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/restService")
public class RestService {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response json_restResponse(InputStream incomingData) {
        StringBuilder jsonRest = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                jsonRest.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        System.out.println("Data Received: " + jsonRest.toString());
 
        // return HTTP response 200 in case of success
        return Response.status(200).entity(jsonRest.toString()).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello simpleRestWebService";
    }

    // This method is called if XML is request
    @GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello simpleRestWebService" + "</hello>";
    }

    // This method is called if HTML is request
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello simpleRestWebService" + "</title>"
        + "<body><h1>" + "Hello simpleRestWebService" + "</body></h1>" + "</html> ";
    }
}
```

> 这里面包含了两种HTTP请求，GET和POST，其中我用GET做直观的测试，做完之后可以戳[http://localhost:8080/simpleRestWebService/api/restService](http://localhost:8080/simpleRestWebService/api/restService)查看结果。另外一个是POST，我们之后用这个来接受含有json文件的请求。


##创建java project做为json client
-   首先写一个小的json文件作为输入，如：

```javascript
{
    "tristan":{
        "name": "tristan",
        "age": 21,
        "university": "UESTC",
        "hobby": ["jog", "ride"]
    }
}
```

> 可以去站长工具那里检测一下是不是符合json格式

-   client class name = "RestClientJava"
-   client内容如下：

```java
package restSerivceClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
 
import org.json.JSONObject;

public class restClient {
    public static void main(String[] args) {
        String string = "";
        try {
 
            // Step1: Let's 1st read file from fileSystem
            InputStream clientInputStream = new FileInputStream(
                    "/home/tristan/workspace/Source/IOFiles/sampleJson.js");
            InputStreamReader clientReader = new InputStreamReader(clientInputStream);
            BufferedReader br = new BufferedReader(clientReader);
            String line;
            while ((line = br.readLine()) != null) {
                string += line + "\n";
            }
 
            JSONObject jsonObject = new JSONObject(string);
            System.out.println(jsonObject);
 
            // Step2: Now pass JSON File Data to REST Service
            try {
                URL url = new URL("http://localhost:8080/simpleRestWebService/api/restService");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();
                System.out.println("ok~before");

                InputStreamReader inReader = new InputStreamReader(connection.getInputStream());
                System.out.println("ok~reader");

                BufferedReader in = new BufferedReader(inReader);
                System.out.println("ok~buffer");
                while (in.readLine() != null) {
                }
                System.out.println("\nREST Service Invoked Successfully..");
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

```

> 如果不能连接到server的话，console中只会有“ok~before”以及一个Exception提示


##Other Resource
-   可以做web.xml的配置字典 [web.xml文件的作用及基本配置](http://blog.csdn.net/shanliangliuxing/article/details/7458492)

