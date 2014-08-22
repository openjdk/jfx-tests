var interval = setInterval(function() {postMessage('omega')}, 1000);
addEventListener('message', function(event) {
    postMessage('omega');
    self.close();
}, false);