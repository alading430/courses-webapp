package it.courses.service;

import it.courses.domain.Course;
import it.courses.domain.Message;
import it.courses.domain.PageRequest;
import it.courses.domain.User;
import it.courses.repository.dao.CourseDao;
import it.courses.repository.dao.UserDao;

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
public class UserService {

	private static final Logger LOGGER = Logger.getLogger(UserService.class);
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private CourseDao courseDao;
	
	public User checkUserLogin(String username, String password) throws Exception {
		LOGGER.info("Check user login");
		User user = userDao.selectUserLogin(username, password);		
		if (user!=null) {
			LOGGER.debug("Login success");
	        return user;
		} else {
			LOGGER.warn("Login error");
			return null;
		}
	}
	
	public List<User> getCourseStudents(String course, PageRequest page) throws Exception {
		LOGGER.info("Get course students");
		List<User> users = userDao.selectCourseStudents(course, page);
		LOGGER.debug("Users found: "+users.size());
        return users;
	}
	
	public Message enrollStudent(String username, String courseName) throws Exception {
		LOGGER.info("Enroll student");
		User user = userDao.selectUser(username, "STUD");
		Course course = courseDao.selectCourse(courseName);
	    userDao.insertEnrollment(user.getId(), course.getId());
		String text = "Student enrollment success";
		LOGGER.debug(text);
		return new Message(text);
	}

}
