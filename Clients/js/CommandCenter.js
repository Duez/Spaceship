
/*
 *
 * */
function CommandCenter() {
    console.log("CommandCenter: start")
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
        
        this.resize();
        this.update();
    },
    
    /*build interface
     * 
     * */
    init_rooms : function () {
        console.log("CommandCenter: init interface") 
        var container = document.getElementById("main-container")
        
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
            "box" : document.getElementById("oxygen-container"),
            "status" : document.getElementById("oxygen_status"),
            "level" : document.getElementById("oxygen_level"),
            "value" : document.getElementById("oxygen_value"),
            "max" : spaceship.data.oxygen,
            "last" : spaceship.data.oxygen
        }
    },
    
    init_goal : function () {
        this.goal = {
            "box" : document.getElementById("goal-container"),
            "status" : document.getElementById("goal_status"),
            "level" : document.getElementById("goal_level"),
            "value" : document.getElementById("goal_value"),
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
        var width = document.getElementById("main-container").offsetWidth
        var height = document.getElementById("main-container").offsetHeight
        
        var margin = 0.03*height
        var room_width = 0.2*width
        var room_height = this.rooms[0].box.offsetHeight
        
        var ellipse_width = width - room_width - margin
        var ellipse_height = height - room_height -margin
        
        for (var i in this.rooms) {
            //YEAHHHH math
            var o = (Math.PI*2) * ( (0.5+parseFloat(i))/this.rooms.length ) 
            var x = Math.round( (width/2)  + ( Math.sin(o) * (ellipse_width/2) ) ) 
            var y = Math.round( (height/2) + ( Math.cos(o) * (ellipse_height/2) ) )
            
            //
            var r = this.rooms[i].box
            r.style.width = room_width
            r.style.top = y - (room_height/2)
            r.style.left = x - (room_width/2)
        }
    },
    
    update_goal : function () {
        if ((this.goal.last - spaceship.data.time) > 0){
            this.goal.status.style.color = "rgba(7,165,210,1)"
            this.goal.status.innerHTML = "ship is moving"
        }else{
            this.goal.status.style.color = "rgba(253,0,13,1)"
            this.goal.status.innerHTML = "ship as stopped"
        }
        
        var t = spaceship.data.time 
        var sec = (t%60)
        if (sec<10) sec = "0"+sec
            
        this.goal.value.innerHTML = Math.floor(t/60) + ":" + sec
        this.goal.level.style.width = (100-(t/this.goal.max)*100)+"%"
        this.goal.last = spaceship.data.time
    },
    
    update_oxygen : function () {
        if ((this.oxygen.last - spaceship.data.oxygen) <= 0){
            this.oxygen.status.style.color = "rgba(7,165,210,1)"
            this.oxygen.status.innerHTML = "ok"
        }else{
            this.oxygen.status.style.color = "rgba(253,0,13,1)"
            this.oxygen.status.innerHTML = "leak detected"
        }
        
        this.oxygen.value.innerHTML = ((spaceship.data.oxygen/this.oxygen.max)*100).toFixed(1)+"%"
        this.oxygen.level.style.width = ((spaceship.data.oxygen/this.oxygen.max)*100)+"%"
        this.oxygen.last = spaceship.data.oxygen
    },
    
    update : function () {
        var self = this
        this.update_event();
        this.update_goal();
        this.update_oxygen();
        window.setTimeout(function(){self.update()}, 1000);
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
        var x = (p.width/2) + 10*Math.cos(m) 
        var y = (p.height/2) + 10*Math.sin(m)
        
        if (broken){
            p.fill(253,0,13,(80+Math.cos(p.frameCount/5)*60) )
        }else{
            p.fill(7,165,210,80)
        }
        p.arc( x, y, 0.8*p.width, 0.8*p.height, start, stop)
        
        p.line(x, y, x+Math.cos(stop)*0.8*(p.width/2) , y+Math.sin(stop)*0.8*(p.height/2) )
        p.line(x, y, x+Math.cos(start)*0.8*(p.width/2) , y+Math.sin(start)*0.8*(p.height/2) )
    }

}   





var cc = new CommandCenter()
//ship canvas and processing sketch
var proc = null
window.onload = function () {
    cc.init()
}




