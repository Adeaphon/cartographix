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
}

/**
 * Initialises the view model and generates the seed map.
 */
$(document).ready(function() {
	var model = new MapViewModel();
	ko.applyBindings(model);
	model.getMap();
});