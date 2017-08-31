const express = require('express');
const bodyParser = require('body-parser');
const pug = require('pug');
const sqslib = require('solutions-sqs');
const bunyan = require('bunyan');

const log = bunyan.createLogger({name: "adminpanel"});
const sqsProps = {
    url: "http://sqs.openmarket.com",
    username: "mariner-team",
    password: ""
};
const sqs = sqslib.create(sqsProps, log);
const tapOnQueue = sqs("hackathon-lta", "lta-tapon-queue");
const tapOffQueue = sqs("hackathon-lta", "lta-tapoff-queue");
const disruptQueue = sqs("hackathon-lta", "lta-disrupt-queue");

const app = express();
app.use(express.static("static"));
app.use(bodyParser.json());

app.get("/", function (req, res) {
    res.render("page.pug", { pageTitle:  "London Travel Assistant - Admin Panel" });
});

app.get("/status/:msisdn", function (req, res) {
    console.log("status for: " + req.params.msisdn);
    res.send(req.params.msisdn);
});

app.get("/tapon/:msisdn/:station", function(req, res) {
    console.log("tap on for: " + req.params.msisdn + " at " + req.params.station);
    tapOnQueue.putMessage({ msisdn: req.params.msisdn, station: req.params.station });
    res.sendStatus(200);
});

app.get("/tapoff/:msisdn", function(req, res) {
    console.log("tap off for: " + req.params.msisdn);
    tapOffQueue.putMessage({ msisdn: req.params.msisdn});
    res.sendStatus(200);
});

app.get("/disrupt/:msisdn", function(req, res) {
    console.log("disrupting: " + req.params.msisdn);
    disruptQueue.putMessage({ msisdn: req.params.msisdn});
    res.sendStatus(200);
});

app.listen(3000, function () {
    console.log('Example app listening on port 3000!');
});
