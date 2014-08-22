var connections = 0;

onconnect = function(event) {
    connections += 1;
    var connection = connections;
    var port = event.ports[0];
    port.postMessage('Connected');
    port.onmessage = function(e) {
        port.postMessage('Connection: ' + connection);
    }
}