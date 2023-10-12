const express = require("express");
const bodyParser = require("body-parser");

const app = express();

app.get("/", function(req, res){
    let today = new Date();

    if(today.getDay() === 6 || today.getDay() === 0){
        res.write("<h1>It's the weekend!</h1>");
        res.send();
    }
    else{
        res.sendFile(__dirname + "/index.html");
    }
    
});

app.listen(3000, function(){
    console.log("Server Started on port 3000");
});
