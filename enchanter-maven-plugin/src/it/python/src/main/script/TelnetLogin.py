print 'set debug';
stream.setDebug( true );
print 'connecting...';
stream.connect( host );
stream.sendLine("");
stream.waitFor(':~>');
stream.sendLine('date');
print 'Server date is '+ stream.getLine();
ssh.disconnect();
