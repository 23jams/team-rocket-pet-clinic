package org.springframework.samples.petclinic.gateways;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Will handle connections to mysql db.
 * @author Olivier Trépanier-Desfossés
 *
 */

public class MysqlGateway {

	String serverName;
	int port;
	String dbName;
	String user;
	String password;
	
	Connection conn;
	
	/**
	 * For the sake of milestone3, I will hardcode these but you should never
	 * hardcode db credentials in code.
	 */
	public MysqlGateway () {
		this.serverName = "localhost";
		this.port = 3306;
		this.dbName = "petclinic";
		this.user = "root";
		this.password = "4everdreamingfish1835!";
		this.connect();
	}
	
	private void connect() {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setServerName(this.serverName);
        dataSource.setPort(this.port);
        dataSource.setDatabaseName(this.dbName);
        dataSource.setUser(this.user);
        dataSource.setPassword(this.password);
        try {
        	conn = (Connection) dataSource.getConnection();
        } catch (Exception e) {
        	System.err.println("Exception: " + e.getMessage());
        }
	}
	
	public void disconnect() {
		try {
			conn.close();
		} catch (Exception e) {
        	System.err.println("Exception: " + e.getMessage());
		}
	}
	
}
