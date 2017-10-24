/**
 * Checks to see if the supplied maps will fit in the supplied area.
 * Treats large maps as the base, and considers things relative to them.
 *
 * larges  - The number of large maps
 * mediums - The number of medium maps
 * smalls  - The number of small maps
 * height  - How many large maps can be stretched on the y axis
 * width   - How many large maps can be stretched on the x axis 
 */
function mapsFit(larges, mediums, smalls, height, width){
	var area = height * width;
	return (larges <= area) && 
		   (4*larges + mediums <= 4*area) && 
		   (16*larges + 4 * mediums + smalls <= 16*area);
}

/**
 * Main View Model used by knockout. Contains a list of map requests used to generate history. 
 */
function MapViewModel(){
	var self = this;
	
	// Array of all maps that have been generated.
	self.maps = ko.observableArray([]);	
	
	 /**
	  * Main map production method. 
	  * Generates a map for the specified location and shows it in the main view window.
	  * The generated map is saved in the history so that it can be redrawn later or used to produce a tiled map.
	  */
	self.getMap = function() {
		var map = self.getMapRequest();
		
		self.maps.push(map);
		
		map.display();
	}
	
	/**
	 * Produces a MapRequest object 
	 */
	self.getMapRequest = function() {
		return new MapRequest($('#location').val(), 
							  $('#zoom-slider').slider("value"), 
							  $('#size-slider').slider("value"), 
							  $('#hue-slider').slider("value"), 
							  $('#greyscale').is(':checked'), 
							  $('#universities').is(':checked'), 
							  $('#parks').is(':checked') );
	}

	// Last map that was removed
	self.removedMap = null;
	
	// Index the last map was removed from
	self.removalIndex = 0;
	
	/**
	 * Removes the supplied map from the history. Saves it incase it was deleted by accident.
	 *
	 * map - The map to delete.
	 */
	self.removeMap = function(map) {
		self.removedMap = map;
		self.removalIndex = self.maps.indexOf(map);
		self.maps.remove(map);	
		$("#deletion-tab").show();
	}
	
	/**
	 * Restores the last deleted map to its original position.
	 */
	self.restoreMap = function() {
		if(self.removedMap){
			self.maps.splice(self.removalIndex, 0, self.removedMap);
			self.removedMap = null;
			$("#deletion-tab").hide();
		}
	}
	
	self.tileMaps = function() {
		var width = $('#tiling-width').val();
		var height = $('#tiling-height').val();
		
		if(mapsFit(self.maps().filter(function(x){return x.size == 3}).length,
					  self.maps().filter(function(x){return x.size == 2}).length,
					  self.maps().filter(function(x){return x.size == 1}).length, 
					  parseInt(width), 
					  parseInt(height))) {
			var request = $.ajax({
				method: 'GET',
				url: '/tiler-0.1.0/tile?', 
				data: {height: height, width: width}
			});
	
			request.done(function(result) {
				alert(result);
			});
			
			request.fail(function(jqXHR, message) {
				alert("Botched it: " + message);
			});
			
		} else {
			alert("Doesn't fit!");
		}
	}
	
}


/**
 * Initialises the view model and generates the seed map.
 */
$(document).ready(function() {
	var model = new MapViewModel();
	ko.applyBindings(model);
	model.getMap();
});