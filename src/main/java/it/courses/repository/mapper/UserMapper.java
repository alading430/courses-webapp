package it.courses.repository.mapper;

import it.courses.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author fvaleri
 * 
 */
public class UserMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int index) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setName(rs.getString("name"));
		user.setProfile(rs.getString("profile"));
		user.setSurname(rs.getString("surname"));
		user.setEmail(rs.getString("email"));
		if (rs.getObject("birth_date") != null)
			user.setBirthDate(rs.getDate("birth_date"));
		if (rs.getObject("address") != null)
			user.setAddress(rs.getString("address"));
		if (rs.getObject("phone") != null)
			user.setPhone(rs.getString("phone"));
		return user;
	}

}
