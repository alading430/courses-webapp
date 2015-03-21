package it.courses.controller;

import it.courses.domain.Course;
import it.courses.domain.Message;
import it.courses.domain.PageRequest;
import it.courses.service.CourseService;
import it.courses.utility.CommonUtil;

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
public class CourseController {

	private static final Logger LOGGER = Logger.getLogger(CourseController.class);
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value="/popularCourses", method=RequestMethod.GET)
	@ResponseBody
	public List<Course> popularCourses(HttpServletRequest req) throws Exception {
		LOGGER.info("Request to "+req.getRequestURI());
		Integer limit = new Integer(req.getParameter("lm"));
		return courseService.getPopularCourses(limit);
	}
	
	@RequestMapping(value="/userCourses", method=RequestMethod.GET)
	@ResponseBody
	public List<Course> userCourses(HttpServletRequest req) throws Exception {
		LOGGER.info("Request to "+req.getRequestURI());
		String username = req.getParameter("un");
		String profile = req.getParameter("pr");
		PageRequest page = new PageRequest(req.getParameter("pg"));
		return courseService.getUserCourses(username, profile, page);
	}
	
	@RequestMapping(value="/newCourses", method=RequestMethod.GET)
	@ResponseBody
	public List<Course> newCourses(HttpServletRequest req) throws Exception {
		LOGGER.info("Request to "+req.getRequestURI());
		String username = req.getParameter("un");
		String profile = req.getParameter("pr");
		PageRequest page = new PageRequest(req.getParameter("pg"));
		return courseService.getNewCourses(username, profile, page);
	}
	
	@RequestMapping(value="/createCourse", method=RequestMethod.POST)
	@ResponseBody
	public Message createCourse(HttpServletRequest req) throws Exception {
		LOGGER.info("Request to "+req.getRequestURI());		
		String username = req.getParameter("un");
		Course course = new Course();
		course.setName(req.getParameter("cn"));
		course.setDescription(req.getParameter("de"));
		course.setStartDate(CommonUtil.stringToDate(req.getParameter("sd")));
		course.setEndDate(!req.getParameter("ed").isEmpty() ? 
			CommonUtil.stringToDate(req.getParameter("ed")) : CommonUtil.stringToDate("2100-01-01"));
		course.setCredits(!req.getParameter("cr").isEmpty() ? 
			new Integer(req.getParameter("cr")) : new Integer("0"));
		return courseService.createCourse(username, course);
	}

}
