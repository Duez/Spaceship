
var ball = []


var pos1 = [0, 0, 0]
var pos2 = [500, 300, 300]
var dim = 2
var img = 0

//ajoute un point a la position donnée
function add(pos, m){
	var r = {
		'pos': pos,
		'm': m
		}
	ball.push(r)
}

//d : nombre de dimension
//n : nombre de point de départ
function init(n){
	for (var i=0; i<n; i++){
        var pos = []
        for (var d=0; d<dim; d++){
            pos.push(Math.random()*pos2[d])
        }
		add(pos, 1)
	}
}


function startQuad(){
	
	var tab = []

	for (var i=0; i<ball.length; i++){
		tab.push(i)
	}
	
	var start = [0,0]
	return quad(tab, pos1, pos2)
}

function quad(t, start, end){
    
    var center = []
    var size = []
     
    for (var i=0; i<start.length; i++){
        center[i] = (start[i]+end[i]) / 2
        size[i] = end[i]-start[i]
    }
    
	var tree = {
			'branch': [],
			'size': size,
			'center': center,
		}
	
	if (t.length==1) {
		tree.pos = ball[t[0]].pos
		tree.m = ball[t[0]].m
	}else{
	
        var split = {} 
        
        for (var i=0; i<t.length; i++){
            
            var p = ball[t[i]].pos
            var s = []
            var st = []
            var ed = []
            
            for (var d=0; d<dim; d++){
                if (p[d] > center[d]){
                    s.push(0)
                    st[d]=center[d]
                    ed[d]=end[d]
                }else{
                    s.push(1)
                    st[d]=start[d]
                    ed[d]=center[d]
                }
            }
            
            var id = s.toString()
            
            if (id in split){
                split[id].list.push(t[i])
            }else{
                split[id] = 
                { 
                    "list" : [t[i]],
                    "start" : st,
                    "end" : ed
                }
            }
        }
        
        for (var id in split){
            //console.log(split[id])
            tree.branch.push( quad(split[id].list, split[id].start, split[id].end) )
        }
    }
	return tree
}


function drawQuad(t,p) {

	var t_length = t.branch.length
	
	p.line(t.center[0]-(t.size[0]/2), t.center[1], t.center[0]+(t.size[0]/2), t.center[1]);
    p.line(t.center[0], t.center[1]-(t.size[1]/2), t.center[0], t.center[1]+(t.size[1]/2));

	for (var i=0; i<t.branch.length; i++){ 
		drawQuad(t.branch[i], p)
	}
}

function sketchProc(p) {

	p.setup = function() {
		p.size( pos2[0] - pos1[0], pos2[1] - pos1[1] +50 );
		p.strokeWeight(1);
		p.stroke(50, 50, 50, 255)
		p.fill(10,10,100,255)
        
        p.frameRate(30)
	}
	
	p.draw = function() {
		
		p.background(40,40,40)
		
		quadTree = startQuad();
		
		//drawball
		p.stroke(50, 50, 250, 255)
		for (var i=0;  i<ball.length; i++){
			p.ellipse( ball[i].pos[0], ball[i].pos[1], 2, 2 );  
		}
		
		p.stroke(200, 50, 50, 255)
		//drawQuad(quadTree, p)
        
        img++
        p.stroke(250, 250, 250, 255)
        p.text(img, 15, 330); 
		
	};
  
}

var canvas = document.getElementById("canvas");
var proc = new Processing(canvas, sketchProc);


