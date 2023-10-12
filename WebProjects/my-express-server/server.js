const express = require("express");
const app = express();

app.get("/", function(req, res){
    res.send("<h1>Hello!</h1>")
});

app.get("/contact", function(req, res){
    res.send("<h1>Contact me @ fakeemail@fakeplace.com!</h1>")
});

app.get("/about", function(req, res){
    res.send("<h2>Hi! I'm Sean McClannahan, and I know a b and c programming langauages!</h2>");
});

app.listen(3000, function(){
    console.log("Server Started on port 3000");
});

