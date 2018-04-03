package org.springframework.samples.petclinic.gateways;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.samples.petclinic.visit.Visit;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class VisitsGateway extends MysqlGateway {
	

	public Visit findById(int id) {
		Visit visit = new Visit();
		String query = "SELECT * FROM visits WHERE id = ?";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
			preparedStatement.setInt(0, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			visit.setId(resultSet.getInt("id"));
			visit.setPetId(resultSet.getInt("pet_id"));
			visit.setDate(resultSet.getDate("visit_date"));
			visit.setDescription(resultSet.getString("description"));
			VisitsGateway visitGateway = new VisitsGateway();
			visitGateway.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return visit;
	}
	
	
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
	
	//Update visit table values based by id
	public void update(Visit visit) {
		try {
			String query = "UPDATE visits SET pet_id = ?, visit_date = ?, description = ? "
	        		+ "WHERE id = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1,  visit.getPetId());
	        preparedStatement.setDate(2, (Date) visit.getDate());
	        preparedStatement.setString(3, visit.getDescription());
	        preparedStatement.setInt(4, visit.getId());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Visit Update Exception: " + e.getMessage());
		}
	}
}
