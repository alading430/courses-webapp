package it.courses.repository.mapper;

import it.courses.domain.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 
 * @author fvaleri
 * 
 */
public class CourseMapper implements RowMapper<Course> {

	public Course mapRow(ResultSet rs, int index) throws SQLException {
		Course course = new Course();
		course.setId(rs.getLong("id"));
		course.setName(rs.getString("name"));
		course.setDescription(rs.getString("description"));
		course.setStartDate(rs.getDate("start_date"));
		if (rs.getObject("end_date") != null)
			course.setEndDate(rs.getDate("end_date"));
		if (rs.getObject("credits") != null)
			course.setCredits(rs.getInt("credits"));
		if (rs.getObject("other") != null)
			course.setOther(rs.getString("other"));
		return course;
	}

}
