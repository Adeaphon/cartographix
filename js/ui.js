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