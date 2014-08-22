var worker = new Worker('spawned/spawnedWorker2.js');
worker.addEventListener('message', function(event) {
    postMessage(event.data);
}, false);
worker.postMessage();