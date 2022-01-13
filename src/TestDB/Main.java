package TestDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	public static void main(String[] args) {
//		by using try with resources, it automatically closes the connection after it runs
//		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kenneth.a.lau\\IdeaProjects\\JavaLearning\\JavaUdemy2\\src\\TestDB\\testjava.db");
//			 Statement statement = conn.createStatement();) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kenneth.a.lau\\IdeaProjects\\JavaLearning\\JavaUdemy2\\src\\TestDB\\testjava.db");
//			associate statement with connection using conn.createstatement();
			Statement statement = conn.createStatement();
//			run code using statement.execute() with the same syntax as when using with command line
//			IF NOT EXISTS will only try to create table is there isn't already one with the same name
			statement.execute("CREATE TABLE IF NOT EXISTS contacts (name TEXT, phone INTEGER, email TEXT)");
//			getting into habit of closing statement first, then connection, try with resources will automatically close.
			statement.close();
			conn.close();;
		} catch (SQLException e) {
			System.out.println("Something went wrong: " + e.getMessage());
		}
	}
}
