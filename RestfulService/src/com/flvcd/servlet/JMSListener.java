package com.flvcd.servlet; 
 
import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 
import javax.naming.*; 
import javax.jms.*; 
import org.apache.activemq.ActiveMQConnectionFactory; 

public class JMSListener extends HttpServlet implements MessageListener { 

    // valid this when it is queue consumer receive message synchronously
    Message message = null;
    
    // initialize the JNDI variables and type of consumers
    public void init(ServletConfig config) throws ServletException { 
        try {
            InitialContext initCtx = new InitialContext(); 
            Context envContext = (Context) initCtx.lookup("java:comp/env");

            ConnectionFactory connectionFactory = (ConnectionFactory) envContext.lookup("jms/FailoverConnectionFactory"); 
            Connection connection = connectionFactory.createConnection(); 
            connection.setClientID("MyClient"); 
            Session jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
            
            // - Topic or Queue & Persistent or Non_persistent
            //      - Queue message consumer
            MessageConsumer consumer = jmsSession.createConsumer((Destination)envContext.lookup("jms/queue/MyQueue")); 
            
            //      - Normal topic message subscriber and can't receive persistent messages
            // MessageConsumer consumer = jmsSession.createConsumer((Destination)envContext.lookup("jms/topic/MyTopic")); 
            
            //      - Persistent message consumer which is based on Topic, besides, Connection must have a unique clientId, and now its "MyClient"
            // TopicSubscriber consumer = jmsSession.createDurableSubscriber((Topic)envContext.lookup("jms/topic/MyTopic"), "MySub"); 

            // asynchronously receive message 
            consumer.setMessageListener(this);
            connection.start();
        } catch (NamingException e) { 
            e.printStackTrace(); 
        } catch (JMSException e) { 
            e.printStackTrace(); 
        }
    }

    // asynchronously receive the messages and handle them
    public void onMessage(Message message) { 
        if (checkText(message, "TestContent1") != null) { 
            String content = checkText(message, "TestContent1"); 
            System.out.println("TestContent1 =" + content);
        } else {
            System.out.println("receive normal messages"); 
        }
    }

    // return the string content of message
    private static String checkText(Message m, String s) { 
        try { 
            return m.getStringProperty(s); 
        } catch (JMSException e) { 
            e.printStackTrace(System.out); 
            return null; 
        } 
    } 
}