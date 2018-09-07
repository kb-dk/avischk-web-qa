

function renderEntityDisplay(currentEntities, currentEntityIndex) {

	var html;
	var nav = "<div class=\"btn-toolbar mb-2 mb-md-0\"><div class=\"btn-group mr-2\" id=\"edition-nav\"></div>";
	
	$("#primary-show").html(nav);
	$("#primary-show").append("<div id=\"edition-show\"><h1> show me a newspaper</h1></div>");
	
	 //<div class="btn-toolbar mb-2 mb-md-0">
     //<div class="btn-group mr-2" id="#edition-nav">
     //  <button class="btn btn-sm btn-outline-secondary">Share</button>
     //  <button class="btn btn-sm btn-outline-secondary">Export</button>
     //</div>
	var mapKeys = Object.keys(currentEntities);
	var currentLocationHash = location.hash; 
	for(var i = 0; i < mapKeys.length; i++) {
		var entity = mapKeys[i];
		if(i == currentEntityIndex) {
			$("#edition-nav").append("<button class=\"btn btn-sm btn-outline-secondary active\">" + entity + "</button>");
        } else {
        	var newLocation = editEntityIndexInHash(location.hash, i);
        	$("#edition-nav").append("<a href=\"" + newLocation + "\" class=\"btn btn-sm btn-outline-secondary\">" + entity + "</>");
        }
		
	}
	
	/*mapKeys.forEach(function(entity) {
		
		$("#edition-nav").append("<button class=\"btn btn-sm btn-outline-secondary\">" + entity + "</button>");
    });*/
	
	
	
	
    //for(var i = 0; i < currentEntities.length; i++) {
    	
    	
    	//for(var j = 0; j < currentEntities[i].length; i++) {
    		
    		
    		// byg nav linje med knapper for hver edition
    	//}
    	//tilføj visnings område. 
    	// vis den 'første' edition.
    	// giv mulighed for at bladre imellem sider i den edition (hvis der er nogen)
    	//html += "<a href=\"#/edition/" + entities[i].handle + "/\">" + entities[i].origRelpath + "</a><br>";
    //}
}

function editEntityIndexInHash(origHash, newIndex) {
	var hashParts = origHash.split("/");
	hashParts[hashParts.length-2] = newIndex; // there's an empty place..
	var newHash = hashParts.join("/");
	return newHash;
}