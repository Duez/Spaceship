
function Admin () {
	this.rooms = {};
	this.rooms.regulation = document.getElementById("regulation");
	this.rooms.computer = document.getElementById("computer");
	this.rooms.weapons = document.getElementById("weapons");
	this.rooms.life = document.getElementById("life");
	this.rooms.engine = document.getElementById("engine");
	this.rooms.command = document.getElementById("command");

	this.params = {};
	var texts = document.querySelectorAll("#parameters input");
	this.params.proba = texts[0];
	this.params.goal = texts[1];
	this.params.oxyRate = texts[2];
}

Admin.prototype.init = function () {
	this.refreshRooms ();
	this.refreshManageTools ({});
	this.initButtons ();
}

Admin.prototype.initButtons = function () {
	var send = document.getElementById("setValues");
	send.onclick = function () {
		var options = {};
		options.oxygenRate = admin.params.oxyRate.value;
		options.baseTime = admin.params.goal.value;
		options.proba = admin.params.proba.value;
		admin.refreshManageTools (options);
	}

	document.getElementById("start").onclick = function () {
		admin.refreshManageTools ({"status":"start"});
	};

	document.getElementById("stop").onclick = function () {
		admin.refreshManageTools ({"status":"stop"});
	};

	document.getElementById("reset").onclick = function () {
		admin.refreshManageTools ({"status":"reset"});
	};

	for (var i=0 ; i<Object.keys(admin.rooms).length ; i++) {
		var key = Object.keys(admin.rooms)[i];
		var button = admin.rooms[key].querySelector("button");
		button.key = key;
		button.onclick = function () {
			spaceship.solveRoom (
				this.key,
				spaceship.data.rooms[dataTranslate[this.key]].event.start
			);
		}
	}
}

Admin.prototype.refreshManageTools = function (options) {
	spaceship.servCom.askServer("/managment", options, admin.loadManagment);
}
Admin.prototype.loadManagment = function (data) {
	var values = JSON.parse(data);
	admin.params.state = values.status;

	admin.params.proba.value = values.proba;
	admin.params.goal.value = values.baseTime;
	admin.params.oxyRate.value = values.oxygenRate;
}

Admin.prototype.refreshRooms = function () {
	//console.log("refresh");
	if (spaceship.data == undefined || admin.params.state == undefined
	|| (admin.params.state != "STARTED" && admin.params.state != "READY")) {
		window.setTimeout(admin.refreshRooms, 3000);
		return;
	}

	if (spaceship.data.rooms == undefined) {
		admin.refreshManageTools();
		window.setTimeout(admin.refreshRooms, 1000);
		return;
	}

	var event = null;

	//console.log(admin.params);
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
		var button = admin.rooms[adminKey].querySelector("button");

		if (value.event == undefined) {
			admin.rooms[adminKey].style.background = "#56A764";
			button.style.display = "none";
			if (label != null)
				label.innerHTML = "None";//value.event.name;
		} else {
			admin.rooms[adminKey].style.background = "#DA7870";
			button.style.display = "inline";
			if (label != null)
				label.innerHTML = value.event.name;
		}
	}
	
	window.setTimeout(admin.refreshRooms, 3000);
}

/*var getStats = function() {
	var names = ["Marc","Yoann","Sophie"];
	spaceship.servCom.askServer("/stats", {"id":1, "names":JSON.stringify(names)}, function(data){
		document.getElementById("pstats").innerHTML = data
	});
}*/


var admin = new Admin();
admin.init();

