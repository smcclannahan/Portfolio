export var item1Shown = true;

export var submitThis = function(searched){
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
	
	var filter = searched.toLowerCase();
	//Change this depending on what item you want to test
	var itemToTest = 0;
	if(products[itemToTest].type != filter && products[itemToTest].name != filter && products[itemToTest].price != filter){
		item1Shown = false;
	}
}