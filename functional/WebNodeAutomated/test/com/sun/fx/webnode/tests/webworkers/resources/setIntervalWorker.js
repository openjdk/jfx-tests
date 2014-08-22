var interval = setInterval(function() {postMessage("alpha");}, 2000);
var timeout = setTimeout(function() {clearInterval(interval);}, 3000);