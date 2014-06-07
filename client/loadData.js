
xhr = new XMLHttpRequest();

var lock = false;

function loadData(func, addr) {
	if (lock) {
		var f = function () {loadData(func, addr);};
		window.setTimeout(f, 100);
	} else {
		lock = true;
		xhr.onreadystatechange  = function() {
			if(xhr.readyState  == 4) {
				if(xhr.status  == 200) {
					func(xhr.responseText);
					lock = false;
				}
			}
		};

		xhr.open("GET", addr,  true); 
		xhr.send(null);
	}
}

function nothing (bla) {}
