
var rooms_def = ["Command room", "Life support", "Defense room", "Power supply", "Engine room", "Weapon room"];
var events_def = ["Asteroid", "Bomb", "Alien", "Fire", "Hack"];

function refresh () {
	var addr = "http://" + document.location.host + "/game/infos/?t=" + Math.random();
	loadData (appliData, addr);
	if (recursive)
		window.setTimeout(refresh, 1000);
}

function solveRoom (numRoom) {
	var addr = "http://" + document.location.host + "/game/solve/" + numRoom + "/?t=" + Math.random();
	loadData (nothing, addr);
}
