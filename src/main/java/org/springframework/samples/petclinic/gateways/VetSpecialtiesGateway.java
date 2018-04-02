package org.springframework.samples.petclinic.gateways;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;

import com.mysql.jdbc.PreparedStatement;

public class VetSpecialtiesGateway extends MysqlGateway {

	public Set<Specialty> findSpecialties(int id) {
		Set<Specialty> set = new HashSet<Specialty>();
		String query = "SELECT * FROM vet_specialties WHERE vet_id = ?";
		
		try {
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next()) {
				Specialty specialty = new Specialty();
				specialty.setId(resultSet.getInt("specialty_id"));
				SpecialtiesGateway specialtiesGateway = new SpecialtiesGateway();
				specialty.setName(specialtiesGateway.findById(specialty.getId()));
				specialtiesGateway.disconnect();
				set.add(specialty);
			}
		} catch (Exception e) {
			System.err.println("Vet Spec Save Exception: " + e.getMessage());
		}
		return set;
	}
	
	
	
	public void save(Vet vet) {
		try {	        
			String query = "INSERT INTO vet_specialties(vet_id, specialty_id) "
	        		+ "VALUES(?, ?)";
	        PreparedStatement preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        for (Specialty specialty: vet.getSpecialties()) {
	        	//Insert new specialties if there is any
	        	SpecialtiesGateway specGateway = new SpecialtiesGateway();
	        	specGateway.save(specialty);
	        	specGateway.disconnect();
	        	//Insert the vet_specialty
	        	preparedStatement = (PreparedStatement) this.conn.prepareStatement(query);
	        	preparedStatement.setInt(1, vet.getId());
	        	preparedStatement.setInt(2, specialty.getId());
	        	preparedStatement.executeUpdate();
	        	preparedStatement.close();
	        }
	        
		} catch (Exception e) {
			System.err.println("VetSpec Save Exception: " + e.getMessage());
		}
	}
	
}
