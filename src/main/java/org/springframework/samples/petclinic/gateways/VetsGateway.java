package org.springframework.samples.petclinic.gateways;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.samples.petclinic.vet.Vet;

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
			}
		} catch (Exception e) {
			System.err.println("Save Exception: " + e.getMessage());
		}
		return collection;
	}
	
}
