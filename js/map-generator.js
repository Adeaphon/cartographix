console.log("Running map-generator.js");

/**
  * Main map production method. Generates an image for the specified location. 
  */
function getMap() {

	var query = "https://maps.googleapis.com/maps/api/staticmap";
	query += "?center=" + encodeURIComponent($('#Location').val());
	query += "&key="+encodeURIComponent($('#Key').val());

	query = setQueryFunctionality(query);
	query = setQueryStyle(query);
	
	$("#image").attr("src", query);
	
}

function setQueryFunctionality(query) {
	query += "&zoom=" + $('#zoom-slider').slider("value");
	query += "&size=" + "620x640";
	query += "&maptype=" + "roadmap";
	query += "&scale=" + 2;
	
	return query;
}

function setQueryStyle(query){
	var water = "0x2a315d";
	var park = water;
	var road = "0xffffff";
	var town = "0x3d72b6";
	var country = "0x37508a";
	var attraction = road;
	var base = road;
	
	query += "&style=feature:all%7Ccolor:" + base;
	
	//Water
	query += "&style=feature:water%7Ccolor:" + water;
	
	//This is used for unknown places of interest
	query += "&style=feature:poi%7Ccolor:" + water;
	
	//Road
	query += "&style=feature:road%7Ccolor:" + road;
	query += "&style=feature:transit%7Ccolor:" + road;
	
	//Town
	query += "&style=feature:landscape.man_made%7Ccolor:" + town;
	//if
	query += "&style=feature:poi.school%7Ccolor:" + town;
	
	//Country
	query += "&style=feature:landscape.natural%7Ccolor:" + country;
	
	//Park
	query += "&style=feature:poi.park%7Ccolor:" + park;
	query += "&style=feature:poi.sports_complex%7Ccolor:" + park;
	query += "&style=feature:poi.business%7Ccolor:" + park;
	
	//Attraction
	query += "&style=feature:poi.attraction%7Ccolor:" + attraction;
	query += "&style=feature:poi.place_of_worship%7Ccolor:" + attraction;
	query += "&style=feature:poi.government%7Ccolor:" + attraction;
	query += "&style=feature:poi.medical%7Ccolor:" + attraction;
	query += "&style=feature:poi.school%7Ccolor:" + attraction;
	
	//Hide labels
	query += "&style=feature:all%7Celement:labels%7Cvisibility:off";
	
	return query;
}
