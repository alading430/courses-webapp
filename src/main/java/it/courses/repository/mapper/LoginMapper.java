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
public class LoginMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int index) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setName(rs.getString("name"));
		user.setProfile(rs.getString("profile"));
		return user;
	}

}
