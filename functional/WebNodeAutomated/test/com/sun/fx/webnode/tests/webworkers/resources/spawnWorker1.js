var worker = new Worker('spawnedWorker1.js');
worker.addEventListener('message', function(event) {
    postMessage(event.data);
}, false);
worker.postMessage();