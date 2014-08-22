addEventListener('message', function(event) {
    postMessage(event.data + 'omega');
}, false);