package apconic.customer.main.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbManager {
	private Connection connection;
	public DbManager() {
		try {
			this.connection = connect();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Connection connect() throws ClassNotFoundException, SQLException{       
		Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:~/crm;IFEXISTS=TRUE", "apconic", "customer" ); 
	}
	
	public Connection getConnection() {
		return connection;
	}

	public void disConnect() throws ClassNotFoundException, SQLException{       
		 this.connection.close();
	}
	
	public PreparedStatement query(String strSql) {

        try {
           PreparedStatement preparedStatement= connection.prepareStatement(strSql);
            return preparedStatement;
        } catch (java.sql.SQLException ex) {
            return null;
        }
    }
	
	public boolean updateSql(PreparedStatement preparedStatement) {
        try {
           preparedStatement.executeUpdate();
          return true;
        } catch (java.sql.SQLException ex) {
           return false;
          }
        	
	}

}
