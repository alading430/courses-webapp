package it.courses.utility;

import it.courses.domain.Course;
import it.courses.domain.User;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author fvaleri
 * 
 */
public class CommonUtil {
	
	public static String dateToString(Date date) {
		if (date!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(date);
		} else
			return null;
	}
	
	public static Date stringToDate(String date) throws Exception {
		if (date!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return new Date(sdf.parse(date).getTime());
		} else
			return null;
	}
	
	public static void sortUsers(List<User> users) {
		Collections.sort(users, new Comparator<User>() {
	        @Override
	        public int compare(User a, User b) {
	            return a.getId().compareTo(b.getId());
	        }
	    });
	}
	
	public static void sortCourses(List<Course> courses) {
		Collections.sort(courses, new Comparator<Course>() {
	        @Override
	        public int compare(Course a, Course b) {
	            return a.getId().compareTo(b.getId());
	        }
	    });
	}

}
