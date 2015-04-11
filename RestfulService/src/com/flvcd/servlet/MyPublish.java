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
 
    public MyPublish() { 
        super();
    }

    public void destroy() { 
        super.destroy(); // Just puts "destroy" string in log 
    } 

    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        System.out.println("get it");
        doPost(request, response); 
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String content=request.getParameter("content");
        System.out.println("post it~");

        try {
            // producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            Message testMessage = jmsSession.createMessage();

            System.out.println("on 1.1");
            testMessage.setStringProperty("RefreshArticleId", content); 
            System.out.println("on 1.2");
            producer.send(testMessage);

            System.out.println("on 2.1");
            testMessage.clearProperties();
            System.out.println("on 2.2");
            testMessage.setStringProperty("RefreshThreadId", content); 
            System.out.println("on 2.3");
            producer.send(testMessage); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws ServletException { 
        try {
            initCtx = new InitialContext(); 
            envContext = (Context) initCtx.lookup("java:comp/env");

            connectionFactory = (ConnectionFactory) envContext.lookup("jms/NormalConnectionFactory"); 
            connection = connectionFactory.createConnection(); 
            jmsSession = connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
            // producer = jmsSession.createProducer((Destination) envContext.lookup("jms/topic/MyTopic")); 
            producer = jmsSession.createProducer((Destination) envContext.lookup("jms/queue/MyQueue")); 
        } catch (NamingException e) { 
            e.printStackTrace(); 
        } catch (JMSException e) { 
            e.printStackTrace(); 
        }
    }
}