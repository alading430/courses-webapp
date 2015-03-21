"use strict";

//module pattern
var Common = (function($) {
	
	//private scope    
    var bindState = function(bind) {
    	History.Adapter.bind(window, "statechange", bind);
	};
    
    var pushState = function(state) {
		History.pushState(null, null, "?"+state);
	};
	
	var backState = function() {
		History.back();
	};
    
    var getUrlParam = function(name) {
		var hashes = window.location.href.slice(
			window.location.href.indexOf("?")+1).split("&");
		for (var i=0, j=hashes.length; i<j; i++) {
			var hash = hashes[i].split("=");
			if (hash[0]==name) return decodeURIComponent(hash[1]);
		}
		return null;
	};
	
	var getCookie = function(name) {
		return $.cookie(name);
	};
	
	var setCookie = function(name, value) {
		$.cookie(name, value, {expires:10});
	};
	
	var delCookie = function(name) {
		$.removeCookie(name);
	};
    
	var isEmpty = function(value) {
    	if (typeof value=="string" || value instanceof String)
    		value = value.trim();
    	if (value!==null && value!==undefined && value.length>0)
    		return false;
    	else
    		return true;
    };
    
    var getMd5Sum = function(value) {
    	if (!value) return; 
    	else return $.md5(value);
    };
    
    var startLoading = function() {
    	if ($("#loading").length===0) {
    		var view = "<div id=\"loading\" style=\"top:0px;left:45%;position:fixed;\
    			width:auto;z-index:5000;color:#ffffff;background-color:#5cb85c;\
    			padding:5px 10px;border:1px solid rgba(0,0,0,0);border-radius:5px;\
    			-webkit-border-radius:5px;-webkit-box-shadow:0 2px 4px rgba(0,0,0,0.2);\
				box-shadow:0 2px 4px rgba(0,0,0,0.2);font-weight:bold;font-size:1em;\
				display:none;vertical-align:baseline;\">Loading...</div>";
    		$("body").prepend(view);
    	}
    	$("#loading").show();
    	$("input[type=submit]").attr("disabled", true);
    	$("button[type=submit]").attr("disabled", true);
    };
    
    var stopLoading = function() {
    	$("button[type=submit]").removeAttr("disabled");
    	$("input[type=submit]").removeAttr("disabled");
    	$("#loading").hide();
    };
    
	var printError = function(value) {
    	if (value === undefined)
    		$("#result").empty().html("<h4 class=\"text-danger message\">Unexpected error...</h4>");
    	else
    		$("#result").empty().html("<h4 class=\"text-danger message\">"+value+"</h4>");
    };
    
    var printMessage = function(value) {
		$("#result").empty().html("<h4 class=\"message\">"+value+"</h4>");
    };
    
    var isValidForm = function(formId) {
    	var result = true;
    	$("#"+formId+" input:not(.skip)").each(function(i) {
    		if (isEmpty($(this).val())) {
    			result = false;
    			$(this).parent().addClass("has-error has-feedback")
			       .append("<span class=\"glyphicon glyphicon-remove form-control-feedback\"></span>");	    			
			} else {
				$(this).parent().removeClass("has-error has-feedback");
				$(this).next("span.glyphicon-remove").remove();
			}
    	});
    	return result;
	}; 
	
	var appendPager = function(recipientId, pagerClass, oldState, firstId, lastId) {
		$("#"+recipientId).append(
			"<ul class=\"pager\">\
				<li class=\"previous\"><a class=\""+pagerClass+"\" href=\"&pg=prev-"+firstId+"\">&larr; Prev</a></li>\
		        <li class=\"next\"><a class=\""+pagerClass+"\" href=\"&pg=next-"+lastId+"\">Next &rarr;</a></li>\
		    </ul>")
		.on("click", "."+pagerClass, function(e){
    		e.preventDefault();
    		pushState(oldState+$(this).attr("href"));
    	});
	};
	
	//public scope
	return {
		bindState: bindState,
		pushState: pushState,
		backState: backState,
		getUrlParam: getUrlParam,
		getCookie: getCookie,
	    setCookie: setCookie,
	    delCookie: delCookie,
	    isEmpty: isEmpty,
	    getMd5Sum: getMd5Sum,
	    startLoading: startLoading,
	    stopLoading: stopLoading,
		printError: printError,	
	    printMessage: printMessage,	
	    isValidForm: isValidForm,
		appendPager: appendPager
	};
	
})(jQuery);
