//array of products with a name, type, and price.
var products = [
	{"name":"Apple",
	"type":"fruit",
	"price":2},
	
	{"name":"Banana",
	"type":"fruit",
	"price":1},
	
	{"name":"Beef",
	"type":"meat",
	"price":5},
	
	{"name":"Celery",
	"type": "vegetable",
	"price":2},
	
	{"name":"Cheese",
	"type":"dairy",
	"price":3},
	
	{"name":"Chicken",
	"type":"meat",
	"price":4},

	{"name":"Chips",
	"type":"other",
	"price":3},
	
	{"name":"Coffee",
	"type":"drink",
	"price":3},
	
	{"name":"Milk",
	"type":"dairy",
	"price":4},
	
	{"name":"Pork",
	"type":"meat",
	"price":4},
		
	{"name":"Potato",
	"type":"vegetable",
	"price":2},
		
	{"name":"Soda",
	"type":"drink",
	"price":2}
	
];
var cart = [];

var totalCost = 0;
//Formats the given int into a string in the USD format, "$x.00"
function moneyFormat(x){
	var money = (x).toLocaleString('en-US', {style:'currency', currency: 'USD',});
	return money;
}

var imgSrcList = [
	"../products/images/apple.jpg",
	"../products/images/banana.png",
	"../products/images/beef.png",
	"../products/images/celery.png",
	"../products/images/cheese.png",
	"../products/images/chicken.png",
	"../products/images/chips.png",
	"../products/images/coffee.png",
	"../products/images/milk.png",
	"../products/images/pork.png",
	"../products/images/potato.png",
	"../products/images/soda.png"
];

//loads the image, name and price of product from the array to the HTML page
function generateNameAndPrice(cell,x){ /*cell is src, x is destination, the change made sort by a lot easier*/
	var item = products[x-1];
	//gets name of product
	document.getElementById("itemName" + cell).innerHTML = item.name;
	document.getElementById("itemName" + cell).style.fontSize = "xx-large";
	//gets price of product
	var price = moneyFormat(item.price);
	document.getElementById("itemPrice" + cell).innerHTML = price;
	document.getElementById("itemPrice" + cell).style.fontSize = "xx-large";
	//gets image of product
	document.getElementById(cell).src=imgSrcList[x - 1];
	
}

//Loads the cart for viewing
function getCart(){
  var x = document.getElementById("myCart");
  if (x.style.display === "none") {
	x.style.display = "block";
	var i;
	var total = 0;
	var display = document.getElementById("myCart");
	display.innerHTML = "";
	//generates a new <p> tag with the item's name, cost, and quantity 
	//in the format "name - cost X quantity"
	for(i = 0; i<cart.length;++i){
		var item = cart[i];
		var itemCost = moneyFormat(item.price);//formats price to US currency
		var displayItem = "<p>" + item.name + '-' + itemCost + " X " + item.quantity + "</p><br>";
		total += item.price * item.quantity;
		display.innerHTML += displayItem;
	}
	//adds total
	totalCost = total;
	//displays total at bottom, and then generates button to go to checkout menu
	var totalDisplay = "<p>Total: " +  moneyFormat(total) + "</p>";
	totalDisplay += "<button onclick = checkOut()>Check Out</button>"
    display.innerHTML += totalDisplay;
  } 
  else {
    x.style.display = "none";
  }	
}
//Adds the given item to the cart
function addToCart(x){
	if(searched == 1){
		searched = 0;
		var index = getIndexCart(x);
		searched = 1;
	} 
	else var index = getIndexCart(x);
	var item = products[index];
	var quantity = parseInt(document.getElementById("quantity"+x).value);
	//generates empty item
	var itemToAdd = {
			"name":"index",
			"price":0,
			"quantity":0
	};
	//gets name and price
	itemToAdd.name = item.name;
	if(checkCart(item.name, quantity) == true){
		return;
	}
	itemToAdd.price = item.price;
	//if quantity selected is blank, default is 1
	//otherwise, add given quantity
	if(Number.isNaN(quantity)){
		itemToAdd.quantity = 1;
	}
	else{
		itemToAdd.quantity = quantity;
	}
	//pushes new item onto cart
	cart.push(itemToAdd);
}
//Checks if item is in the cart already
//if it is, add the given quantity
function checkCart(name, q){
	if(Number.isNaN(q)){
		q = 1;
	}
	var i;
	for(i = 0; i < cart.length;++i){
		var item = cart[i];
		if(item.name == name){
			item.quantity += q;
			return true;
		}
	}
	return false;
}
//Goes to checkout page after storing total into storage
function checkOut(){
	var totalMoney = moneyFormat(totalCost)
	localStorage.setItem("total", totalMoney);
	window.location.href = "./checkout.html";
}

