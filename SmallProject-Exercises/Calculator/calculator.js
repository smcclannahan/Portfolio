const express = require("express");
const bodyParser = require("body-parser");

const app = express();
app.use(bodyParser.urlencoded({extended: true}));

app.get("/", function(req, res){
    res.sendFile(__dirname + "/index.html");
});

app.get("/bmicalculator", function(req, res){
    res.sendFile(__dirname + "/bmiCalculator.html");
});

app.post("/", function(req, res){
    res.send("The result is " + (Number(req.body.num1) + Number(req.body.num2)));
});

app.post("/bmicalculator", function(req, res){
    let weight = parseFloat(req.body.weight);
    let height = parseFloat(req.body.height);

    let result = Math.round(weight/(Math.pow(height, 2)));

    res.send("BMI result is " + result);
});

app.listen(3000, function(){
    console.log("Server Started on port 3000");
});
