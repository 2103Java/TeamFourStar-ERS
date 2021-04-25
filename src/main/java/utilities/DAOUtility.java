package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOUtility {

	//This class holds the basic connect function. Currently operates on environment variables.
	
	private static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		if (connection == null ) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(System.getenv("rdsURL"), "postgres", System.getenv("rdsPassword"));			
			}
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
			return connection;	
	}
	
}
