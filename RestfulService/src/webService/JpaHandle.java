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
