package org.springframework.samples.petclinic.gateways;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class PetTypeGateway extends MysqlGateway {
	
	public PetType findByName(String name) {
		PetType type = new PetType();
		String query = "SELECT * FROM types WHERE name = ?";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
			preparedStatement.setString(0, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			type.setId(resultSet.getInt("id"));
			type.setName(name);
		} catch (SQLException e) {
			System.err.println("Exception: " + e.getMessage());
		}
		return type;
	}
	
	public PetType findById(int id) {
		PetType type = new PetType();
		String query = "SELECT * FROM types WHERE id = ?";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
			preparedStatement.setInt(0, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			type.setId(id);
			type.setName(resultSet.getString("name"));
		} catch (SQLException e) {
			System.err.println("Exception: " + e.getMessage());
		}
		return type;
	}
	
	public Collection<PetType> findPetTypes() {
		Collection<PetType> collection = new ArrayList<PetType>();
		String query = "SELECT * FROM types";
		try {
			Statement statement = (Statement) this.conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				PetType petType = new PetType();
				petType.setId(resultSet.getInt("id"));
				petType.setName(resultSet.getString("name"));
				collection.add(petType);
			}
			
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		return collection;
	}
	
	public void save(PetType type) {
		try {
			String query = "INSERT INTO types(id, name) "
	        		+ "VALUES(?, ?)";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, type.getId());
	        preparedStatement.setString(2, type.getName());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("PetType Save Exception: " + e.getMessage());
		}
	}
	
	//Update types table values based by id
	public void update(PetType type) {
		try {
			String query = "UPDATE types SET name = ? "
	        		+ "WHERE id = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setString(1, type.getName());
	        preparedStatement.setInt(2, type.getId());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("PetType Update Exception: " + e.getMessage());
		}
	}
}
