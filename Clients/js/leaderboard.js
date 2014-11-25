
function init() {
    $.post( "stats", function( data ) {
        build(data)
    });
    
    document.onkeypress = function (e) { checkKey(e);}
}

function build (data) {
    var data = JSON.parse(data);
    
    //calcul la durée de chaque game
    for (var i=0; i<data.length; i++){
        console.log(i)
        var delta = Math.floor( (data[i].stop - data[i].start) / 1000 ) 
        data[i].delta = delta
        
        var minute = Math.floor(delta/60)
        var sec = Math.floor(delta%60)
        if (sec<10) sec = "0"+sec
        data[i].time =  minute + ":" + sec 
    }

    //trie les parties 
    data.sort(function (a, b) {
        if (a.win && !b.win) return -1;
        if (!a.win && b.win) return 1;
        if (a.win && (a.delta > b.delta) ) return 1;
        if (a.win && (a.delta < b.delta) ) return -1;
        if (!a.win && (a.delta > b.delta) ) return -1;
        if (!a.win && (a.delta < b.delta) ) return 1;
        return 0;
    });
    
    var div_parent = $("#lb_table");
    
    for (var i=0; i<data.length; i++){
        
        var team = "?"
        if (typeof data[i].names != "undefined"){ 
            team = ""
            for (var j=0; j<data[i].names.length; j++){
                team += " " + data[i].names[j]
            }
        }
        var status;
        if (data[i].win) status = "win"
        else status = "fail"
        
        var div = jQuery('<div/>', {
            html: "<div class='lb_rank'>" + i +
            "</div><div class='lb_team'>" + team +
            "</div><div class='lb_status'>" + status +
            "</div><div class='lb_time'>" + data[i].time + "</div>",
            class: 'lb_row',
            id : data[i].id
        })
        .click(function(){
            document.location.href = "result.html?id="+this.id
        })
        .appendTo(div_parent);
    }
}


function checkKey (e) {    
    e = e || window.event;
    e.preventDefault()
    
    var key = e.keyCode;
    if (key==0) key = e.which
 
    if (key == 13){
        admin.refreshManageTools ({"status":"reset"});
        document.location.href = "CommandCenter.html"
    }
    
}