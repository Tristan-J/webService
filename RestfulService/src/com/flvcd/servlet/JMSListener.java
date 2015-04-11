package com.flvcd.servlet; 
 
import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 
import javax.naming.*; 
import javax.jms.*; 
import org.apache.activemq.ActiveMQConnectionFactory; 

public class JMSListener extends HttpServlet implements MessageListener { 
    
    // initialize the JNDI variables and type of consumers
    public void init(ServletConfig config) throws ServletException { 
        try {
            InitialContext initCtx = new InitialContext(); 
            Context envContext = (Context) initCtx.lookup("java:comp/env");

            ConnectionFactory connectionFactory = (ConnectionFactory) envContext.lookup("jms/FailoverConnectionFactory"); 
            Connection connection = connectionFactory.createConnection(); 
            connection.setClientID("MyClient"); 
            Session jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
            
            // - Queue message consumer
            MessageConsumer consumer = jmsSession.createConsumer((Destination)envContext.lookup("jms/queue/MyQueue")); 
            
            // - Normal topic message subscriber and can't receive persistent messages
            // MessageConsumer consumer = jmsSession.createConsumer((Destination)envContext.lookup("jms/topic/MyTopic")); 
            
            // - Persistent message consumer which is based on Topic, besides, Connection must have a unique clientId, and now its "MyClient"
            // TopicSubscriber consumer = jmsSession.createDurableSubscriber((Topic)envContext.lookup("jms/topic/MyTopic"), "MySub"); 
            
            consumer.setMessageListener(this); 
            connection.start();
        } catch (NamingException e) { 
            e.printStackTrace(); 
        } catch (JMSException e) { 
            e.printStackTrace(); 
        } 
    } 

    // asynchronously receive the messages and handle it
    public void onMessage(Message message) { 
        if (checkText(message, "RefreshArticleId") != null) { 
            String articleId = checkText(message, "RefreshArticleId"); 
            System.out.println("接收刷新文章消息，开始刷新文章ID=" + articleId); 
        } else if (checkText(message, "RefreshThreadId") != null) { 
            String threadId = checkText(message, "RefreshThreadId"); 
            System.out.println("接收刷新论坛帖子消息，开始刷新帖子ID=" + threadId); 
        } else {
            System.out.println("接收普通消息，不做任何处理！"); 
        }
    }

    // synchronously receive the messages and handle them
    public void receive(Message message) {
        if (checkText(message, "RefreshArticleId") != null) { 
            String articleId = checkText(message, "RefreshArticleId"); 
            System.out.println("接收刷新文章消息，开始刷新文章ID=" + articleId); 
        } else if (checkText(message, "RefreshThreadId") != null) { 
            String threadId = checkText(message, "RefreshThreadId"); 
            System.out.println("接收刷新论坛帖子消息，开始刷新帖子ID=" + threadId); 
        } else {
            System.out.println("接收普通消息，不做任何处理！"); 
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