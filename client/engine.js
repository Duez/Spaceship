
window.onload = function () {
	var solve = document.getElementById("solve");
	solve.onclick = function () {
		recursive = true;
		solveRoom(4);
		refresh();
	};
	recursive = true;
	refresh();
};

function appliData (data) {
	var values = data.split(";");
	var startIdxRoom = values.length - rooms_def.length;

	var time = document.getElementById("time");
	var seconds = parseInt(values[0])
	time.innerHTML = Math.floor(seconds/60) + ":" + ("0" + (seconds%60)).slice(-2);

	var engine = document.getElementById("engine");
	engine.innerHTML = "null" == values[startIdxRoom+4] ? "No problem" : values[startIdxRoom+4];
}
