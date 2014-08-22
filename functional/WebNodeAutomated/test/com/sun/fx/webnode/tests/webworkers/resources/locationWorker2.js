var location_value = location.href;
try {
    location.href = "http://test.me";
} catch (err) {}
postMessage(location_value == location);
