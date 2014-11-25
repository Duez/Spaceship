
//
var xc = 0.5
var yc = 0.481
var r1 = 0.48
var r2 = 0.44

/*
 *
 * */
function CommandCenter() {
    console.log("CommandCenter: start")
    
    this.isStarted = false;
    this.mem = {
        "oxy" : [],
        "time" : []
    };
}


CommandCenter.prototype = {
    
    init : function () {
        var self = this
        if (spaceship.data == undefined) {
            window.setTimeout(function(){self.init()}, 1000);
            return;
        }
        
        console.log("CommandCenter: init")
        //var
        this.rooms = []
        this.ship = document.getElementById("ship");
        
        //build CommandCenter
        this.init_rooms()
        this.init_oxygen()
        this.init_goal()
        
        //processing
        proc = new Processing(cc.ship, shipProc);
        proc.externals.sketch.options.isTransparent = true;
        
        //auto-resize
        var self = this //haha
        window.onresize = function () {
            self.resize()
        };
        
        //save
        for (var key in spaceship.data.rooms) {
            this.mem[key]=[]   
        }
        
        this.resize();
        this.update();
    },
    
    /*build interface
     * 
     * */
    init_rooms : function () {
        console.log("CommandCenter: init interface") 
        var container = document.getElementById("screen")
        
        for (var key in spaceship.data.rooms) {
            var type = rooms_def[key].name
            
            //create room box
            var box = document.createElement('div')
            box.className = "room"
            box.style.top
            
            //add room name div
            var name = document.createElement('div')
            name.className = "room_name"
            name.appendChild(document.createTextNode(type))
            box.appendChild(name)
            
            //add room event
            var div_event = document.createElement('div')
            div_event.className = "room_event"
            var eventi = document.createElement('span')
            eventi.className = "event_icon"
            div_event.appendChild(eventi)
            var eventn = document.createElement('span')
            eventn.className = "event_name"
            div_event.appendChild(eventn)
            box.appendChild(div_event)

            //add room box info to HTML
            container.appendChild(box)
            
            //store box dom element for later (just in case)
            this.rooms.push({
                "id" : key,
                "box" : box,
                "name" : name,
                "event_icon" : eventi,
                "event_name" : eventn
            })
        }
    },
    
    
    init_oxygen : function () {
        this.oxygen = {
            "level" : document.getElementById("oxygen_level"),
            "max" : spaceship.data.oxygen,
            "last" : spaceship.data.oxygen
        }
    },
    
    init_goal : function () {
        this.goal = {
            "value" : document.getElementById("goal"),
            "max" : spaceship.data.time,
            "last" : spaceship.data.time
        }
    },
    
    init_time : function () {
    },
    
    /*
     * 
     * */
    resize : function () {
        console.log("CommandCenter: resize")      
        this.update_room_pos();
        
        this.ship_width = this.ship.offsetWidth
        this.ship_height = this.ship.offsetHeight
    },
    
    
    /*compute room width/height/position 
     * 
     * */
    update_room_pos : function  () {
        var width = document.getElementById("screen").offsetWidth
        var height = document.getElementById("screen").offsetHeight
        
        var margin = 0.03*height
        var room_width = 0.2*width
        var room_height = this.rooms[0].box.offsetHeight
        
        var ellipse_width = width - room_width - margin
        var ellipse_height = 0.85*height - room_height -margin
        
        for (var i in this.rooms) {
            //YEAHHHH math
            var o = (Math.PI*2) * ( (0.5+parseFloat(i))/this.rooms.length ) 
            var x = Math.round( width*xc  + ( Math.sin(o) * (ellipse_width/2) ) ) 
            var y = Math.round( height*yc + ( Math.cos(o) * (ellipse_height/2) ) )
            
            //
            var r = this.rooms[i].box
            r.style.width = room_width
            r.style.top = y - (room_height/2)
            r.style.left = x - (room_width/2)
        }
    },
    
    update_goal : function () {
        if ((this.goal.last - spaceship.data.time) > 0){
            this.start()
            this.goal.value.style.color = "rgba(7,165,210,1)"
        }else{
            this.goal.value.style.color = "rgba(253,0,13,1)"
        }
        
        var t = spaceship.data.time 
        var sec = (t%60)
        if (sec<10) sec = "0"+sec
            
        this.goal.value.innerHTML = Math.floor(t/60) + ":" + sec
        this.goal.last = spaceship.data.time
    },
    
    update_oxygen : function () {
        if ((this.oxygen.last - spaceship.data.oxygen) <= 0){
            this.oxygen.level.className = "gauge_level oxygen"
        }else{
            this.oxygen.level.className = "gauge_level oxygen2"
        }
        this.oxygen.level.style.width = ((spaceship.data.oxygen/this.oxygen.max)*100)+"%"
        this.oxygen.last = spaceship.data.oxygen
    },
    
    update : function () {
        var self = this
        
        if (Object.keys(spaceship.data).length==0){
            this.end();
            return;
        }
        
        this.update_event();
        this.update_goal();
        this.update_oxygen();
        
        if (spaceship.data.oxygen == 0) {
            this.end()
        }else if (spaceship.data.time == 0) {
            this.end()
        } else {
            window.setTimeout(function(){self.update()}, 1050);
        }
    },
    
    /* 
     * 
     * */
    update_event : function() {
        for (var i in this.rooms){
            var r = this.rooms[i]
            if(typeof spaceship.data.rooms[r.id].event != 'undefined'){
                var e = spaceship.data.rooms[r.id].event.name
                if (typeof event_def[e] == 'undefined') e = "default"
                r.event_icon.style.background = event_def[e].color
                r.event_name.innerHTML = event_def[e].name
            }else{
                r.event_icon.style.background = 'rgba(0,0,0,0)'
                r.event_name.innerHTML = ""
            }
        }
    },
    
    start : function(){
        if(!this.isStarted){
            this.isStarted = true
            this.begin = new Date().getTime()
        }
    },
    
    end : function() {
        setTimeout(function(){
            window.location.href = "result.html"
        },2000)
    },
    

}//end CommandCenter prototype







function shipProc(p) {
    /* @pjs transparent="true"; */
    
    p.setup = function() {
        p.smooth();
        p.hint(p.DISABLE_OPENGL_2X_SMOOTH);
        p.hint(p.ENABLE_OPENGL_4X_SMOOTH);
        p.frameRate(25)
    }
    
    p.draw = function() {
        p.background(0,0,0,0)
        p.width = cc.ship_width
        p.height = cc.ship_height
        p.size(p.width, p.height);
        
        var i=0
        for (var key in spaceship.data.rooms) {
            var isBroken = false
            if (typeof spaceship.data.rooms[key].event != 'undefined') isBroken = true
            
            var start = -(Math.PI*2) * ( (parseFloat(i)+1)/cc.rooms.length ) + Math.PI/2
            var stop = -(Math.PI*2) * ( (parseFloat(i))/cc.rooms.length ) + Math.PI/2
            
            p.strokeWeight(2);
            p.stroke(255,255,255,100)
            p.drawSegment(start,stop,isBroken)
            i++
        }
        
        
    };
    
    p.drawSegment = function(start,stop,broken) {
        
        var m = (start+stop)/2
        var x = p.width*xc + 10*Math.cos(m) 
        var y = p.height*yc + 10*Math.sin(m)
        
        if (broken){
            p.fill(253,0,13,(80+Math.cos(p.frameCount/5)*60) )
        }else{
            p.fill(7,165,210,80)
        }
        p.arc( x, y, r1*p.width, r2*p.height, start, stop)
        
        p.line(x, y, x+Math.cos(stop)*r1*(p.width/2) , y+Math.sin(stop)*r2*(p.height/2) )
        p.line(x, y, x+Math.cos(start)*r1*(p.width/2) , y+Math.sin(start)*r2*(p.height/2) )
    }

}   





var cc = new CommandCenter()
//ship canvas and processing sketch
var proc = null

window.onload = function () {
    admin.refreshManageTools ({"status":"reset"});
    setTimeout(function(){cc.init()}, 2000);
}

document.onkeypress = function (e) {
    e = e || window.event;
    
    var key = e.keyCode;
    if (key==0) key = e.which
        
    if (key == 13){
        e.preventDefault()
        $("#start").css('display','none')
        admin.refreshManageTools ({"status":"start"});
        document.onkeypress = function () {};
    }
}




