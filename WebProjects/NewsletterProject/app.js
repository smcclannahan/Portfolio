const express = require("express");
const https = require("https");
const bodyParser = require("body-parser");

const app = express();

app.use(express.static("public"));

app.use(bodyParser.urlencoded({extended: true}));

app.listen(process.env.PORT || 3000, function(){
    console.log("Listening on port 3000");
});

app.post("/", function(req, res){
    const firstName = req.body.firstName;
    const lastName = req.body.lastName;
    const userEmail = req.body.email;

    const data = {
        members:[
            {
                email_address: userEmail,
                status: "subscribed",
                merge_fields: {FNAME: firstName, LNAME: lastName}
            }
        ]
    }
    const jsonData = JSON.stringify(data);

    https.request(url, options, function() {

    });

    console.log(firstName, lastName, userEmail);
});

app.get("/", function(req, res){
    res.sendFile(__dirname + "/signup.html");
});

