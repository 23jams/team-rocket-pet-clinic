package org.springframework.samples.petclinic.gateways;

import java.sql.Date;

import org.springframework.samples.petclinic.visit.Visit;

import com.mysql.jdbc.PreparedStatement;

public class VisitsGateway extends MysqlGateway {
	public void save(Visit visit) {
		try {
			String query = "INSERT INTO visits(id, pet_id, visit_date, description) "
	        		+ "VALUES(?, ?, ?, ?)";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, visit.getId());
	        preparedStatement.setInt(2,  visit.getPetId());
	        preparedStatement.setDate(3, (Date) visit.getDate());
	        preparedStatement.setString(4, visit.getDescription());
	        
	        preparedStatement.executeUpdate();
	        
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Save Exception: " + e.getMessage());
		}
	}
}
