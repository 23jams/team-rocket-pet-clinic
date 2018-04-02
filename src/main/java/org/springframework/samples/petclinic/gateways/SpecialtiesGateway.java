package org.springframework.samples.petclinic.gateways;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
