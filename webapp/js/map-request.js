/**
 * Object representing a map request for a particular location. 
 * Can be converted to a google maps query, and displayed using the "display" method.
 *
 * place     - The string describing the center of this map.
 * zoom      - How zoomed in the map is. https://developers.google.com/maps/documentation/static-maps/intro#Zoomlevels
 * size      - Relative size of the map as an integer from 1 (smallest) to 3 (biggest). Affects both dimensions and scale. https://developers.google.com/maps/documentation/static-maps/intro#scale_values
 * hue       - The hue to use for all colours. A value from 0 to 360. Uses a HSL model rather than HSV. 
 * greyscale - Whether or not the map should be grey. 
 * schools   - Whether or not schools should be highlighted. Optional because some campuses cover a lot of ground.
 * parks     - Whether or not parkland should be highlighted. Looks interesting in areas without water (but not in national parks).
 */
function MapRequest(place, zoom, size, hue, greyscale, schools, parks){
	var self = this;
	
	// The string describing the center of this map.
	self.place = place;
	
	// How zoomed in the map is. https://developers.google.com/maps/documentation/static-maps/intro#Zoomlevels
	self.zoom = zoom;
	
	// Relative size of the map as an integer from 1 (smallest) to 3 (biggest). Affects both dimensions and scale. https://developers.google.com/maps/documentation/static-maps/intro#scale_values
	self.size = size;
	
	// A visual representation of size. S, M, or L.
	self.sizeIcon = nameSize(size);
	
	// The hue to use for all colours. A value from 0 to 360. Uses a HSL model rather than HSV. 
	self.hue = hue;
	
	// Whether or not the map should be grey. 
	self.greyscale = greyscale;
	
	// Whether or not schools should be highlighted. Optional because some campuses cover a lot of ground.
	self.schools = schools;
	
	// Whether or not parkland should be highlighted. Looks interesting in areas without water (but not national parks).
	self.parks = parks;

	/**
	 * Draws this map in the main viewport.
	 */
	self.display = function(){
		$("#image").attr("src", self.asQuery());
		$("#image-wrapper").show();
	}
	
	/**
	 * Converts this object into a qoogle maps query.
	 */
	self.asQuery = function(){
		var query = "https://maps.googleapis.com/maps/api/staticmap";
		
		query += "?key=AIzaSyBg7DmTKr7MMRzfcVT1Q9JXx-jI0xNiXME";

		query += "&center=" + encodeURIComponent(self.place);
		query += "&zoom=" + self.zoom;
		
		var size = self.size;
		
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
		var hue = self.hue;
		var colourful = !self.greyscale;

		var dark = "0x" + tinycolor({h:hue, s:37.78*colourful, l:26.47}).toHex();
		var medium = "0x" + tinycolor({h:hue, s:43.01*colourful, l:37.85}).toHex();
		var light = "0x" + tinycolor({h:hue, s:49.79*colourful, l:47.65}).toHex();
		
		var road = "0xffffff";
		var attraction = "0xffffff";
		var base = "0xffffff";
			
		var water = dark;

		var town = light;
		var country = medium;
		
		if (self.parks){
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
		if (self.schools){
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
}
