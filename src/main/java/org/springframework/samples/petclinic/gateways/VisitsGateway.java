package org.springframework.samples.petclinic.gateways;

import java.sql.Date;
import java.sql.Timestamp;

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
	        Date date = new Date(((Timestamp) visit.getDate()).getTime());
	        preparedStatement.setDate(3, date);
	        preparedStatement.setString(4, visit.getDescription());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Visit Save Exception: " + e.getMessage());
		}
	}
}
