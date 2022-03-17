var total = localStorage.getItem("total");

function printTotal(){
	var display = document.getElementById("cart");
	display.innerHTML += total;
}
//Checks if the given credit card number is in the valid format
//and that the card type is not the default selection
function verify(){
	//regex for standard credit card, in format xxxx-xxxx-xxxx-xxxx
	var cardNum = /^\(?([0-9]{4})\)?[-. ]?([0-9]{4})[-. ]?([0-9]{4})[-. ]?([0-9]{4})$/;
	var select = document.getElementById("cardType").value;
	var card = document.getElementById("cardNum").value;
	
	//checks if the number is formatted right and if the card type is not the default
	//if not, diplays an error.
	if(card.match(cardNum) && select != "select"){
		window.location.href = "./thankYou.html";
	}
	else{
		alert("Your card number was incorrect, or you did not select a type");
	}
}