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
     
     
    //定义初始化所需要的变量 
    private InitialContext initCtx; 
    private Context envContext; 
    private ConnectionFactory connectionFactory; 
    private Connection connection; 
    private Session jmsSession; 
    private MessageProducer producer; 
     
     
    public void onMessage(Message message) { 
        // TODO Auto-generated method stub 
 
    } 
 
    /** 
     * Constructor of the object. 
     */ 
    public MyPublish() { 
        super(); 
    } 
 
    /** 
     * Destruction of the servlet. <br> 
     */ 
    public void destroy() { 
        super.destroy(); // Just puts "destroy" string in log 
        // Put your code here 
    } 
 
    /** 
     * The doGet method of the servlet. <br> 
     * 
     * This method is called when a form has its tag value method equals to get. 
     *  
     * @param request the request send by the client to the server 
     * @param response the response send by the server to the client 
     * @throws ServletException if an error occurred 
     * @throws IOException if an error occurred 
     */ 
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
 
    doPost(request, response); 
    } 
 
    /** 
     * The doPost method of the servlet. <br> 
     * 
     * This method is called when a form has its tag value method equals to post. 
     *  
     * @param request the request send by the client to the server 
     * @param response the response send by the server to the client 
     * @throws ServletException if an error occurred 
     * @throws IOException if an error occurred 
     */ 
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException { 
        String content=request.getParameter("content"); 
        //设置持久方式 
        try { 
            producer.setDeliveryMode(DeliveryMode.PERSISTENT); 
            Message testMessage = jmsSession.createMessage(); 
            // 发布刷新文章消息 
            testMessage.setStringProperty("RefreshArticleId", content); 
            producer.send(testMessage); 
            // 发布刷新帖子消息 
            testMessage.clearProperties(); 
            testMessage.setStringProperty("RefreshThreadId", content); 
            producer.send(testMessage); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
         
         
    } 
 
    /** 
     * Initialization of the servlet. <br> 
     * 
     * @throws ServletException if an error occurs 
     */ 
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