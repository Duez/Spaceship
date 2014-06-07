
function redirect () {
	var value = document.getElementById("room").value;
	var page = "http://" + document.location.host;
	switch (value) {
		case "1":
			page += "/life.html";
		break;
		case "2":
			page += "/defense.html";
		break;
		case "3":
			page += "/power.html";
		break;
		case "4":
			page += "/engine.html";
		break;
		case "5":
			page += "/weapon.html";
		break;
	}

	document.location.href = page;
}

window.onload = function () {
	var roomButton = document.getElementById ("roomButton");
	roomButton.onclick = redirect;
}
