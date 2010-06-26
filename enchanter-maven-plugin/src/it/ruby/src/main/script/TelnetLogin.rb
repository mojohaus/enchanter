$conn.connect( $host );
$conn.sendLine("");
$conn.setTimeout( 1000 );
$conn.waitFor( "ogin:" );
