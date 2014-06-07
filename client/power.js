
window.onload = function () {
	var solve = document.getElementById("solve");
	solve.onclick = function () {
		recursive = true;
		solveRoom(3);
		refresh();
	};
	recursive = true;
	refresh();
};

function appliData (data) {
	var values = data.split(";");
	var startIdxRoom = values.length - rooms_def.length;

	var room = document.getElementById("power");
	room.innerHTML = "null" == values[startIdxRoom+3] ? "No problem" : values[startIdxRoom+3];
}
