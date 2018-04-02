package org.springframework.samples.petclinic.gateways;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.samples.petclinic.owner.Pet;

import com.mysql.jdbc.PreparedStatement;

public class PetsGateway extends MysqlGateway {
	public void save(Pet pet) {
		try {
			String query = "INSERT INTO pets(id, name, birth_date, type_id, owner_id) "
	        		+ "VALUES(?, ?, ?, ?, ?)";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, pet.getId());
	        preparedStatement.setString(2, pet.getName());
	        preparedStatement.setDate(3, (Date) pet.getBirthDate());
	        preparedStatement.setInt(4, pet.getType().getId());
	        preparedStatement.setInt(5, pet.getOwner().getId());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Pet Save Exception: " + e.getMessage());
		}
	}
	
	public Pet findById(int id) {
		Pet pet = new Pet();
		String query = "SELECT * FROM pet WHERE id = ?";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
			preparedStatement.setInt(0, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			pet.setId(resultSet.getInt("id"));
			pet.setName(resultSet.getString("name"));
			pet.setBirthDate(resultSet.getDate("brith_date"));
			PetTypeGateway petTypeGateway = new PetTypeGateway();
			pet.setType(petTypeGateway.findById(resultSet.getInt("type_id")));
			petTypeGateway.disconnect();
			OwnersGateway ownerGateway = new OwnersGateway();
			pet.setOwner(ownerGateway.findById(id));
			ownerGateway.disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pet;
	}
	
}
