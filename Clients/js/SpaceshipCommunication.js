
function Spaceship () {
	this.servCom = new ServerCommunication();
}


Spaceship.prototype.refresh = function () {
	spaceServ.servCom.askServer("/ship", {}, spaceServ.loadData);
	window.setTimeout(spaceServ.refresh, 1000);
}

Spaceship.prototype.loadData = function (data) {
	console.log("<- " + data);
}


var spaceServ = new Spaceship();
window.setTimeout(spaceServ.refresh, 1000);
