// package com.flvcd.servlet; 
 
// import java.io.IOException; 
// import java.io.PrintWriter; 

// import javax.jms.Connection; 
// import javax.jms.ConnectionFactory; 
// import javax.jms.DeliveryMode; 
// import javax.jms.Destination; 
// import javax.jms.JMSException; 
// import javax.jms.Message; 
// import javax.jms.MessageConsumer;
// import javax.jms.MessageListener; 
// import javax.jms.MessageProducer; 
// import javax.jms.Session; 
// import javax.naming.Context; 
// import javax.naming.InitialContext; 
// import javax.naming.NamingException; 
// import javax.servlet.ServletException; 
// import javax.servlet.http.HttpServlet; 
// import javax.servlet.http.HttpServletRequest; 
// import javax.servlet.http.HttpServletResponse; 

// public class MyPublish extends HttpServlet implements MessageListener {

//     private InitialContext initCtx; 
//     private Context envContext; 

//     private ConnectionFactory connectionFactory; 
//     private Connection connection; 
//     private Session jmsSession; 
//     private MessageProducer producer; 

//     public void onMessage(Message message) { 
//         // TODO Auto-generated method stub 
//     } 
 
//     public MyPublish() { 
//         super();
//     }

//     public void destroy() { 
//         super.destroy(); // Just puts "destroy" string in log 
//     } 

//     public void doGet(HttpServletRequest request, HttpServletResponse response) 
//             throws ServletException, IOException { 
//         System.out.println("receive it 1");
//         doPost(request, response); 
//     }

//     public void doPost(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException, IOException {
//         String content=request.getParameter("content");
//         System.out.println("receive it 2");

//         try {
//             // producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//             producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//             Message testMessage = jmsSession.createMessage();

//             testMessage.setStringProperty("RefreshArticleId", content); 
//             producer.send(testMessage);

//             testMessage.clearProperties();
//             testMessage.setStringProperty("RefreshThreadId", content); 
//             connection.start();
//             producer.send(testMessage);

//             MessageConsumer consumer = jmsSession.createConsumer((Destination)envContext.lookup("jms/queue/MyQueue")); 
//             System.out.println("receive 0");
//             Message message = consumer.receive(3000);
//             System.out.println("receive 1");
//             if (checkText(message, "RefreshArticleId") != null) { 
//                 System.out.println("receive 2.1");
//                 String articleId = checkText(message, "RefreshArticleId"); 
//                 System.out.println("ID=" + articleId); 
//             } else if (checkText(message, "RefreshThreadId") != null) { 
//                 System.out.println("receive 2.2");
//                 String threadId = checkText(message, "RefreshThreadId"); 
//                 System.out.println("ID=" + threadId); 
//             } else {
//                 System.out.println("receive 2.3");
//                 System.out.println("receive normal messages");
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }

//     public void init() throws ServletException { 
//         try {
//             initCtx = new InitialContext(); 
//             envContext = (Context) initCtx.lookup("java:comp/env");

//             connectionFactory = (ConnectionFactory) envContext.lookup("jms/NormalConnectionFactory"); 
//             connection = connectionFactory.createConnection(); 
//             jmsSession = connection.createSession(false,Session.AUTO_ACKNOWLEDGE); 
//             // producer = jmsSession.createProducer((Destination) envContext.lookup("jms/topic/MyTopic")); 
//             producer = jmsSession.createProducer((Destination) envContext.lookup("jms/queue/MyQueue")); 
//         } catch (NamingException e) { 
//             e.printStackTrace(); 
//         } catch (JMSException e) { 
//             e.printStackTrace(); 
//         }
//     }

    
//     // return the string content of message
//     private static String checkText(Message m, String s) { 
//         try { 
//             return m.getStringProperty(s); 
//         } catch (JMSException e) { 
//             e.printStackTrace(System.out); 
//             return null; 
//         } 
//     } 
// }



package com.flvcd.servlet; 
 
import java.io.IOException; 
import java.io.PrintWriter; 

import javax.jms.Connection; 
import javax.jms.ConnectionFactory; 
import javax.jms.DeliveryMode; 
import javax.jms.Destination; 
import javax.jms.JMSException; 
import javax.jms.Message; 
import javax.jms.MessageConsumer;
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

// message provider
// when try in synchronous way, invalid the JNDI configurations in JMSListener.java and vice versa
public class MyPublish extends HttpServlet implements MessageListener {

    private InitialContext initCtx; 
    private Context envContext; 

    private ConnectionFactory connectionFactory; 
    private Connection connection; 
    private Session jmsSession; 
    private MessageProducer producer; 

    // onMessage for implementing MessageListener
    public void onMessage(Message message) { 
        // TODO Auto-generated method stub 
    } 

    // inherit from super class
    public MyPublish() { 
        super();
    }

    // destroy class
    public void destroy() { 
        super.destroy(); 
    } 

    // handle GET request from broswer
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String content = request.getParameter("content");
        sendMessage(content);
        System.out.println(content);
    }

    // handle data passed from doGet()
    public boolean sendMessage(String content)
            throws ServletException, IOException {

        try {
            // - choose the type of persistent message 
            // producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            Message testMessage = jmsSession.createMessage();
            testMessage.setStringProperty("TestContent1", content); 
            producer.send(testMessage);

            // - can add other properties to message
            // testMessage.clearProperties();
            // testMessage.setStringProperty("TestContent2", content); 
            // producer.send(testMessage);
            connection.start();

            // - for synchronous way, valid following block
            // MessageConsumer consumer = jmsSession.createConsumer((Destination)envContext.lookup("jms/queue/MyQueue")); 
            // Message message = consumer.receive(3000);
            // if (checkText(message, "TestContent1") != null) { 
            //     String content1 = checkText(message, "TestContent1"); 
            //     System.out.println("TestContent1 =" + content1); 
            // } else {
            //     System.out.println("receive normal messages");
            // }


            // - for asynchronous way
            // do nothing

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // init JNDI and JMS session
    public void init() throws ServletException { 
        try {
            initCtx = new InitialContext(); 
            envContext = (Context) initCtx.lookup("java:comp/env");

            connectionFactory = (ConnectionFactory) envContext.lookup("jms/NormalConnectionFactory"); 
            connection = connectionFactory.createConnection(); 
            jmsSession = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            // - choose for Tpoic or Queue destination
            // producer = jmsSession.createProducer((Destination) envContext.lookup("jms/topic/MyTopic")); 
            producer = jmsSession.createProducer((Destination) envContext.lookup("jms/queue/MyQueue")); 
        } catch (NamingException e) { 
            e.printStackTrace();
        } catch (JMSException e) { 
            e.printStackTrace();
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