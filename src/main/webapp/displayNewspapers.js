


function renderNewspaperForYear(newspaper, years, currentyear) {
	var html;
	var nav = "<div class=\"btn-toolbar mb-2 mb-md-0\"><div class=\"btn-group mr-2\" id=\"year-nav\"></div>";
	
	$("#primary-show").html(nav);
	$("#primary-show").append("<div id=\"year-show\"><h1> show me a newspaper</h1></div>");
	
	var currentLocationHash = location.hash; 
	for(var i = 0; i < years.length; i++) {
		var year = years[i];
		if(year == currentyear) {
			$("#year-nav").append("<button class=\"btn btn-sm btn-outline-secondary active\">" + year + "</button>");
        } else {
        	var newLocation = editYearIndexInHash(location.hash, year);
        	$("#year-nav").append("<a href=\"" + newLocation + "\" class=\"btn btn-sm btn-outline-secondary\">" + year + "</>");
        }
	}
	
	var url = 'web-qa/dates/' + newspaper + '/' + currentyear;
	$.getJSON(url, {}, function(dates) {
        var html = "<h1>Datoer for " + newspaper + "</h1>";
        for(var i = 0; i < dates.length; i++) {
            var day = moment(dates[i]).format('YYYY-MM-DD');
            html += "<a href=\"#/newspapers/" + newspaper + "/" + day +"/0/\">" + day + "</a><br>";
        }
        $("#year-show").html(html);
    });
		
		
}

function editYearIndexInHash(origHash, newIndex) {
	var hashParts = origHash.split("/");
	hashParts[hashParts.length-2] = newIndex; // there's an empty place..
	var newHash = hashParts.join("/");
	return newHash;
}