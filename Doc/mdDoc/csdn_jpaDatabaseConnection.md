#使用JPA进行数据操作

[TOC]

-------------------


##Preface
-   本smaple 使用java persistnece api 进行数据操作
-   在之前jerseyRestfulservice&Client的基础上需要进行细微的修改
-   参考[tutorialspoint](http://www.tutorialspoint.com/jpa/jpa_tutorial.pdf)
-   本文原创，允许转载但务必贴出本文链接


##环境&工具
-   [Jersey JAX-RS 2.0 RI bundle](http://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.17/jaxrs-ri-2.17.zip) 解压后将所有.jar文件放在WebContent>WEB-INF>lib下面
-   [json.jar](http://www.java2s.com/Code/JarDownload/java-json/java-json.jar.zip) **注：因为eclipse有特殊的喜好，在web servicejar 工程中，jar包的引用根据web容器而有所不同，对于tomcat，所有引用.jar必须放在web-info/lib/下**
-   [mysql-connector-java-5.1.35.tar.gz](http://dev.mysql.com/downloads/connector/j/)**注：需要向oracle出卖自己的email他才会让你下这个东西，之和后json.jar一样需要放在web-info/lib/下**
-   JPA [eclipselink](http://www.eclipse.org/downloads/download.php?file=/rt/eclipselink/releases/2.6.0/eclipselink-2.6.0.v20150309-bf26070.zip) 同上，需要将其中所有的jar包放入web-info/lib/下
-   java version "1.8.0_40"
-   tomcat 8.0
-   eclipse Luna
-   mysql 5.6.19


##JPA基本过程说明
-   Form Oracle: The Java Persistence API provides a POJO persistence model for object-relational mapping. The Java Persistence API was developed by the EJB 3.0 software expert group as part of JSR 220, but its use is not limited to EJB software components. It can also be used directly by web applications and application clients, and even outside the Java EE platform, for example, in Java SE applications.
-   我的理解是：
    -   run on server 之后加载jdbc和jpa函数库
    -   入口是persistence.xml，进入后会根据配置通过jdbc数据源池链接到mysql
    -   查找之前编入相关annotation的实体（entity），将实体类map到数据库
    -   在内存中copy物理介质上的数据库
    -   当有对数据库的操作发生时，会首先通过类似sql的jpql在内存中的数据库中进行操作，检验命令是否正确，成功后对应操作物理介质上的数据库
    -   当物理介质上的数据库完成了修改，会返回修改内存中的数据库
-   从tutorialspoint中卡的图：
![驱动中的类](http://img.blog.csdn.net/20150408190408785)
![对应简介](http://img.blog.csdn.net/20150408190414732)


##将原dynamic web service 添加JPA facet
-   如果原来的项目中没有JPA facet需要从project->property中添加


##创建entity class

```java
package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String description;

    public Book(int inputId, String inputTitle, String inputDescription) {
        super();
        this.id = inputId;
        this.title = inputTitle;
        this.description = inputDescription;
    }

    public Book() {
        super();
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int setId(int inputId) {
        this.id = inputId;
        return this.id;
    }

    public String setTitle(String inputTitle) {
        this.title = inputTitle;
        return this.title;
    }

    public String setDescription(String inputDescription) {
        this.description = inputDescription;
        return this.description;
    }

    @Override
    public String toString() {
        return "Book: [id:" + this.id + ", title:" + this.title + "]";
    }
}

```


##修改persistence.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
    <persistence-unit name="RestfulService">
        <class>entity.Book</class><!-- package.entityClass -->
        <properties>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://127.0.0.1:3306/LIBRARY"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="123456"/>
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
            <property name="eclipselink.logging.level" value="FINE"/>
            <property name="eclipselink.ddl-generation" value="create-tables"/>
        </properties>
    </persistence-unit>
</persistence>
```

> 注：因为默认情况下（不加@Column annotation），之后要写的JPQL都会自动转成大写，而所用版本的mysql对大小写敏感，所以这边索性将database name 改为大写，对应mysql那边也做了修改


##创建JpaHandle类对数据进行操作

```java
package webService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JpaHandle {

    // for jpa test, if jpa service run well the titles of Book will show in console window
    public static void showTitle() {
        EntityManagerFactory bookFactory = Persistence.createEntityManagerFactory( "RestfulService" );
        EntityManager entityManager = bookFactory.createEntityManager();

        //Scalar function
        Query query = entityManager.createQuery("Select e.title from Book e");
        System.out.println("this is a test 2");
        List<String> list = query.getResultList();

        System.out.println("test list before");
        System.out.println(list.toString());
        System.out.println("test list after");
        int tempI = 0;
        for(String e:list) {
            System.out.println("ok" + tempI);
            tempI++;
            System.out.println("Book title :"+e);
        }
    }

    // get the titles of Books 
    public static List<String> getTitle() {
        EntityManagerFactory bookFactory = Persistence.createEntityManagerFactory( "RestfulService" );
        EntityManager entityManager = bookFactory.createEntityManager();

        // Query query = entityManager.createQuery("select e from Book e where uppercase(e.title) = :title");
        Query query = entityManager.createQuery("Select e.title from Book e");
        List<String> list = query.getResultList();

        return list;
    }

}

```

>如果查看console会发现"Select e.title from Book e"作用在mysql时，会被自动转成大写，所以如果不使用@Column annotation或者其他设置，mysql中的table、column都需要换成大写


##对应修改WebService.java

```java
package webService;

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

import org.json.JSONException;  
import org.json.JSONObject;

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

        return returnCode;
    }
}
```