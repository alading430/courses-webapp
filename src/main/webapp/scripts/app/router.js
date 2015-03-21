"use strict";

var Router = (function($) {
	
    var route = function(){
		//console.log(History.getState());
    	
		var username = Common.getCookie("username");
		var password = Common.getCookie("password");
		var profile = Common.getCookie("profile");
		var action = Common.getUrlParam("ac");
		
		if (username && password)
			switch (action) {
				case "userc":
					Course.getUserCourses(username, profile, Common.getUrlParam("pg"));
					break;
				case "newco":
					Course.getNewCourses(username, profile, Common.getUrlParam("pg"));
					break;
				case "cform":
					Course.renderCourseForm();
					break;
				case "creac":
					Course.createCourse(username);
					break;
				case "studc":
					User.getCourseStuds(Common.getUrlParam("cn"), Common.getUrlParam("pg"));
					break;
				case "enros":
					User.enrollStudent(username, Common.getUrlParam("cn"));
					break;
				case "logout":
					User.logout();
					Course.getPopularCourses();
					break;
				default:
					Course.getPopularCourses();
					break;
			} 
		else
			Course.getPopularCourses();
	};
	
	return {
		route: route
	};
	
})(jQuery);
