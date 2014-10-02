
function Admin () {
	this.rooms = {};
	this.rooms.regulation = document.getElementById("regulation");
	this.rooms.computer = document.getElementById("computer");
	this.rooms.weapons = document.getElementById("weapons");
	this.rooms.life = document.getElementById("life");
	this.rooms.engine = document.getElementById("engine");
	this.rooms.command = document.getElementById("command");
}

Admin.prototype.init = function () {
	this.refreshRooms ();
}

Admin.prototype.refreshRooms = function () {
	if (spaceship.data == undefined) {
		window.setTimeout(admin.refreshRooms, 3000);
		return;
	}

	var event = null;

	var oxygen = spaceship.data.oxygen;
	admin.rooms.life.querySelector(".specialLine .special").innerHTML = "" + oxygen + "%";


	var mins = Math.floor(spaceship.data.time / 60);
	var secs = "" + (spaceship.data.time % 60);
	if (secs.length == 1)
		secs = "0" + secs;
	admin.rooms.engine.querySelector(".specialLine .special").innerHTML = "" + mins + ":" + secs;

	for (var i=0 ; i<Object.keys(spaceship.data.rooms).length ; i++) {
		var key = Object.keys(spaceship.data.rooms)[i];
		var value = spaceship.data.rooms[key];

		var adminKey = Object.keys(admin.rooms)[i];
		var label = admin.rooms[adminKey].querySelector(".lineEvent .event");

		if (value.event == undefined) {
			if (label != null)
				label.innerHTML = "None";//value.event.name;
		} else {
			if (label != null)
				label.innerHTML = value.event.name;
		}
	}
	
	window.setTimeout(admin.refreshRooms, 3000);
}


var admin = new Admin();
admin.init();

