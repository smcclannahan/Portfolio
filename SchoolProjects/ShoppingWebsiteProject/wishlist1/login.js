var userList = [
	{
		username: "test",
		password: "test"
	}
]

function loginCheck() {
	
	var username = document.getElementById('username').value
	var password = document.getElementById('password').value

	for(i = 0; i < userList.length; i++) {
		if(username == userList[i].username && password == userList[i].password){
			window.alert(username + " is logged in!");
			window.location.href = "../welcomePage/welcome.html";
			return
		}
	}
	window.alert("Incorrect username and or password");

}

function registerCheck() {

	var newUsername = document.getElementById('newUsername').value;
	var newPassword = document.getElementById('newPassword').value;

	var newuser = {
		username: newUsername,
		password: newPassword
	}

	if(newUsername == "" || newPassword == ""){
		window.alert("Invalid username and or password");
	}
	else {
		userList.push(newuser);
		window.alert("Your account is now registered! Your username is " + newUsername + " and your password is " + newPassword);
	}

}

// var registrationInfo = [
// 	{
// 		username: derrick,
// 		password: vang
// 	}
// ]

// function signIn() {
// 	var modal = document.getElementById('login');

// 	window.onclick = function(event) {
// 		if (event.target == modal) {
// 			modal.style.display = "none";
// 		}
// 	}
// }

function signUp() {
	// var x = document.getElementById("register");
	// if (x.style.display === "none") {
	//   x.style.display = "block";
	// } else {
	//   x.style.display = "none";
	// }
	window.location.href = "../welcomePage/register.html";
}

// function registerCheck() {

// 	var initialUsername = document.getElementById('username2').value;
// 	var firstPassword = document.getElementById('password2').value;
// 	//var secondPassword = document.getElementById('password3').value;
// 	var newUser = {
// 		username: initialUsername,
// 		password: firstPassword
// 	}

// 	// if(firstPassword == secondPassword) {
// 	// 	window.alert("Your username is " + initialUsername + " and your password is " + firstPassword);
// 	// 	registrationInfo.push(newUser);
// 	// 	return
// 	// }
// 	registrationInfo.push(newUser);
// 	//window.alert("Incorrect password. Try again.");
// }

// function loginCheck() {
// 	var usernamen = document.getElementById('username1').value;
// 	var passwordn = document.getElementById('password1').value;
// 	for(i = 0; i < registrationInfo.length; ++i){
// 	if(usernamen == registrationInfo[i].username && passwordn == registrationInfo[i].password){
// 		window.alert(usernamen + " is logged in!");
// 		return
// 		}
// 	}
// 	window.alert("Incorrect username or password. Try again.");
// }