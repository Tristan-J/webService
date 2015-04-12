#JMSSample

[TOC]

-------------------


##Preface
-   因为功能平行，所以需要在之前jerseyRestfulservice的基础上需要添加独立package
-   最终版本中包含持久/非持久消息、topic/queue 消息、同步非同步接收等所有方式，本sample为清晰起见，只展示发布-订阅的持久消息发送接收，其他方式大同小异，具体移步[github](https://github.com/Tristan-J/webService)
-   其他需要进一步设置的功能，如持久化到数据库或指定本地文件等，本sample及最终代码中暂时没有涉及，不过有时间会继续更新
-   本文原创，允许转载但务必贴出本文链接


##环境&工具
-   [ActiveMQ 5.11.1](http://activemq.apache.org/)
-   java version "1.8.0_40"
-   tomcat 8.0
-   eclipse Luna


##JMS 基本概念
-   From Oracle: Messaging is a method of communication between software components or applications. A messaging system is a peer-to-peer facility: A messaging client can send messages to, and receive messages from, any other client. Each client connects to a messaging agent that provides facilities for creating, sending, receiving, and reading messages.
-   [wikipedia](http://en.wikipedia.org/wiki/Java_Message_Service)
-   从[javapoint](http://www.javatpoint.com/jms-tutorial)以及其他相关材料中的总结：
    -   JMS 相关类及其关系
![jms programming model](http://img.blog.csdn.net/20150412120834293)
    -   p2p 方式
![jms queue](http://img.blog.csdn.net/20150412120856554)
    -   publisher-subscriber 方式 
![jms topic](http://img.blog.csdn.net/20150412120913168)
    -   以上两种方式的api
![jms api](http://img.blog.csdn.net/20150412120923308)



##workspace/Servers/tomcat/context.xml 在tomcat中配置JNDI，可参考[activeMQ](http://activemq.apache.org/setting-up-activemq-with-tomcat-559.html)及[tomcatExpert](http://www.tomcatexpert.com/blog/2010/12/16/integrating-activemq-tomcat-using-local-jndi)
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
  Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--><!-- The contents of this file will be loaded for each web application -->
<Context>

    <!-- Default set of monitored resources. If one of these changes, the    -->
    <!-- web application will be reloaded.                                   -->
    <WatchedResource>WEB-INF/web.xml</WatchedResource>
    <WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>

    <Resource 
      name="jms/FailoverConnectionFactory" 
      auth="Container" 
      type="org.apache.activemq.ActiveMQConnectionFactory" 
      description="JMS Connection Factory" 
      factory="org.apache.activemq.jndi.JNDIReferenceFactory" 
      brokerURL="failover:(tcp://localhost:61616)?initialReconnectDelay=100&amp;maxReconnectAttempts=5" 
      brokerName="localhost" 
      useEmbeddedBroker="false"/> 
    <Resource 
      name="jms/NormalConnectionFactory" 
      auth="Container" 
      type="org.apache.activemq.ActiveMQConnectionFactory" 
      description="JMS Connection Factory" 
      factory="org.apache.activemq.jndi.JNDIReferenceFactory" 
      brokerURL="tcp://localhost:61616" 
      brokerName="localhost" 
      useEmbeddedBroker="false"/> 
    <Resource 
      name="jms/topic/MyTopic"
      auth="Container" 
      type="org.apache.activemq.command.ActiveMQTopic" 
      factory="org.apache.activemq.jndi.JNDIReferenceFactory" 
      physicalName="MY.TEST.FOO"/> 
    <Resource 
      name="jms/queue/MyQueue" 
      auth="Container" 
      type="org.apache.activemq.command.ActiveMQQueue" 
      factory="org.apache.activemq.jndi.JNDIReferenceFactory" 
      physicalName="MY.TEST.FOO.QUEUE"/>

    <!-- Uncomment this to disable session persistence across Tomcat restarts -->
    <!--
    <Manager pathname="" />
    -->

    <!-- Uncomment this to enable Comet connection tacking (provides events
         on session expiration as well as webapp lifecycle) -->
    <!--
    <Valve className="org.apache.catalina.valves.CometConnectionManagerValve" />
    -->
</Context>
```


##创建publisher

```java
package com.flvcd.servlet; 
 
import java.io.IOException; 
import java.io.PrintWriter; 
 
import javax.jms.Connection; 
import javax.jms.ConnectionFactory; 
import javax.jms.DeliveryMode; 
import javax.jms.Destination; 
import javax.jms.JMSException; 
import javax.jms.Message; 
import javax.jms.MessageListener; 
import javax.jms.MessageProducer; 
import javax.jms.Session; 
import javax.naming.Context; 
import javax.naming.InitialContext; 
import javax.naming.NamingException; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
 
public class MyPublish extends HttpServlet implements MessageListener { 
     
     
    private InitialContext initCtx; 
    private Context envContext; 
    private ConnectionFactory connectionFactory; 
    private Connection connection; 
    private Session jmsSession; 
    private MessageProducer producer; 
     
     
    public void onMessage(Message message) { 
        // TODO Auto-generated method stub 
 
    } 
 
    // Constructor of the object.  
    public MyPublish() { 
        super(); 
    } 
 
    // Destruction of the servlet
    public void destroy() { 
        super.destroy(); 
    } 
 
    // pass content to doPost
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
 
        doPost(request, response);
    } 
 
    // publish message
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        String content=request.getParameter("content"); 
        try { 
            producer.setDeliveryMode(DeliveryMode.PERSISTENT); 
            Message testMessage = jmsSession.createMessage(); 
            testMessage.setStringProperty("RefreshArticleId", content); 
            producer.send(testMessage); 
            testMessage.clearProperties(); 
            testMessage.setStringProperty("RefreshThreadId", content); 
            producer.send(testMessage); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
         
         
    } 
 
    // init JNDI and JMS session
    public void init() throws ServletException { 
        // Put your code here 
        try { 
            initCtx = new InitialContext(); 
            envContext = (Context) initCtx.lookup("java:comp/env"); 
            connectionFactory = (ConnectionFactory) envContext.lookup("jms/NormalConnectionFactory"); 
            connection = connectionFactory.createConnection(); 
            jmsSession = connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
            producer = jmsSession.createProducer((Destination) envContext.lookup("jms/topic/MyTopic")); 
 
        } catch (NamingException e) { 
            e.printStackTrace(); 
        } catch (JMSException e) { 
            e.printStackTrace(); 
        } 
    } 
}

```


##创建异步 message consumer

``` java
package com.flvcd.servlet; 
 
import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 
import javax.naming.*; 
import javax.jms.*; 
import org.apache.activemq.ActiveMQConnectionFactory; 
 
public class JMSListener extends HttpServlet implements MessageListener { 
    
    // initialize JNDI and JMS
    public void init(ServletConfig config) throws ServletException { 
        try { 
            InitialContext initCtx = new InitialContext(); 
            Context envContext = (Context) initCtx.lookup("java:comp/env"); 
            ConnectionFactory connectionFactory = (ConnectionFactory) envContext 
                    .lookup("jms/FailoverConnectionFactory"); 
            Connection connection = connectionFactory.createConnection(); 
            connection.setClientID("MyClient"); 
            Session jmsSession = connection.createSession(false, 
                    Session.AUTO_ACKNOWLEDGE); 
            TopicSubscriber consumer=jmsSession.createDurableSubscriber((Topic)envContext.lookup("jms/topic/MyTopic"), "MySub"); 
            consumer.setMessageListener(this); 
            connection.start(); 
        } catch (NamingException e) { 
            e.printStackTrace(); 
        } catch (JMSException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    // receive the message and handle the content 
    public void onMessage(Message message) { 
        if (checkText(message, "RefreshArticleId") != null) { 
            String articleId = checkText(message, "RefreshArticleId"); 
            System.out.println("RefreshArticleId = " + articleId); 
        } else if (checkText(message, "RefreshThreadId") != null) { 
            String threadId = checkText(message, "RefreshThreadId"); 
            System.out.println("RefreshThreadId = " + threadId); 
        } else { 
            System.out.println("normal message"); 
        } 
    } 
 
    private static String checkText(Message m, String s) { 
        try { 
            return m.getStringProperty(s); 
        } catch (JMSException e) { 
            e.printStackTrace(System.out); 
            return null; 
        } 
    } 
}
```


##index.jsp 置于WebContent/ 下

``` jsp
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
  <body>
  <form action="myPublish.do">
    <input type="text" name="content" />
    <input type="submit" name="tijiao" />
  </form>

  </body>
</html>
```
> 该jsp为web项目首页，当项目在tomcat容器中启动，会自动打开该页面，在其中输入随机字符点击submit，字符会以get方式传给provider，之后将内容加进新创建的message中


##index.jsp 置于WebContent/ 下
``` jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="myPublish.do"> 
    <input type="text" name="content" /> 
    <input type="submit" value="提交" > 
</form>

</body>
</html>
```


##修改web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
    <display-name>RestfulService</display-name>

    <servlet>
        <servlet-name>api Jersey REST Service</servlet-name>
        <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class> 
        <init-param>
            <param-name>jersey.config.server.provider.packages</param-name>
            <param-value>webService</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>api Jersey REST Service</servlet-name>
        <url-pattern>/REST/*</url-pattern>
    </servlet-mapping>

    <servlet> 
        <servlet-name>jms-listener</servlet-name> 
        <servlet-class> 
            com.flvcd.servlet.JMSListener 
        </servlet-class> 
        <load-on-startup>1</load-on-startup> 
        </servlet> 
        
        <servlet>
        <description>This is the description of my J2EE component</description>
        <display-name>This is the display name of my J2EE component</display-name>
        <servlet-name>MyPublish</servlet-name>
        <servlet-class>com.flvcd.servlet.MyPublish</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>MyPublish</servlet-name>
        <url-pattern>/myPublish.do</url-pattern>
    </servlet-mapping>
      
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
</web-app>
```


