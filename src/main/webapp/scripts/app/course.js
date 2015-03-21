"use strict";

var Course = (function($) {
	
	var getPopularCourses = function() {
		Common.startLoading();
		$.getJSON("popularCourses", {"lm":5})
			.done(function(res) {
				Common.stopLoading();
				if (!Common.isEmpty(res)) {
					var view = "<h2>Popular courses</h2><table class=\"table table-striped\">\
						<tr><th>Name</th><th>Description</th><th>Other</th></tr>";
					$.each(res, function(index, value) {
						view += "<tr><td>"+value.name+"</td><td>"+value.description
							+"</td><td>Prof: "+value.other+"</td></tr>";
					});
					view += "</table>";
					$("#result").empty().html(view);
				} else
					Common.printError("No result found");
			}).fail(function() {
				Common.stopLoading();
				Common.printError();
			});
	};
    
    var getUserCourses = function(username, profile, page) {
    	Common.startLoading();
		$.getJSON("userCourses", {"un":username, "pr":profile, "pg":page})
			.done(function(res) {
				Common.stopLoading();
				if (!Common.isEmpty(res)) {
					var view = "<h2>My courses</h2><table class=\"table table-striped\">\
						<tr><th>#</th><th>Name</th><th>Description</th><th>Start</th><th>Other</th></tr>";
					$.each(res, function(index, value) {
						view += "<tr><td>"+value.id+"</td><td class=\"cname\">"+value.name+"</td><td>"
							+value.description+"</td><td>"+value.startDate+"</td>";
						if (profile=="PROF")
							view += "<td><a class=\"studs\" href=\"#\"><span class=\"glyphicon glyphicon-eye-open\">\
								</span>Students:<span class=\"nstud\">"+value.other+"</span></a></td></tr>";
						else if (profile=="STUD")
							view += "<td>Prof: "+value.other+"</td></tr>";
					});
					view += "</table>";
					$("#result").empty().html(view);
					Common.appendPager("result", "userc", "ac=userc", res[0].id, res[res.length-1].id);
					
					//event handler
					$("#result").on("click", ".studs", function(e){
			    		e.preventDefault();
			    		var nstud = $(this).find(".nstud").html();
			    		if (nstud>0) {
			    			var cname = $(this).parent().siblings(".cname").html();
			    			Common.pushState("ac=studc&cn="+cname+"&pg=next-0");
			    		}
			    	});
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
	
	var getNewCourses = function(username, profile, page) {
		Common.startLoading();
		$.getJSON("newCourses", {"un":username, "pr":profile, "pg":page})
			.done(function(res) {
				Common.stopLoading();
				if (!Common.isEmpty(res)) {
					var view = "<h2>New courses</h2><table class=\"table table-striped\">\
						<tr><th>#</th><th>Name</th><th>Description</th><th>Start</th><th>Other</th><th></th></tr>";
					$.each(res, function(index, value) {
						view += "<tr><td>"+value.id+"</td><td class=\"cname\">"+value.name+"</td><td>"
							+value.description+"</td><td>"+value.startDate+"</td><td>Prof: "
							+value.other+"</td><td><a class=\"enroll\" href=\"#\">\
							<span class=\"glyphicon glyphicon-plus-sign\"></span>Enroll</a></td></tr>";
					});
					view += "</table>";
					$("#result").empty().html(view);
					Common.appendPager("result", "newco", "ac=newco", res[0].id, res[res.length-1].id);
					
					//event handler
					$("#result").on("click", ".enroll", function(event){
			    		e.preventDefault();
			    		var cname = $(this).parent().siblings(".cname").html();
			    		Common.pushState("ac=enros&cn="+cname);
			    	});					
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
	
	var renderCourseForm = function() {
		var view = "<h2>New course</h2><form id=\"courseForm\" role=\"form\">\
		  <div class=\"form-group\"><label class=\"control-label\" for=\"nome\">Name (*)</label>\
		    <input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"Insert course name...\"></div>\
		  <div class=\"form-group\"><label class=\"control-label\" for=\"descrizione\">Description (*)</label>\
		    <input type=\"text\" class=\"form-control\" id=\"description\" placeholder=\"Insert course description...\"></div>\
		  <div class=\"form-group\"><label class=\"control-label\" for=\"dataInizio\">Start date (*)</label>\
		    <input type=\"date\" class=\"form-control\" id=\"startDate\" placeholder=\"Insert start date...\"></div>\
	      <div class=\"form-group\"><label class=\"control-label\" for=\"dataFine\">End date</label>\
		    <input type=\"date\" class=\"form-control skip\" id=\"endDate\" placeholder=\"Insert end date...\"></div>\
	      <div class=\"form-group\"><label class=\"control-label\" for=\"crediti\">Credits</label>\
		    <input type=\"number\" class=\"form-control skip\" id=\"credits\" placeholder=\"Insert credits...\"></div>\
		  <div class=\"form-group\"><span class=\"help-block\">(*) Required</span></div>\
		  <button type=\"submit\" class=\"btn btn-success\">Create</button></form>";
		$("#result").empty().html(view);
		
		//event handler
		$("#courseForm").on("submit", function(e){
    		e.preventDefault();
    		if (Common.isValidForm("courseForm"))
    			Common.pushState("ac=creac");
    	});
	};
	
	var createCourse = function(username) {		
		var formValues = {
			"un": username,
			"cn": $("#name").val(),
			"de": $("#description").val(),
			"sd": $("#startDate").val(),
			"ed": $("#endDate").val(),
			"cr": $("#credits").val(),
		};
		
		Common.startLoading();
		$.ajax({type: "POST",
				dataType: "json",
				url: "createCourse",
				data: formValues
			}).done(function(res){
				Common.stopLoading();
				Common.printMessage(res.text);
			}).fail(function(){
				Common.stopLoading();
				Common.printError();
			});
	};
	
	return {
		getPopularCourses: getPopularCourses,	    
		getUserCourses: getUserCourses,		
		getNewCourses: getNewCourses,		
		renderCourseForm: renderCourseForm, 		
		createCourse: createCourse
	};	
	
})(jQuery);
