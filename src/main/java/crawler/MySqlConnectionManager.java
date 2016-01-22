package crawler;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySqlConnectionManager {

	private String ip = "localhost";
	private String port = "3306";
	private String database = "recommender";
	private String userName = "root";
	private String password = "";
	private String connectionString;

	public MySqlConnectionManager() {
		connectionString = "jdbc:mysql://"
				+ this.ip
				+ ":"
				+ this.port
				+ "/"
				+ this.database
				+ "?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false&cachePrepStmts=true&useServerPrepStmts=true";
	}

	public MySqlConnectionManager(String ip, String port, String database,
			String userName, String password) {
		this.ip = ip;
		this.port = port;
		this.database = database;
		this.userName = userName;
		this.password = password;

		connectionString = "jdbc:mysql://"
				+ ip
				+ ":"
				+ port
				+ "/"
				+ database
				+ "?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";

	}

	public MySqlConnectionManager(String userName, String password,
			String connectionString) {
		this.userName = userName;
		this.password = password;
		this.connectionString = connectionString;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public Connection getDBConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionURL = connectionString;
			connection = DriverManager.getConnection(connectionURL, userName,
					password);

		} catch (Exception ex) {
			Logger.getLogger(MySqlConnectionManager.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		return connection;
	}

	public ResultSet ExecuteQuery(String sql) {
		ResultSet result = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionURL = connectionString;
			Connection connection = DriverManager.getConnection(connectionURL,
					userName, password);
			Statement stmt = connection.createStatement();
			String mysql = sql;

			result = stmt.executeQuery(mysql);

		} catch (Exception ex) {
			Logger.getLogger(MySqlConnectionManager.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		return result;
	}

	public int ExecuteUpdate(String sql) {
		int result = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionURL = connectionString;
			Connection connection = DriverManager.getConnection(connectionURL,
					userName, password);
			Statement stmt = connection.createStatement();

			String mysql = sql;

			result = stmt.executeUpdate(mysql);

			connection.close();
		} catch (Exception ex) {
			Logger.getLogger(MySqlConnectionManager.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		return result;
	}

	public int ExecuteUpdateBlob(String sql,
			List<InputStream> listOfInputStreams) {

		int result = 0;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			String connectionURL = connectionString;
			Connection connection = DriverManager.getConnection(connectionURL,
					userName, password);
			PreparedStatement statement = connection.prepareStatement(sql);

			for (int i = 0; i < listOfInputStreams.size(); i++) {
				statement.setBlob(i + 1, listOfInputStreams.get(i));
			}

			result = statement.executeUpdate();
			connection.close();
		} catch (Exception ex) {
			Logger.getLogger(MySqlConnectionManager.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return result;
	}

}
