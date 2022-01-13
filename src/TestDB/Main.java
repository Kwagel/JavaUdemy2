package TestDB;

import java.sql.*;

public class Main {
	public static final String DB_NAME = "testjava.db";
	public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\kenneth.a" +
			".lau\\IdeaProjects\\JavaLearning\\JavaUdemy2\\src\\TestDB\\testjava.db";
	
	public static final String TABLE_CONTACTS = "contacts";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_EMAIL = "email";
	
	public static void main(String[] args) {
//		by using try with resources, it automatically closes the connection after it runs
//		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\kenneth.a
//		.lau\\IdeaProjects\\JavaLearning\\JavaUdemy2\\src\\TestDB\\testjava.db");
//			 Statement statement = conn.createStatement();) {
		try {
			Connection conn = DriverManager.getConnection(CONNECTION_STRING);
//			associate statement with connection using conn.createstatement();
//			autocommit is automatically on for sqlite, so any changes are automatically committed and saved, this is
//			not the same for all databases, and you may need tp manually commit after you make changes
//			conn.setAutoCommit(false);
			Statement statement = conn.createStatement();
//			run code using statement.execute() with the same syntax as when using with command line
//			IF NOT EXISTS will only try to create table is there isn't already one with the same name
			statement.execute("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
			statement.execute("CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + " (" +
					COLUMN_NAME + " text, " +
					COLUMN_PHONE + " integer, " +
					COLUMN_EMAIL + " text" + ")");
			insertContact(statement,"Tim", 6545678, "tim@email.com");
			insertContact(statement,"Joe", 45632, "joe@anywhere.com");
			insertContact(statement,"Jane", 4829484, "jane@somewhere.com");
			insertContact(statement,"Fido", 9038, "dog@email.com");
			statement.execute("UPDATE " + TABLE_CONTACTS +
					" SET " + COLUMN_PHONE + "= 55667789 " +
					" WHERE " + COLUMN_NAME + "= 'Jane'");
			
			statement.execute("DELETE FROM " + TABLE_CONTACTS +
					" WHERE " + COLUMN_NAME + "= 'Joe'");

//			you can retrieve the data from an execute command by creating a ResultSet and giving the statements
// result set
//			ResultSet results = statement.getResultSet();
//			execute query automatically returns the result set
			ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_CONTACTS);
			while (results.next()) {
				System.out.println(results.getString(COLUMN_NAME) + " " + results.getInt(COLUMN_PHONE) + " " + results.getString(COLUMN_EMAIL));
			}
			results.close();
//			getting into habit of closing statement first, then connection, try with resources will automatically
//			close.
			statement.close();
			
			
			conn.close();
			;
		} catch (SQLException e) {
			System.out.println("Something went wrong: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private static void insertContact(Statement statement, String name, int phone, String email) throws SQLException {
		statement.execute("INSERT INTO " + TABLE_CONTACTS +
				"(" + COLUMN_NAME +
				", " + COLUMN_PHONE +
				", " + COLUMN_EMAIL +
				") " +
				"VALUES ('" +
				name + "', " +
				phone + ", '" +
				email +
				"')");
	}
}
