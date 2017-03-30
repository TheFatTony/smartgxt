function getParameter(name) {
	// var dc = document.cookie;
	var dc = localStorage.getItem(name);
	var prefix = name + "=";
	if (dc != null) {
		var begin = 18;
		var end = begin + 2;
		return unescape(dc.substring(begin, end));
	} else
		return null;
}

// document.write("<div id=\"loading\"><img style=\"margin: 5px; float: right\"
// src=\"loading.png\" /></div>");
var locale = getParameter("sgxt.states.locale");
if (locale != "") {
	document.writeln("<meta name=\"gwt:property\" content=\"locale=" + locale
			+ "\"> ");
	if (locale == "ru") {
		document.write("<title>Загрузка...<\/title>");
	} else if (locale == "en") {
		document.write("<title>Loading...<\/title>");
	}
} else
	document.write("<title>Loading...<\/title>");

var scale = getParameter("sgxt.states.scale");
if (scale == "fl") {
	document
			.writeln("<link rel=\"stylesheet\" type=\"text/css\" href=\"smartgxt/css/smartgxt-scale-large.css\" />");
} else if (scale == "fh") {
	document
			.writeln("<link rel=\"stylesheet\" type=\"text/css\" href=\"smartgxt/css/smartgxt-scale-huge.css\" />");
}
