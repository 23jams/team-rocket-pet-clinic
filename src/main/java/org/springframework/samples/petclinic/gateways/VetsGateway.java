package org.springframework.samples.petclinic.gateways;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;


import org.springframework.samples.petclinic.vet.Vet;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class VetsGateway extends MysqlGateway {

	public Collection<Vet> findAll() {
		Collection<Vet> collection = new ArrayList<Vet>();
		String query = "SELECT * FROM vets";
		try {
			Statement statement = (Statement) this.conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Vet vet = new Vet();
				vet.setId(resultSet.getInt("id"));
				vet.setFirstName(resultSet.getString("first_name"));
				vet.setLastName(resultSet.getString("last_name"));
				VetSpecialtiesGateway vetSpecGateway = new VetSpecialtiesGateway();
				vet.setSpecialtiesInternal(vetSpecGateway.findSpecialties(vet.getId()));
				collection.add(vet);
				vetSpecGateway.disconnect();
			}
		} catch (Exception e) {
			System.err.println("Save Exception: " + e.getMessage());
		}
		return collection;
	}
	
	public void save(Vet vet) {
		try {
			//Insert into vet table
			String query = "INSERT INTO vets(id, first_name, last_name) "
	        		+ "VALUES(?, ?, ?)";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, vet.getId());
	        preparedStatement.setString(2, vet.getFirstName());
	        preparedStatement.setString(3, vet.getLastName());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
	        
	        
		} catch (Exception e) {
			System.err.println("Vets Save Exception: " + e.getMessage());
		}
	}
	
	//Delete
	public void delete(Vet vet) {
		try {
			String query = "DELETE FROM vets WHERE id = ?, first_name = ?, last_name = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, vet.getId());
	        preparedStatement.setString(2, vet.getFirstName());
	        preparedStatement.setString(3, vet.getLastName());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
			
		} catch (Exception e) {
			System.err.println("Vets Delete Exception: " + e.getMessage());
		}
	}
	
	//Update vet table values based by id
	public void update(Vet vet) {
		try {
			String query = "UPDATE vets SET first_name = ?, last_name = ? "
	        		+ "WHERE id = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setString(1, vet.getFirstName());
	        preparedStatement.setString(2, vet.getLastName());
	        preparedStatement.setInt(3, vet.getId());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
	        
	        
		} catch (Exception e) {
			System.err.println("Vets Update Exception: " + e.getMessage());
		}
	}
	
}
