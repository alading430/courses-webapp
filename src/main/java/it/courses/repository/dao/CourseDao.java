package it.courses.repository.dao;

import it.courses.domain.Course;
import it.courses.domain.PageRequest;
import it.courses.repository.mapper.CourseMapper;
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
public class CourseDao {

	private static final Logger LOGGER = Logger.getLogger(CourseDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Course selectCourse(String name) throws Exception {
		LOGGER.debug("Select course " + name);
		String query = "select id, name, description, start_date, "
				+ "end_date, credits, null as other from courses where name = ?";
		List<Course> list = jdbcTemplate.query(query, new Object[] { name }, new CourseMapper());
		if (list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	public List<Course> selectPopularCourses(Integer limit) throws Exception {
		LOGGER.debug("Select popular courses");
		String query = "select c.id, c.name, c.description, c.start_date, "
				+ "c.end_date, c.credits, concat(u.name, ' ', u.surname) as other, count(s.student) as idx "
				+ "from courses c, users u, students_courses s where c.professor = u.id and s.course = c.id "
				+ "group by s.course order by idx desc, c.id asc limit ?";
		List<Course> courses = jdbcTemplate.query(query, new Object[] { limit }, new CourseMapper());
		return courses;
	}

	public List<Course> selectProfessorCourses(String username, String profile, PageRequest page) throws Exception {
		LOGGER.debug("Select professor courses");
		String query = "select * from ( "
				+ "select c.id, c.name, c.description, c.start_date, c.end_date, c.credits, count(s.student) as other "
				+ "from courses c, users u, profiles p, students_courses s "
				+ "where c.professor = u.id and u.profile = p.id and s.course = c.id and u.username = ? and p.code = ? "
				+ "group by s.course union "
				+ "select c.id, c.name, c.description, c.start_date, c.end_date, c.credits, 0 as other "
				+ "from courses c, users u, profiles p where c.professor = u.id and u.profile = p.id "
				+ "and u.username = ? and p.code = ? and c.id not in (select distinct course from students_courses)) x";
		if (page.getDirection().equals("next"))
			query += " where x.id > ? order by id asc limit ?";
		if (page.getDirection().equals("prev"))
			query += " where x.id < ? order by id desc limit ?";

		List<Course> courses = jdbcTemplate.query(query, 
			new Object[] { username, profile, username, profile, page.getLastSeen(), page.getSize() }, 
			new CourseMapper());

		if (page.getDirection().equals("prev"))
			CommonUtil.sortCourses(courses);
		return courses;
	}

	public List<Course> selectStudentCourses(String username, String profile, PageRequest page) throws Exception {
		LOGGER.debug("Select student courses");
		String query = "select c.id, c.name, c.description, c.start_date, "
				+ "c.end_date, c.credits, concat(u.name, ' ', u.surname) as other from courses c, users u, "
				+ "(select c.id from courses c, users u, profiles p, students_courses s where s.student = u.id "
				+ "and u.profile = p.id and s.course = c.id and u.username = ? and p.code = ?) x "
				+ "where c.id = x.id and c.professor = u.id";
		if (page.getDirection().equals("next"))
			query += " and c.id > ? order by c.id asc limit ?";
		if (page.getDirection().equals("prev"))
			query += " and c.id < ? order by c.id desc limit ?";

		List<Course> courses = jdbcTemplate.query(query, 
			new Object[] { username, profile, page.getLastSeen(), page.getSize() },
			new CourseMapper());

		if (page.getDirection().equals("prev"))
			CommonUtil.sortCourses(courses);
		return courses;
	}

	public List<Course> selectNewCourses(String username, String profile, PageRequest page) throws Exception {
		LOGGER.debug("Select new courses");
		String query = "select c.id, c.name, c.description, c.start_date, "
				+ "c.end_date, c.credits, concat(u.name, ' ', u.surname) as other from courses c, users u  "
				+ "where c.professor = u.id and c.id not in (select c.id "
				+ "from courses c, users u, profiles p, students_courses s where s.student = u.id and s.course = c.id "
				+ "and u.profile = p.id and u.username = ? and p.code = ?)";
		if (page.getDirection().equals("next"))
			query += " and c.id > ? order by c.id asc limit ?";
		if (page.getDirection().equals("prev"))
			query += " and c.id < ? order by c.id desc limit ?";

		List<Course> courses = jdbcTemplate.query(query, 
			new Object[] { username, profile, page.getLastSeen(), page.getSize() },
			new CourseMapper());

		if (page.getDirection().equals("prev"))
			CommonUtil.sortCourses(courses);
		return courses;
	}

	public void insertCourse(Long profId, Course course) throws Exception {
		LOGGER.debug("Insert course");
		String query = "insert into courses (professor, name, description, "
				+ "start_date, end_date, credits) values (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(query,
			new Object[] { profId, course.getName(), course.getDescription(), 
				course.getStartDate(), course.getEndDate(),	course.getCredits() });
	}

}
