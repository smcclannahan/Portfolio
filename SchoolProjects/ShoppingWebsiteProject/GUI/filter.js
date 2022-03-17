//Global
var sorted; 

//Sets all quantities to 1, so quantity  on an old selection doesnt effect the new selection
function setQuantity(){
	for(var i = 0; i < 12; i++) document.getElementById("quantity"+i).value = 1;
}

//Fixes cells that may be messed up after adding to wishlist while using the filter.
function fixCellData(){
	if(sorted) getChange(sorted,0);
	else getChange(3,0);
}

//changes the representation of the list based off the selection.
function getChange(list,resetQuantity){ /*This code is gross, but a for loop didnt work for whatever reason, i'll work on it*/
	if(getSearch() && list > 2 && resetQuantity == 1){
		alert("Cannot filter by category while searching");
		return;
	} 
	else{
		if(resetQuantity) setQuantity();
		var indexArr = [];
		sorted = list;
		if(list == 1){ /*Price low to high*/
			indexArr = [2,1,11,12,4,5,7,8,6,9,10,3];
			generateNameAndPrice(1,2);
			generateNameAndPrice(2,1);
			generateNameAndPrice(3,11);
			generateNameAndPrice(4,12);
			generateNameAndPrice(5,4);
			generateNameAndPrice(6,5);
			generateNameAndPrice(7,7);
			generateNameAndPrice(8,8);
			generateNameAndPrice(9,6);
			generateNameAndPrice(10,9);
			generateNameAndPrice(11,10);
			generateNameAndPrice(12,3);
		}
		else if(list == 2){ /*price high to low*/
			indexArr = [3,10,9,6,8,7,5,4,12,11,1,2];
			generateNameAndPrice(1,3);
			generateNameAndPrice(2,10);
			generateNameAndPrice(3,9);
			generateNameAndPrice(4,6);
			generateNameAndPrice(5,8);
			generateNameAndPrice(6,7);
			generateNameAndPrice(7,5);
			generateNameAndPrice(8,4);
			generateNameAndPrice(9,12);
			generateNameAndPrice(10,11);
			generateNameAndPrice(11,1);
			generateNameAndPrice(12,2);
		}
		else if(list == 3){/*fruit first*/
			indexArr = [1,2,3,4,5,6,7,8,9,10,11,12];
			generateNameAndPrice(1,1);
			generateNameAndPrice(2,2);
			generateNameAndPrice(3,3);
			generateNameAndPrice(4,4);
			generateNameAndPrice(5,5);
			generateNameAndPrice(6,6);
			generateNameAndPrice(7,7);
			generateNameAndPrice(8,8);
			generateNameAndPrice(9,9);
			generateNameAndPrice(10,10);
			generateNameAndPrice(11,11);
			generateNameAndPrice(12,12);
		}
		else if(list == 4){ /*Meat first*/
			indexArr = [3,6,10,1,2,4,5,7,8,9,11,12];
			generateNameAndPrice(1,3);
			generateNameAndPrice(2,6);
			generateNameAndPrice(3,10);
			generateNameAndPrice(4,1);
			generateNameAndPrice(5,2);
			generateNameAndPrice(6,4);
			generateNameAndPrice(7,5);
			generateNameAndPrice(8,7);
			generateNameAndPrice(9,8);
			generateNameAndPrice(10,9);
			generateNameAndPrice(11,11);
			generateNameAndPrice(12,12);
		}
		else if(list == 5){ /*Dairy first*/
			indexArr = [5,9,1,2,3,4,6,7,8,10,11,12];
			generateNameAndPrice(1,5);
			generateNameAndPrice(2,9);
			generateNameAndPrice(3,1);
			generateNameAndPrice(4,2);
			generateNameAndPrice(5,3);
			generateNameAndPrice(6,4);
			generateNameAndPrice(7,6);
			generateNameAndPrice(8,7);
			generateNameAndPrice(9,8);
			generateNameAndPrice(10,10);
			generateNameAndPrice(11,11);
			generateNameAndPrice(12,12);
		}
		else if(list == 6){ /*Vegetables first*/
			indexArr = [4,11,1,2,3,5,6,7,8,9,10,12];
			generateNameAndPrice(1,4);
			generateNameAndPrice(2,11);
			generateNameAndPrice(3,1);
			generateNameAndPrice(4,2);
			generateNameAndPrice(5,3);
			generateNameAndPrice(6,5);
			generateNameAndPrice(7,6);
			generateNameAndPrice(8,7);
			generateNameAndPrice(9,8);
			generateNameAndPrice(10,9);
			generateNameAndPrice(11,10);
			generateNameAndPrice(12,12);
		}
		else if(list == 7){	/*Drinks first*/
			indexArr = [8,12,9,2,3,4,5,6,7,1,10,11];
			generateNameAndPrice(1,8);
			generateNameAndPrice(2,12);
			generateNameAndPrice(3,9);
			generateNameAndPrice(4,2);
			generateNameAndPrice(5,3);
			generateNameAndPrice(6,4);
			generateNameAndPrice(7,5);
			generateNameAndPrice(8,6);
			generateNameAndPrice(9,7);
			generateNameAndPrice(10,1);
			generateNameAndPrice(11,10);
			generateNameAndPrice(12,11);
		}
		else if(list == 8){/*"Other" first*/
			indexArr = [7,1,2,3,4,5,6,8,9,10,11,12];
			generateNameAndPrice(1,7);
			generateNameAndPrice(2,1);
			generateNameAndPrice(3,2);
			generateNameAndPrice(4,3);
			generateNameAndPrice(5,4);
			generateNameAndPrice(6,5);
			generateNameAndPrice(7,6);
			generateNameAndPrice(8,8);
			generateNameAndPrice(9,9);
			generateNameAndPrice(10,10);
			generateNameAndPrice(11,11);
			generateNameAndPrice(12,12);
		}
}
	return indexArr
}

/*These functions return the correct index in the list for the current filter on the list*/
function getIndexCart(index) {
	var defaultArr = [1,2,3,4,5,6,7,8,9,10,11,12];
	if(sorted) {
		indexA = getChange(sorted,0);
		return (indexA[index] - 1);
	}
	else return (defaultArr[index] - 1);
}

function getIndexWishList(index) {
	var defaultArr = [1,2,3,4,5,6,7,8,9,10,11,12];
	if(sorted) {
		indexA = getChange(sorted,0);
		return (indexA[index]);
	}
	else return (defaultArr[index]);
}