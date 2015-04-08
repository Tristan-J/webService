package webService;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class JSQLDataHandle {

	public static void showTitle() {
		EntityManagerFactory bookFactory = Persistence.createEntityManagerFactory( "RestfulService" );
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
	}

}
