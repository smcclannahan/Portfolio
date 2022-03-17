var products = [
	{"name":"Apple",
	"type":"fruit",
	"price":"$2.00"},
	
	{"name":"Banana",
	"type":"fruit",
	"price":"$1.00"},
	
	{"name":"Beef",
	"type":"meat",
	"price":"$5.00"},
	
	{"name":"Celery",
	"type": "vegetable",
	"price":"$2.00"},
	
	{"name":"Cheese",
	"type":"dairy",
	"price":"$3.00"},
	
	{"name":"Chicken",
	"type":"meat",
	"price":"$4.00"},

	{"name":"Chips",
	"type":"other",
	"price":"$3.00"},
	
	{"name":"Coffee",
	"type":"drink",
	"price":"$3.00"},
	
	{"name":"Milk",
	"type":"dairy",
	"price":"$4.00"},
	
	{"name":"Pork",
	"type":"meat",
	"price":"$4.00"},
		
	{"name":"Potato",
	"type":"vegetable",
	"price":"$2.00"},
		
	{"name":"Soda",
	"type":"drink",
	"price":"$2.00"}
	
];

function moneyFormat(x){
	var money = (x).toLocaleString('en-US', {style:'currency', currency: 'USD',});
	return money;
}

function wishlist() {
  var x = document.getElementById("myWishlist");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function pushTowishlist(x,list){
	if(searched == 1){
		searched = 0;
		var index = getIndexWishList(x - 1); //Added filter compatibility 
		searched = 1;
	} 
	else var index = getIndexWishList(x - 1); //Added filter compatibility 
	var thisItem = products[index-1];
    var thisName = document.getElementById("itemName"+index).innerText = thisItem.name;
	var thisPrice = moneyFormat(thisItem.price);
	document.getElementById("itemPrice"+index).innerText = thisPrice;
    var thisTotal = thisName+' '+thisPrice;
    var li = document.createElement("li");
	var thisProduct = document.createTextNode(thisTotal);
	var thisList = document.getElementById('myList');
	li.appendChild(thisProduct);

	var addBtn = document.createElement("input");
	addBtn.type = "text";
	addBtn.placeholder = "##";
	addBtn.value = document.getElementById("quantity"+(x-1)).value;
	addBtn.size = "2";
	addBtn.maxLength = "2";
    li.appendChild(addBtn);
	thisList.appendChild(li);

    var removeBtn = document.createElement("input");
    removeBtn.type = "button";
    removeBtn.value = "Remove";
    removeBtn.onclick = remove;
    li.appendChild(removeBtn);
	thisList.appendChild(li);
	if(searched == 1){
		searched = 0;
		fixCellData(); //Fixes last issue with wishList compatibility, where text and prices would change on cells
		searched = 1;
	} 
	else fixCellData(); //Fixes last issue with wishList compatibility, where text and prices would change on cells
}

function remove(e) {
  var el = e.target;
  el.parentNode.remove();
}

function welcome() {
	window.location.href = "../GUI/GUI.html"
}
function welcomeBack() {
	window.location.href = "../welcomePage/welcome.html"
}

function aboutMe() {
	var x = document.getElementById("aboutMePage");
	if (x.style.display === "none") {
	  x.style.display = "block";
	} else {
	  x.style.display = "none";
	}
  }