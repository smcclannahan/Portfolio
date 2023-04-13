const express = require("express");
const https = require("https");
const bodyParser = require("body-parser");

const app = express();

app.use(bodyParser.urlencoded({extended: true}));

app.get("/", function(req, res){
    res.sendFile(__dirname + "/index.html");
});


app.listen(3000, function(){
    console.log("Listening on port 3000");
});

app.post("/", function(req, res){
   const query = req.body.cityName;
    const apiKey = "";
    const units = "imperial";

    const url = "https://api.openweathermap.org/data/2.5/weather?appid=fea5b92a52db9e42217ca57a5e01f789&q="+ query + "&units=" + units;
    https.get(url, function(response){
        console.log(response.statusCode);

        response.on("data", function(data){
            const weatherData = JSON.parse(data);
            const desc = weatherData.weather[0].description;    
            const icon = weatherData.weather[0].icon;
            const imageURL = "https://openweathermap.org/img/wn/"+ icon +"@2x.png";
            res.write("<p>The weather is currently " + desc + "</p>");
            res.write("<h1>The temp is " + weatherData.main.temp + " degrees Farenheit in " + query + "</h1>");
            res.write("<img src=" + imageURL + ">");
            res.send();
    })
    });

})