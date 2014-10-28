

function release_keyboard(){
    document.onkeydown = function (e) { }
    document.onkeypress = function (e) { }
    document.onkeyup = function (e) { }
}

function Room() {
    this.lock_div = document.getElementById("lock")
    this.input=""  
    this.init()
    this.lock();  
    this.event_solved=[]
    this.print();
}



Room.prototype = {
    
    init : function () {
        var self = this;
        
        if (spaceship.data == undefined) {
            window.setTimeout(function(){self.init()}, 1000);
            return;
        }
        
        for (var key in spaceship.data.rooms){
            this.div = jQuery('<div/>', {
                html: key,
                click: (function(){self.defineRoom($(this))}),
                class: 'button'
            })
            .appendTo("#menu")
        }
    },
    
    defineRoom : function(room){
        console.log("click")
        this.roomID = room.html()
        $("#menu").fadeOut(500) 
        
        $("#door_label").html(rooms_def[this.roomID].name)
        $("#room_name").html(rooms_def[this.roomID].name)
        
    },
    
    //ouvre la porte
    open : function() {
        var self=this;

        $('#red_left').fadeOut(1000);
        $('#red_right').fadeOut(1000);
        setTimeout(function(){
            $('#door_right').animate({ left: "90%"}, 500);
            $('#door_left').animate({ left: "-35%"}, 500);
            self.checkEvent()
        },800);
    },
    
    //ferme la porte
    close : function() {
        $('#red_left').fadeIn(1000);
        $('#red_right').fadeIn(1000);
        setTimeout(function(){
            $('#door_right').animate({ left: "42%"}, 500);
            $('#door_left').animate({ left: "0%"}, 500);
        },800);
        
        this.lock()
    },
    
    //
    checkEvent : function () {
        var self=this;
        
        //check if event exist
        if (typeof spaceship.data.rooms[this.roomID].event == "undefined" ||
            this.event_solved.indexOf(spaceship.data.rooms[this.roomID].event.start) != -1 
        ){
            $("#room_event").html("room status : OK")
            setTimeout(function(){
                self.close()
            },1500);
            
            return
        }
        
        this.eventID = spaceship.data.rooms[this.roomID].event.name
        this.eventStart = spaceship.data.rooms[this.roomID].event.start
        
        if (typeof event_def[this.eventID].player != "undefined") {
            if (event_def[spaceship.data.rooms[this.roomID].event.name].player != this.player){
                $("#room_event").html(event_def[spaceship.data.rooms[this.roomID].event.name].player + " needed to solve " + event_def[this.eventID].name )
                setTimeout(function(){
                    self.close()
                },1500);
                
                return
            }
        }
        
        $("#room_event").html( event_def[this.eventID].name )
        var callback = function() { self.solve() }//double closure ?
        this.g = new MiniGames("game",3, callback)        
    },
    
    solve :function () {
        this.event_solved.push(this.eventStart)
        $("#room_event").html("YEAHHHHH!!!")
        $("#game").html("")
        spaceship.solveRoom (rooms_def[this.roomID].short);
        this.close()
        
    },
    
    //rend le controle au verrou
    lock : function() {
        var self=this
        //take control of input
        document.onkeydown = function (e) { self.checkKey(e);}
    },
    
    //input event handler
    checkKey : function(e) {
        e = e || window.event;
        e.preventDefault()
        
        if (e.keyCode == 8){
            this.input = this.input.substring(0, this.input.length-1)
        }else if (e.keyCode == 13){
            release_keyboard()
            this.unlock();
            return
        }else{
            var char = String.fromCharCode(e.keyCode)
            this.input += char
        }
        this.print()
    },
    
    //ecran du verrou
    print : function (text){
        this.lock_div.innerHTML = this.input + "<span class='bar'>_</span>"
    },
    
    //tentative d'ouverture
    unlock : function () {
        var self = this;
        
        if (typeof password[this.input.toLowerCase()] != 'undefined' ){
            this.player = password[this.input.toLowerCase()]
            this.print()
            release_keyboard()
            this.open()
        }else{
            console.log("plop")
            this.lock_div.innerHTML = "wrong password"
            setTimeout(function(){
                self.input = ""
                self.print();
                self.lock();
            },800);
        }
        
        this.input = ""
    }
    
}





/* s'occupe de lancer les minigames a la suite 
 * id => la fenetre dans laquelle les jeux s'affiche
 * n  => le nombre de jeux a résoudre
 */
function MiniGames (id, n, callback) {
	this.n=n;
	this.id=id;
    this.callback = callback;
	this.gameList = [Game1,Game2]
	
	this.nextGame();
}

MiniGames.prototype = {

	nextGame : function () {
		var self = this;
		this.n--;
		
		//custom callback for the last game
		var callback = function() { self.nextGame() }
		if (this.n <= 0) callback = function() { self.end() }
		
		//choose random minigame
		var g = this.gameList[ Math.floor(Math.random()*this.gameList.length) ]
		this.m = new g(this.id, callback)
	},
	
	end : function () {
        this.callback()
	}
}





//TODO factorize
function Game1 (id, callback) {
	this.id = id;
	this.callback = callback;
    this.init();    
}

Game1.prototype = {
 
    init : function () {
        var self = this;	//javascript magic
        this.goal = 20;		//nombre de hit a effectuer
		this.count = 0;
		this.up = true;		//check si la touche n'est pas en hold
        this.key = 97 + Math.floor(Math.random()*26) //choose random letter
		
        console.log("press " + this.key + "  " + String.fromCharCode(this.key))
		this.display();
		
		//take control of input
        document.onkeypress = function (e) { self.checkKey(e);}
		document.onkeyup = function () { self.up = true }
    },
	
	display : function () {
		this.div = jQuery('<div/>', {
			html: "hit <span class='key blink1'>" + String.fromCharCode(this.key) + "</span>  ",
			style: 'display : none',
			class: 'minigame'
		})
		.appendTo("#"+this.id)
		.slideDown(200);
		
		this.gauge = jQuery('<div/>', {class: 'gauge'})
		.appendTo(this.div)
		
		this.level = jQuery('<div/>', {class: 'gauge_level'})
		.appendTo(this.gauge)
		
	},
    
    checkKey : function(e) {	
        e = e || window.event;
        e.preventDefault()
		
        if(this.key == e.keyCode.toString() & this.up){
            this.count++;
			this.level.css("width", "" + Math.floor( (this.count/this.goal)*100 ) + "%");
			this.up = false;
        }
		if (this.count>=this.goal) this.end()
    },
	
	end : function () {
		this.div.addClass("inactive")
		this.callback()
	}
    
}



function Game2 (id, callback) {
	this.id =id;
	this.callback = callback;
    this.init();    
}

Game2.prototype = {
 
    init : function () {
        var self = this;	//javascript magic
        this.goal = 60;	//nombre de hit a effectuer
		this.count = 0;
        this.key = 97 + Math.floor(Math.random()*26) //choose random letter
		
        console.log("hold " + this.key + "  " + String.fromCharCode(this.key))
		this.display();
		
		//take control of input
        document.onkeypress = function (e) { self.checkKey(e);}
    },
	
	display : function () {
		this.div = jQuery('<div/>', {
			html: "hold <span class='key'>" + String.fromCharCode(this.key) + "</span>  ",
			style: 'display : none',
			class: 'minigame'
		})
		.appendTo("#"+this.id)
		.slideDown(200);
		
		this.gauge = jQuery('<div/>', {class: 'gauge'})
		.appendTo(this.div)
		
		this.level = jQuery('<div/>', {class: 'gauge_level'})
		.appendTo(this.gauge)
	},
    
    checkKey : function(e) {	
        e = e || window.event;
        e.preventDefault()
        
        if(this.key == e.keyCode.toString()){
            this.count++;
			this.level.css("width", "" + Math.floor( (this.count/this.goal)*100 ) + "%");
        }
		if (this.count>=this.goal) this.end()
    },
	
	end : function () {
		this.div.addClass("inactive")
		this.callback()
	}
    
}


//var game = new MiniGames("game",2)
var room = new Room();