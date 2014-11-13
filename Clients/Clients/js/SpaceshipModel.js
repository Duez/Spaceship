
function Spaceship () {
	this.servCom = new ServerCommunication();
}


Spaceship.prototype.refresh = function () {
	spaceship.servCom.askServer("/ship", {}, spaceship.loadData);
	window.setTimeout(spaceship.refresh, 1000);
}

Spaceship.prototype.loadData = function (data) {
	spaceship.data = JSON.parse(data);
}

Spaceship.prototype.solveRoom = function (room, time) {
	this.servCom.askServer("/solve", {"room":room,
									  "time":time}, function(){})
}


var spaceship = new Spaceship();
window.setTimeout(spaceship.refresh, 1000);
