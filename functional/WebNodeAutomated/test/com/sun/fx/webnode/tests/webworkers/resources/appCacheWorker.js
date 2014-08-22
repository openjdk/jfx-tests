var test;
try {
    test = self.applicationCache;
} catch (error) {}
postMessage(test != null);