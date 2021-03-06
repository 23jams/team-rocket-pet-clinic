package org.springframework.samples.petclinic.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;

import com.mysql.jdbc.PreparedStatement;

public class SpecialtiesGateway extends MysqlGateway {
	public String findById(int id) {
		String query = "SELECT * FROM specialties WHERE id = ?";
		String specialty = "";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
			preparedStatement.setInt(0, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			specialty = resultSet.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return specialty;
	}
	
	public int findByName(String name) {
		String query = "SELECT * FROM specialties WHERE name = ?";
		int id = 0;
		try {
			PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
			preparedStatement.setString(0, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			id = resultSet.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public void save(Specialty specialty) {
		try {	        
			String query = "INSERT INTO specialties(id, name) "
	        		+ "VALUES(?, ?)";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, specialty.getId());
	        preparedStatement.setString(2, specialty.getName());
	        preparedStatement.executeUpdate();
        	preparedStatement.close();
	        
		} catch (Exception e) {
			System.err.println("Specialty Save Exception: " + e.getMessage());
		}
	}
	
	//Delete specialty table values
	
	public void delete(Specialty specialty) {
		try {
			String query = "DELETE FROM specialties WHERE id = ?, name = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, specialty.getId());
	        preparedStatement.setString(2, specialty.getName());
	        preparedStatement.executeUpdate();
        	preparedStatement.close();
	        
		} catch (Exception e) {
			System.err.println("Specialty Delete Exception: " + e.getMessage());
		}
	}
	
	//Update specialty table values based by id
	public void update(Specialty specialty) {
		try {	        
			String query = "UPDATE specialties SET name = ? "
	        		+ "WHERE id = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setString(1, specialty.getName());
	        preparedStatement.setInt(2, specialty.getId());
	        preparedStatement.executeUpdate();
        	preparedStatement.close();
	        
		} catch (Exception e) {
			System.err.println("Specialty Update Exception: " + e.getMessage());
		}
	}
}
