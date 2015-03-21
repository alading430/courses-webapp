package it.courses.service;

import it.courses.domain.Course;
import it.courses.domain.Message;
import it.courses.domain.PageRequest;
import it.courses.domain.User;
import it.courses.repository.dao.CourseDao;
import it.courses.repository.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Each service is a singleton by default.
 * 
 * @author fvaleri
 * 
 */
@Service
public class CourseService {

	private static final Logger LOGGER = Logger.getLogger(CourseService.class);
	
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private UserDao userDao;
	
	public List<Course> getPopularCourses(Integer limit) throws Exception {
		LOGGER.info("Get popular courses");
		List<Course> courses = courseDao.selectPopularCourses(limit);
		LOGGER.debug("Courses found: "+courses.size());
        return courses;
	}
	
	public List<Course> getUserCourses(String username, String profilo, PageRequest page) throws Exception {
		LOGGER.info("Get user courses");
		List<Course> courses = new ArrayList<Course>();
		
		if (profilo.equals("PROF")) 
			courses = courseDao.selectProfessorCourses(username, profilo, page);
		else if (profilo.equals("STUD")) 
			courses = courseDao.selectStudentCourses(username, profilo, page);
		
		LOGGER.debug("Courses found: "+courses.size());		
        return courses;
	}
	
	public List<Course> getNewCourses(String username, String profilo, PageRequest page) throws Exception {
		LOGGER.info("Get new courses");
		List<Course> courses = courseDao.selectNewCourses(username, profilo, page);
		LOGGER.debug("Courses found: "+courses.size());
        return courses;
	}
	
	public Message createCourse(String username, Course course) throws Exception {
		LOGGER.info("Create course");		
		User user = userDao.selectUser(username, "PROF");
		courseDao.insertCourse(user.getId(), course);
		String text = "Course creation success";
		LOGGER.debug(text);
		return new Message(text);
	}

}
