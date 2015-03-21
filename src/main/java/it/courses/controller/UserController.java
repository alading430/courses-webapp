package it.courses.controller;

import it.courses.domain.Message;
import it.courses.domain.PageRequest;
import it.courses.domain.User;
import it.courses.service.UserService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST interface.
 * 
 * @author fvaleri
 * 
 */
@Controller
public class UserController {

	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/userLogin", method=RequestMethod.GET)
	@ResponseBody
	public User userLogin(HttpServletRequest req) throws Exception {
		LOGGER.info("Request to "+req.getRequestURI());
		String username = req.getParameter("un");
		String password = req.getParameter("pw");
		return userService.checkUserLogin(username, password);
	}
	
	@RequestMapping(value="/courseStudents", method=RequestMethod.GET)
	@ResponseBody
	public List<User> courseStudents(HttpServletRequest req) throws Exception {
		LOGGER.info("Request to "+req.getRequestURI());
		String course = req.getParameter("cn");
		PageRequest page = new PageRequest(req.getParameter("pg"));
		return userService.getCourseStudents(course, page);
	}
	
	@RequestMapping(value="/enrollStudent", method=RequestMethod.POST)
	@ResponseBody
	public Message enrollStudent(HttpServletRequest req) throws Exception {
		LOGGER.info("Request to "+req.getRequestURI());
		String username = req.getParameter("un");
		String course = req.getParameter("cn");
		return userService.enrollStudent(username, course);
	}

}
