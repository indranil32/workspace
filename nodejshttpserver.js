var http = require('http');
var fs = require('fs');
var url = require('url');

// Create a server
http.createServer( function (request, response) {
  var  b = new Buffer("Return something");
  response.write(b.toString());
  console.log('listening to client');
  response.end();
}).listen(8081);

// Console will print the message
console.log('Server running at http://127.0.0.1:8081/');
