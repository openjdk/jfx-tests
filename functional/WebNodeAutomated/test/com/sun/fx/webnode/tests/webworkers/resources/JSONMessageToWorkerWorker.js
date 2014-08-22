addEventListener('message', function(event) {
    var data = event.data;
    switch (data.option) {
        case '1':
            self.postMessage('alpha' + data.option);
            break;
        case '2':
            self.postMessage('omega' + data.option);
            break;
        default:
            self.postMessage('unknown');
  };
}, false);