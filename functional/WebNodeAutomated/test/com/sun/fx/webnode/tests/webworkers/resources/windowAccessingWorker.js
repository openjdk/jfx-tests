var test;
try {
    test = window.location;
} catch (error) {}
postMessage(test == null);