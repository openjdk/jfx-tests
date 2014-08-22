var timeout1 = setTimeout(function() {postMessage("alpha");}, 1000);
var timeout2 = setTimeout(function() {postMessage("omega");}, 5000);
clearTimeout(timeout2);