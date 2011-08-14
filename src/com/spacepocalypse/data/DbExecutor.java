package com.spacepocalypse.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbExecutor {
	private Connection dbConnection;

	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	public Connection getDbConnection() {
		if (dbConnection == null) {
			// This will load the MySQL driver, each DB has its own driver
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			// Setup the connection with the DB
			try {
				dbConnection = DriverManager.getConnection(
						"jdbc:mysql://localhost/beerdb?" +
						"user=root&password=3324newpasswordmysql");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dbConnection;
	}
	
	public static void main(String[] args) {
		DbExecutor dbe = new DbExecutor();
		try {
			PreparedStatement ps = dbe.getDbConnection().prepareStatement("select count(*) from beers");
			ps.execute();
			ResultSet results = ps.getResultSet();
			while (results.next()) {
				System.out.println("RESULT: " + results.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				dbe.getDbConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
