addEventListener('message', function(event) {
    var test = event.data;
    event.data = event.data + 'omega';
    postMessage(test);
}, false);