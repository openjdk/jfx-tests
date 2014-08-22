var connections = 0;

onconnect = function(event) {
    var port = event.ports[0];
    connections += 1;
    var interval = setInterval(function() {port.postMessage('Connections: ' + connections)}, 1000);
}