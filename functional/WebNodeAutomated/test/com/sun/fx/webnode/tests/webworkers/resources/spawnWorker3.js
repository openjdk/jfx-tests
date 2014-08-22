if (Worker != null)
    postMessage("Success");
try {
    var worker = new Worker('http://127.0.0.1:13412/spawnedWorker1.js');
    worker.addEventListener('message', function(event) {
        postMessage(event.data);
    }, false);
    worker.postMessage();
} catch (error) {}