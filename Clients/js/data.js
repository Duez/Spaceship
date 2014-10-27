event_def = {
	"events.Fire" : 
	{
        "name" : "fire !!!",
		"icon" : null,
		"effect" : "blink",
        "player" : "firefighter",
		"color" : "red"
	},
	"events.Aliens" : 
	{   "name" : "aliens !!!",
		"icon" : null,
		"effect" : "blink",
        "player" : "marine",
		"color" : "green"
	},
	"events.Hack" : 
	{
        "name" : "hack",
		"icon" : null,
		"effect" : "blink",
        "player" : "engineer",
		"color" : "blue"
	},
	"events.NoSignal" : 
	{
        "name" : "no signal",
		"icon" : null,
		"effect" : "blink",
		"color" : "purple"
	},
	"events.Meteor" : 
	{
        "name" : "meteor",
		"icon" : null,
		"effect" : "blink",
		"color" : "brown"
	}, 
    "events.ElectricFailure" : 
    {
        "name" : "electric failure",
        "icon" : null,
        "effect" : "blink",
        "color" : "brown"
    }, 
	"default" : 
	{
        "name" : "unknow event",
		"icon" : null,
		"effect" : "blink",
		"color" : "grey"
	}
}
     
//lower case only
password = {
    "mario" : "firefighter",
    "luigi" : "marine",
    "peach" : "engineer"
}

rooms_def = {
	"rooms.CommandCenter" :
	{
        "name" : "command center",
        "short" : "command",
		"color" : "rgba(33,59,89,1)",
		"broken" : "never!!!!!"
	},
    "rooms.RegulationRoom" :
    {
        "name" : "regulation room",
        "short" : "regulation",
        "color" : "rgba(136,44,44,1)",
        "broken" : "fire defense disabled"
    },
    "rooms.ComputerCenter" :
    {
        "name" : "computer center",
        "short" : "computer",
        "color" : "rgba(89,124,40,1)",
        "broken" : "hack defense disabled"
    },
    "rooms.WeaponsRoom" :
    {
        "name" : "weapons room",
        "short" : "weapons",
        "color" : "rgba(85,29,89,1)",
        "broken" : "aliens defense disabled"
    },
    "rooms.LifeSupport" :
    {
        "name" : "life support",
        "short" : "life",
        "color" : "rgba(0,200,200,1)",
        "broken" : "oxygen production disabled"
    },    
    "rooms.EngineRoom" :
    {
        "name" : "engine room",
        "short" : "engine",
        "color" : "rgba(136,134,60,1)",
        "broken" : "spaceship immobilized"
    },    
	"default" : 
	{
        "name" : "unknow room", 
        "short" : "",
        "color" : "rgba(100,100,100,1)",
        "broken" : "something bad happens here! maybe..."
	}
}