package it.courses.controller;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

/**
 * @author fvaleri
 * 
 */
@Controller
public class InitController {

	private static final Logger LOGGER = Logger.getLogger(InitController.class);
	
	@PostConstruct
	public void init() {
		LOGGER.info("Courses server ready!");	
	}
	
}
