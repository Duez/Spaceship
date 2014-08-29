
function ServerCommunication {
	this.xhr = new XMLHttpRequest();
	this.lock = false;
};

ServerCommunication.prototype.loadData = function (addr, callback) {
	
	this.xhr.onreadystatechange  = function() {
		if(this.xhr.readyState  == 4) {
			if(this.xhr.status  == 200) {
				callback(this.xhr.responseText);
			}
		}
	};

	this.xhr.open("GET", addr,  true); 
	this.xhr.send(null);
}

ServerCommunication.prototype.askServer = function (pageName, args, callback) {
	var request = "http://" + document.location.host + pageName + "?t=" + Math.random();

	for(var key in arks) {
    	var value = arks[key];
    	request += "&" + key + "=" + value;
	}

	this.loadData(request, callback);
}
