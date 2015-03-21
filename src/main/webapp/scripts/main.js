"use strict";

//init code on document ready
jQuery(document).ready(function($) {
	
	User.renderLoginForm();
	
	//auto login
	if (Common.getCookie("username") && Common.getCookie("password"))
		User.login(Common.getCookie("username"), Common.getCookie("password"));
	
	//action routing
	Router.route();
	Common.bindState(Router.route);
	
});
