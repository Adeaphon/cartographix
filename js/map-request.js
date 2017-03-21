function MapRequest(place, zoom, hue, size, greyscale, schools, parks){
	var self = this;
	
	self.place = place;
	self.zoom = zoom;
	self.size = size;
	self.sizeIcon = nameSize(size);
	
	self.hue = hue;
	
	self.greyscale = greyscale;
	self.schools = schools;
	self.parks = parks;

	self.query = function(){
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
