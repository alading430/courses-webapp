package it.courses.repository.dao;

import it.courses.domain.PageRequest;
import it.courses.domain.User;
import it.courses.repository.mapper.LoginMapper;
import it.courses.repository.mapper.UserMapper;
import it.courses.utility.CommonUtil;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author fvaleri
 * 
 */
@Repository
public class UserDao {

	private static final Logger LOGGER = Logger.getLogger(UserDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public User selectUserLogin(String username, String password) throws Exception {
		LOGGER.debug("Select user login");
		String query = "select u.id, u.name, p.code as profile from users u, profiles p where u.profile = p.id "
				+ "and u.username = ? and u.password = ?";
		List<User> users = jdbcTemplate.query(query, new Object[] { username, password }, new LoginMapper());
		if (users.size() == 1)
			return users.get(0);
		else
			return null;
	}

	public User selectUser(String username, String profilo) throws Exception {
		LOGGER.debug("Select user " + username);
		String query = "select u.id, u.name, p.code as profile, u.surname, u.email, u.birth_date, u.address, u.phone "
				+ "from users u, profiles p where u.profile = p.id and u.username = ? and p.code = ?";
		List<User> users = jdbcTemplate.query(query, new Object[] { username, profilo }, new UserMapper());
		if (users.size() == 1)
			return users.get(0);
		else
			return null;
	}

	public List<User> selectCourseStudents(String course, PageRequest page) throws Exception {
		LOGGER.debug("Select course students");
		String query = "select u.id, u.name, p.code as profile, u.surname, u.email, u.birth_date, u.address, u.phone "
				+ "from students_courses s, users u, profiles p where s.student = u.id and u.profile = p.id "
				+ "and p.code = 'STUD' and s.course = (select id from courses where name = ?)";

		if (page.getDirection().equals("next"))
			query += " and u.id > ? order by u.id asc limit ?";
		if (page.getDirection().equals("prev"))
			query += " and u.id < ? order by u.id desc limit ?";

		List<User> users = jdbcTemplate.query(query, 
			new Object[] { course, page.getLastSeen(), page.getSize() },
			new UserMapper());

		if (page.getDirection().equals("prev"))
			CommonUtil.sortUsers(users);
		return users;
	}

	public void insertEnrollment(Long studId, Long courseId) throws Exception {
		LOGGER.debug("Insert enrollment");
		String query = "insert into students_courses (student, course) values (?, ?)";
		jdbcTemplate.update(query, new Object[] { studId, courseId });
	}

}
