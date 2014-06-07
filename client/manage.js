
var recursive = false;
var proba = 0.0000000001;

function appliData (data) {
	var values = data.split(";");
	var startIdxRoom = values.length - rooms_def.length;

	var timer = document.getElementById("timer");
	var seconds = parseInt(values[0]);
	timer.innerHTML = Math.floor(seconds/60) + ":" + ("0" + (seconds%60)).slice(-2);

	var oxygen = document.getElementById("oxygen");
	oxygen.innerHTML = parseInt(values[1]) + "%";

	var currentProba = parseFloat(values[2]);
	if ((currentProba != proba)) {
		proba = currentProba;
		var value = document.getElementById("proba");
		value.setAttribute("value", proba);
	}

	for (var i=startIdxRoom+1 ; i<rooms_def.length+startIdxRoom ; i++) {
		var room = document.getElementById("event" + (i-startIdxRoom));
		var txt;

		if (values[i] == "null")
			txt = "OK";
		else
			txt = values[i];
		room.innerHTML = txt;
	}

	if (seconds == 0)
		recursive = false;
}

function startServer () {
	var addr = "http://" + document.location.host + "/game/start/?t=" + Math.random();
	loadData (nothing, addr);
	recursive = true;
	refresh();
}

function resetServer () {
	var addr = "http://" + document.location.host + "/game/reset/?t=" + Math.random();
	loadData (nothing, addr);
	recursive = false;
	refresh();
}

function setProba (proba) {
	var addr = "http://" + document.location.host + "/game/proba/?" + proba + ",t=" + Math.random();
	loadData (nothing, addr);
	refresh();
}

window.onload = function () {
	var startButton = document.getElementById("startButton");
	startButton.onclick = function () {startServer();};
	var startButton = document.getElementById("resetButton");
	startButton.onclick = function () {resetServer();};
	var probaButton = document.getElementById("probaButton");
	probaButton.onclick = function () {
		var currentProba = parseFloat(document.getElementById("proba").value);
		if ((currentProba > 0.0) && (currentProba <= 1.0))
			setProba (currentProba);
	}

	for (var i=1 ; i<rooms_def.length ; i++) {
		var room = document.getElementById("room" + i);
		room.innerHTML = rooms_def[i] + " : ";
	}

	refresh();
};
