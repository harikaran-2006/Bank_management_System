package com.bank;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	  private static final String URL      = "jdbc:mysql://localhost:0000/bank_db";
	    private static final String USER     = "User Name";
	    private static final String PASSWORD = "Your sqlbench password";
	    		 // change this

	    private static Connection connection;

	    private DBConnection() {}

	    public static Connection getConnection() throws SQLException {
	        if (connection == null || connection.isClosed()) {
	            try {
	                Class.forName("com.mysql.cj.jdbc.Driver");
	                connection = DriverManager.getConnection(URL, USER, PASSWORD);
	                System.out.println("Connected to database.");
	            } catch (ClassNotFoundException e) {
	                throw new SQLException("Driver not found.", e);
	            }
	        }
	        return connection;
	    }

	    public static void closeConnection() {
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	                System.out.println("Connection closed.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

