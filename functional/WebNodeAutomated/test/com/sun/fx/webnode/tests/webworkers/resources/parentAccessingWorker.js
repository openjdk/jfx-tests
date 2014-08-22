var test;
try {
    test = parent;
} catch (error) {}
postMessage(test == null);