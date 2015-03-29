package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
 
	public Connection getConnection() throws Exception{
		try{
			String connectionURL = "jdbc:mysql://localhost:3306/Test";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// java.sql.Driver d = new com.mysql.jdbc.Driver();
			// connection = DriverManager.getConnection(connectionURL, "root", "123456");
			return connection;
		}
		// catch (SQLException e){
		// 	throw e;
		// }
		catch (Exception e){
			throw e;
		}
	}
}