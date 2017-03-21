function MapModel(){
	var self = this;
	self.requests = ko.observableArray([]);	
	
		/**
	  * Main map production method. Generates an image for the specified location. 
	  */
	self.getMap = function() {
		console.log("Starting");
		var request = self.getMapRequest();
		
		self.requests.push(request);
		
		$("#image").attr("src", request.query);
		$("#image-wrapper").show();
	}
	
	self.getMapRequest = function(){
		return new MapRequest($('#location').val(), $('#zoom-slider').slider("value"), $('#hue-slider').slider("value"), $('#size-slider').slider("value"), $('#greyscale').is(':checked'), $('#universities').is(':checked'), $('#parks').is(':checked') );
	}

}

$(document).ready(function() {
	var model = new MapModel();
	ko.applyBindings(model);
	model.getMap();
});