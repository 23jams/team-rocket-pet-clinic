package org.springframework.samples.petclinic.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.samples.petclinic.owner.Owner;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * Gateway used for table owners. Only needed functions will be defined to be able to mimic what
 * the original app does with HSQLDB
 * @author olitr
 *
 */
public class OwnersGateway extends MysqlGateway {
	
	public void save(Owner owner) {
		try {
			String query = "INSERT INTO owners(id, first_name, last_name, address, city, telephone) "
	        		+ "VALUES(?, ?, ?, ?, ?, ?)";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, owner.getId());
	        preparedStatement.setString(2,  owner.getFirstName());
	        preparedStatement.setString(3, owner.getLastName());
	        preparedStatement.setString(4, owner.getAddress());
	        preparedStatement.setString(5, owner.getCity());
	        preparedStatement.setString(6, owner.getTelephone());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Owner Save Exception: " + e.getMessage());
		}
	}
	
	//Update owner table values based by id
	//Note: this was used to update new database if there was inconsistencies. However 
	public void update(Owner owner) {
		try {
			String query = "UPDATE owners SET (first_name = ?, last_name = ?, address = ?, city = ?, telephone = ? "
					+ " WHERE id = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setString(1,  owner.getFirstName());
	        preparedStatement.setString(2, owner.getLastName());
	        preparedStatement.setString(3, owner.getAddress());
	        preparedStatement.setString(4, owner.getCity());
	        preparedStatement.setString(5, owner.getTelephone());
	        preparedStatement.setInt(6, owner.getId());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Owner Update Exception: " + e.getMessage());
		}
	}
	
	//Delete owner entry from owner table
	public void delete(Owner owner) {
		try {
			String query = "DELETE FROM owners WHERE first_name = ?, last_name = ?, address = ?, city = ?, telephone = ?, id = ?";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setString(1,  owner.getFirstName());
	        preparedStatement.setString(2, owner.getLastName());
	        preparedStatement.setString(3, owner.getAddress());
	        preparedStatement.setString(4, owner.getCity());
	        preparedStatement.setString(5, owner.getTelephone());
	        preparedStatement.setInt(6, owner.getId());
	        preparedStatement.executeUpdate();
	        preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Owner Update Exception: " + e.getMessage());
		}
	}
	
	public Collection<Owner> findByLastName(String lastName) {
		Collection<Owner> collection = new ArrayList<Owner>();
		String query = "SELECT * FROM owners";
		try {
			Statement statement = (Statement) this.conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				Owner owner = new Owner();
				owner.setId(resultSet.getInt("id"));
				owner.setFirstName(resultSet.getString("first_name"));
				owner.setLastName(resultSet.getString("last_name"));
				owner.setAddress(resultSet.getString("address"));
				owner.setCity(resultSet.getString("city"));
				owner.setTelephone(resultSet.getString("telephone"));
				collection.add(owner);
			}
		} catch (SQLException e) {
			System.err.println("findByLastName Exception: " + e.getMessage());
		}
		return collection;
	}
	
	public Owner findById(int id) {
		Owner owner = new Owner();
		String query = "SELECT * FROM owners WHERE id = ?";
		try {
			PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
			preparedStatement.setInt(0, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			owner.setId(resultSet.getInt("id"));
			owner.setFirstName(resultSet.getString("first_name"));
			owner.setLastName(resultSet.getString("last_name"));
			owner.setAddress(resultSet.getString("address"));
			owner.setCity(resultSet.getString("city"));
			owner.setTelephone(resultSet.getString("telephone"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return owner;
	}
}
