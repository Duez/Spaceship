
function Result() {

}


Result.prototype = {
    
    get_stat : function () {
        var self = this;
        $.post( "stats", function( data ) {
            data = JSON.parse(data);
            if (window.location.search == ""){
                self.stat = data[data.length-1]
            }else{
                var id = window.location.search.replace("?id=","")
                self.stat = data[id-1]                
            }
            self.get_result()
        });
    },
    
    get_result : function () {
        var self = this;
        $.ajax({
            url: 'stats',
            type: 'GET',
            data: { id: self.stat.id },
            success: function(data) {
                data = JSON.parse(data);
                self.result = data
                self.display(data)
            }
        });
    },
    
    display : function() {
        
        console.log(this.result)
        //fail/win
        
        if (!result.stat.win) {
            $("#result").html("Game Over")
            $("#result").css("color","rgba(253,0,13,1)")
        }else{
            $("#result").html("Victory")
            $("#result").css("color","rgba(7,165,210,1)")
        }
        
        //game_time
        var ellapsed_time = ( this.stat.stop - this.stat.start ) / 1000
        var minute = Math.floor(ellapsed_time/60)
        var sec = Math.floor(ellapsed_time%60)
        if (sec<10) sec = "0"+sec
        $("#score").html( minute + ":" + sec )
        
        var canvas = document.getElementById("canvas");
        var proc = new Processing(canvas, graphProc);
        proc.externals.sketch.options.isTransparent = true;
        
        var delta = this.stat.stop - this.stat.start
        console.log(delta)
        for (var key in this.result.rooms){
            var div = jQuery('<div/>', {
                html: this.result.rooms[key].name,
                class: 'room_stat'
            })
            
            for (key2 in this.result.rooms[key].events){
                var event = this.result.rooms[key].events[key2]
                var start = (((event.start - this.stat.start)/delta).toFixed(3))*100
                var stop = 100
                if (typeof event.stop != "undefined") stop = (((event.stop - this.stat.start)/delta).toFixed(3))*100
                jQuery('<div/>', {
                    style: 'width : '+(stop-start)+"%"+'; left : '+start+"%"+'; background-color : '+event_def[event.name].color,
                    class: 'event_bar'
                })
                .appendTo(div)
            }
            div.appendTo("#stats")
        }
    }
}


function graphProc(p) {
    /* @pjs transparent="true"; */
    
    p.setup = function() {
        p.smooth();
        p.hint(p.DISABLE_OPENGL_2X_SMOOTH);
        p.hint(p.ENABLE_OPENGL_4X_SMOOTH);
        p.frameRate(1)
    }
    
    p.draw = function() {
        p.background(0,0,0,255)
        p.width = document.getElementById("graph").offsetWidth
        p.height = document.getElementById("graph").offsetHeight
        p.size(p.width, p.height);
        
        var delta = result.stat.stop - result.stat.start
        
        //oxygen
        p.noStroke()
        p.fill(7,165,210,255)
        p.beginShape();
        p.vertex(0,0);
        var oxygen = result.result.oxygen
        for (var key in oxygen){
            var x = ((oxygen[key].timestamp - result.stat.start)/delta)
            var y = oxygen[key].value/100
            p.vertex(x*p.width, (1-y)*p.height)
        }
        p.vertex(p.width,p.height);
        p.vertex(0,p.height);
        p.endShape(p.CLOSE);
        
        
        //travel
        p.stroke(255,69,0,255)
        p.strokeWeight(5)
        p.noFill()
        p.beginShape();
        p.vertex(0,p.height);
        var travel = result.result.travel
        for (var key in travel){
            var x = ((travel[key].timestamp - result.stat.start)/delta)
            var y = (travel[key].value/travel[0].value)
            p.vertex(x*p.width, y*p.height)
        }
        p.endShape();
        
    };
}







var result = new Result()

result.get_stat()


function checkKey (e) {    
    e = e || window.event;
    e.preventDefault()
    
    var key = e.keyCode;
    if (key==0) key = e.which
 
    if (key == 13){
        admin.refreshManageTools ({"status":"reset"});
        document.location.href = "CommandCenter.html"
    }
    if (key == 32){
        admin.refreshManageTools ({"status":"reset"});
        document.location.href = "leaderboard.html"
    }
}

document.onkeydown = function (e) { checkKey(e);}
    