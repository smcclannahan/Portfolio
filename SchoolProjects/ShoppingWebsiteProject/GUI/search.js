//Put the list inside this function for simplicity.
//This function requires that the items in the products
//list are in exactly the same order as the gui cells
var searched = 0; 

var submitThis = function(){
	getChange(3); /*un-does any filters the client may have currently applied*/
	var products = [
	{"name":"apple",
	"type":"fruit",
	"price":2},
	
	{"name":"banana",
	"type":"fruit",
	"price":1},
	
	{"name":"beef",
	"type":"meat",
	"price":5},
	
	{"name":"celery",
	"type": "vegetable",
	"price":2},
	
	{"name":"cheese",
	"type":"dairy",
	"price":3},
	
	{"name":"chicken",
	"type":"meat",
	"price":4},

	{"name":"chips",
	"type":"other",
	"price":3},
	
	{"name":"coffee",
	"type":"drink",
	"price":3},
	
	{"name":"milk",
	"type":"dairy",
	"price":4},
	
	{"name":"pork",
	"type":"meat",
	"price":4},
		
	{"name":"potato",
	"type":"vegetable",
	"price":2},
		
	{"name":"soda",
	"type":"drink",
	"price":2}
	
];
	
	var item = document.getElementById("searchtext");
	var filter = item.value.toLowerCase();
	var count = 0;
	for(var i = 0; i < products.length; i++){
		//Hides all products that don't have the same type as the one being searched for
		if(products[i].type != filter && products[i].name != filter && products[i].price != filter){
			document.getElementById("item" + (i+1).toString()).style.display = "none";
		}
		else{
			document.getElementById("item" + (i+1).toString()).style.display = "block";
			count++;
		}
	}
	if(count === 0){
		searched = 0;
		alert("No results found");
		home();
	}
	else{
		searched = 1;
	} 
	
	document.getElementById("htl").style.display = "none";
	document.getElementById("lth").style.display = "none";
	document.getElementById("fruit").style.display = "none";
	document.getElementById("meat").style.display = "none";
	document.getElementById("dairy").style.display = "none";
	document.getElementById("vegetable").style.display = "none";
	document.getElementById("drink").style.display = "none";
	document.getElementById("other").style.display = "none";
	//Must return false or won't work properly.
	return false;
}

function home(){
	var products = [
	{"name":"apple",
	"type":"fruit",
	"price":2},
	
	{"name":"banana",
	"type":"fruit",
	"price":1},
	
	{"name":"beef",
	"type":"meat",
	"price":5},
	
	{"name":"celery",
	"type": "vegetable",
	"price":2},
	
	{"name":"cheese",
	"type":"dairy",
	"price":3},
	
	{"name":"chicken",
	"type":"meat",
	"price":4},

	{"name":"chips",
	"type":"other",
	"price":3},
	
	{"name":"coffee",
	"type":"drink",
	"price":3},
	
	{"name":"milk",
	"type":"dairy",
	"price":4},
	
	{"name":"pork",
	"type":"meat",
	"price":4},
		
	{"name":"potato",
	"type":"vegetable",
	"price":2},
		
	{"name":"soda",
	"type":"drink",
	"price":2}
	
];
	searched = 0;
	getChange(3); /*un-does any filters the client may have currently applied*/
	document.getElementById("htl").style.display = "block";
	document.getElementById("lth").style.display = "block";
	document.getElementById("fruit").style.display = "block";
	document.getElementById("meat").style.display = "block";
	document.getElementById("dairy").style.display = "block";
	document.getElementById("vegetable").style.display = "block";
	document.getElementById("drink").style.display = "block";
	document.getElementById("other").style.display = "block";

	for(var i = 0; i < products.length; i++){
		document.getElementById("item" + (i+1).toString()).style.display = "block";
	}
}

function getSearch(){
	return searched;
}
