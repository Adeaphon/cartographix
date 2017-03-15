var zoomhandle = $( "#zoom-handle" );
$( "#zoom-slider" ).slider({
	value: 13,
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
	value: 210,
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

function nameSize(size){
	switch(size){
		case 1:
			return "S";
		case 2:
			return "M";
		case 3:
			return "L";
		default:
			return "";
	}
}

var sizehandle = $( "#size-handle" );
$( "#size-slider" ).slider({
	value: 3,
	min: 1,
	max: 3,
	create: function() {
		sizehandle.text( nameSize($( this ).slider( "value" )) );
	},
	slide: function( event, ui ) {
		sizehandle.text( nameSize(ui.value) );
	}
});

$("#image-wrapper").hide();