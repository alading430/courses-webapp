"use strict";

var User = (function($) {
	
	var login = function(username, password) {
		var result = false;
		Common.startLoading();		
		$.ajax({dataType: "json",
				async: false,
				url: "userLogin",
				data: {"un":username, "pw":password}
			}).done(function(res){
				result = true;
				Common.stopLoading();
				Common.setCookie("username", username);
				Common.setCookie("password", password);
				Common.setCookie("profile", res.profile);
				Common.setCookie("name", res.name);
				renderMainMenu(username, res.profile);
				$("#login").empty().html(
					"<p id=\"user\" class=\"navbar-right\">User: "
					+res.name+"<br>Profile: "+res.profile+"</p>");
			}).fail(function(){
				Common.stopLoading();
				logout();
				Common.printError("Login error");
			});
		return result;
	};
    
    var logout = function() {
    	Common.delCookie("username");
		Common.delCookie("password");
		Common.delCookie("name");
		Common.delCookie("profile");
    	$("#mainMenu,#result").empty().html("");
    	renderLoginForm();
    };
    
    var renderLoginForm = function() {
    	var view = "<form id=\"loginForm\" class=\"navbar-form navbar-right\" role=\"form\">\
            <div class=\"form-group\"><input id=\"username\" type=\"text\" placeholder=\"Username\" class=\"form-control\"/></div>\
    		<div class=\"form-group\"><input id=\"password\" type=\"password\" placeholder=\"Password\" class=\"form-control\"/></div>\
            <button type=\"submit\" class=\"btn btn-success\">Login</button></form>";
    	$("#login").empty().html(view);
    	
    	//event handler
    	$("#loginForm").on("submit", function(e){
    		e.preventDefault();   		
    		var username = $("#username").val();
    		var password = $("#password").val();
    		if (username && password) login(username, Common.getMd5Sum(password));
    	});
    };
    
    var renderMainMenu = function(username, profilo) {
    	var view = "<li id=\"mainMenu\" class=\"dropdown\"><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">\
    		Dropdown <span class=\"caret\"></span></a><ul class=\"dropdown-menu\" role=\"menu\">";
    	if (profilo=="STUD")
    		view += "<li><a class=\"mitem\" href=\"ac=userc&pg=next-0\">Show my courses</a></li>\
    			<li><a class=\"mitem\" href=\"ac=newco&pg=next-0\">Show new courses</a></li>";						
		else if (profilo=="PROF")
			view += "<li><a class=\"mitem\" href=\"ac=userc&pg=next-0\">Show my courses</a></li>\
    			<li><a class=\"mitem\" href=\"ac=cform\">Create new course</a></li>";
    	view += "<li class=\"divider\"></li><li><a class=\"mitem\" href=\"ac=logout\">\
    		<span class=\"glyphicon glyphicon-log-out\"></span> Logout</a></li></ul></li>";
    	$(".navbar-nav").append(view);
    	
    	//event handler
    	$(".navbar-nav").on("click", ".mitem", function(e){
    		e.preventDefault();
    		Common.pushState($(this).attr("href"));
    	});
    };
	
	var getCourseStuds = function(course, page) {
		Common.startLoading();
		$.getJSON("courseStudents", {"cn":course, "pg":page})
			.done(function(res) {
				Common.stopLoading();
				if (!Common.isEmpty(res)) {
					var view = "<h4>Enrolled students</h4><table class=\"table table-striped\">\
						<tr><th>#</th><th>Name</th><th>Surname</th><th>Birth</th>\
						<th>Address</th><th>Email</th><th>Phone</th></tr>";
					$.each(res, function(index, value) {
						view += "<tr><td>"+value.id+"</td><td>"+value.name+"</td><td>"
							+value.surname+"</td><td>"+value.birthDate+"</td><td>"+value.address
							+"</td><td>"+value.email+"</td><td>"+value.phone+"</td></tr>";
					});
					view += "</table>";
					$("#result").empty().html(view);
					Common.appendPager("result", "cours", "ac=studc&cn="+course, res[0].id, res[res.length-1].id);
				} else {
					var isPaginated = !($(".pager").length==0);
					if (isPaginated) Common.backState();
					else Common.printError("No result found");
				}
			}).fail(function() {
				Common.stopLoading();
				Common.printError();
			});
	};
	
	var enrollStudent = function(username, course) {
		Common.startLoading();		
		$.ajax({type: "POST",
				dataType: "json",
				url: "enrollStudent",
				data: {"un":username, "cn":course}
			}).done(function(res){
				Common.stopLoading();
				Common.printMessage(res.text);
			}).fail(function(){
				Common.stopLoading();
				Common.printError();
			});
	};
    
	return {		
		login: login,	    
	    logout: logout,	    
	    renderLoginForm: renderLoginForm,	
	    getCourseStuds: getCourseStuds,		
	    enrollStudent: enrollStudent
	};	
	
})(jQuery);
