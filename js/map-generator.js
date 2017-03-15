console.log("Running map-generator.js");

/**
  * Main map production method. Generates an image for the specified location. 
  */
function getMap() {

	var query = "https://maps.googleapis.com/maps/api/staticmap";
	query += "?center=" + encodeURIComponent($('#location').val());
	query += "&key=AIzaSyBg7DmTKr7MMRzfcVT1Q9JXx-jI0xNiXME";

	query = setQueryFunctionality(query);
	query = setQueryStyle(query);
	
	$("#image").attr("src", query);
	$("#image-wrapper").show();
	
}

function setQueryFunctionality(query) {
	query += "&zoom=" + $('#zoom-slider').slider("value");
	
	var size = $('#size-slider').slider("value");
	
	switch(size){
	case 1:
		query += "&scale=" + 1;
		query += "&size=" + "300x340";
	case 2:
		query += "&scale=" + 1;
		query += "&size=" + "600x640";
	case 3:
		query += "&scale=" + 2;	
		query += "&size=" + "600x640";
	}

	query += "&maptype=" + "roadmap";
	
	return query;
}

function setQueryStyle(query){
	
	var hue = $('#hue-slider').slider("value");
	var colourful = !$('#greyscale').is(':checked');

	var dark = "0x" + tinycolor({h:hue, s:37.78*colourful, l:26.47}).toHex();
	var medium = "0x" + tinycolor({h:hue, s:43.01*colourful, l:37.85}).toHex();
	var light = "0x" + tinycolor({h:hue, s:49.79*colourful, l:47.65}).toHex();
	
	var road = "0xffffff";
	var attraction = "0xffffff";
	var base = "0xffffff";
		
	var water = dark;

	var town = light;
	var country = medium;
	
	if ($('#parks').is(':checked')){
		var park = dark;
	} else {
		var park = medium
	}
		
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
	if ($('#universities').is(':checked')){
		query += "&style=feature:poi.school%7Ccolor:" + attraction;
	} else {
		query += "&style=feature:poi.school%7Ccolor:" + town;
	}
	
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
	
	//Hide labels
	query += "&style=feature:all%7Celement:labels%7Cvisibility:off";
	
	return query;
}

getMap();