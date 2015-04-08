package service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ScalarandAggregateFunctions {

	public static void main( String[] args ) {
		EntityManagerFactory bookFactory = Persistence.createEntityManagerFactory( "JPASample" );
		EntityManager entityManager = bookFactory.createEntityManager();

		//Scalar function
		// Query query = entityManager.createQuery("Select UPPER(e.title) from Book e");
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

		//Aggregate function
		// Query query1 = entityManager.createQuery("Select MAX(e.salary) from Employee e");
		// Double result=(Double) query1.getSingleResult();
		// System.out.println("Max Employee Salary :"+result);
	}

}


// create database LIBRARY;
// use LIBRARY
// create table `BOOK` (
//     `ID`    int(11) NOT NULL AUTO_INCREMENT,
//     `TITLE` text    NOT NULL,
//     `DESCRIPTION`   text    NULL,
//     PRIMARY KEY(`ID`)
// );
// insert into BOOK (TITLE, DESCRIPTION) values("Stardust", "Stardust is a novel by Neil Gaiman, usually published with illustrations by Charles Vess. Stardust has a different tone and style from most of Gaiman''s prose fiction, being consciously written in the tradition of pre-Tolkien English fantasy, following in the footsteps of authors such as Lord Dunsany and Hope Mirrlees. It is concerned with the adventures of a young man from the village of Wall, which borders the magical land of Faerie.");
// insert into BOOK (TITLE) values("testTitle2"),("testTitle3");