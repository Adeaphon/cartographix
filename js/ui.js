var zoomhandle = $( "#zoom-handle" );
$( "#zoom-slider" ).slider({
	value: 14,
	min: 5,
	max: 20,
	create: function() {
		zoomhandle.text( $( this ).slider( "value" ) );
	},
	slide: function( event, ui ) {
		zoomhandle.text( ui.value );
	}
});

var huehandle = $( "#hue-handle" );
$( "#hue-slider" ).slider({
	value: 240,
	min: 0,
	max: 330,
	step: 30,
	create: function() {
		var hue = $( this ).slider( "value" );
		huehandle.css("background-color", tinycolor({h:hue, s:49.79, l:47.65}).toHexString());
	},
	slide: function( event, ui ) {
		huehandle.css("background-color", tinycolor({h: ui.value, s:49.79, l:47.65}).toHexString());
	}
});

$("#image-wrapper").hide();