
window.onload = function () {
	var solve = document.getElementById("solve");
	solve.onclick = function () {
		recursive = true;
		solveRoom(1);
		refresh();
	};
	recursive = true;
	refresh();
};

function appliData (data) {
	var values = data.split(";");
	var startIdxRoom = values.length - rooms_def.length;

	var oxygen = document.getElementById("oxygen");
	oxygen.innerHTML = parseInt(values[1]) + "%";

	var life = document.getElementById("life");
	life.innerHTML = "null" == values[startIdxRoom+1] ? "No problem" : values[startIdxRoom+1];
}
