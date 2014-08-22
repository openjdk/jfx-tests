var test;
try {
    test = document.getElementById('content').innerHTML;
} catch (error) {}
postMessage(test == null);