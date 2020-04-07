// pentru a instala cele necesare trebuie "npm install"
// pentru a porni serverel :"node main.js"
var myDB = require("./dummydb")
// exemplu de acces in bd


var tools = require("./func.js");
var express        =        require("express");
var bodyParser     =        require("body-parser");
var app            =        express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/',function(req,res){
  res.sendfile("index.html");
});
// exemplu de folosire a stringurilor
app.post('/login',function(req,res){
  var user_name=req.body.user;
  var password=req.body.password;
  console.log("User name = "+user_name+", password is "+password);
  var id = tools.check_user(user_name,password)
  if(id != false){
    res.send("True");
    if(req.body.latitude != undefined && req.body.longitude != undefined){
      tools.set_location(id,req.body.latitude,req.body.longitude);
    }
  }
  else
    res.send("False");
});
app.post("/get_book",function(req,res){
  var response = [];
  var title = req.body[0].title;
  var author = req.body[0].author;
  let x = tools.search_book(title,author); 
  console.log('/GETBOOK');
  console.log(x==undefined );
  console.log(title+' '+author);
  console.log(x.id,x.titlu);
  console.log(x.locatii);
  data = [];
  for (var k = 0; k < x.locatii.length; k++){
    data.push(tools.get_location(x.locatii[k]).name);
    // console.log(x[0].locatii[k]);
  }
  var y = JSON.parse(JSON.stringify(x));
  y.locatii = data;
  response.push(y);
  res.send(response);
});
app.post("/get_books",function(req,res){
  // Dat fiind ca rezultatul e trimis ca obiect arrayJSON,trebuie sa apelam req.body[0].ceva;
  // console.log(req,req.body[0],req.body.title,req.body[0].title);
  var title = req.body[0].title;
  var x = tools.search_books(title);
  for (var j = 0; j < x.length; j++){
  data = [];
    for (var k = 0; k < x[j].locatii.length; k++){
      data.push(tools.get_location(Number(x[j].locatii[k])).name);
      // console.log(x[j].locatii[k]);
    }
    x[j].locatii = data;
  }
  console.log(x);
  res.send(x);
});
app.post("/get_notifs",function(req,res){
  var user = req.body[0].user;
  console.log("HA,"+user);
  var x = tools.get_recomm(user);
  console.log(x);
  res.send(x);
});
app.post("/add_on", function(req,res){
  var title_author = req.body.title.split(" - ");
  var title = title_author[0];
  var author = title_author[1];
  var flag = req.body.list;
  var user = req.body.user;
  tools.add_book(title, author, user, flag)
  console.log(title, author, flag, user);
  res.send("all good");
});
app.post("/get_people", function(req, res){
  var location = req.body[0].location;
  var x = tools.get_people_near(location);
  console.log(x);
  res.send(x);
  console.log(location);
});
app.post("/get_user_books", function(req, res){
  var flag = req.body[0].flag;
  var user = req.body[0].user;
  console.log(flag, user);
  var x = tools.get_user_books(user, flag);
  res.send(x)
  console.log(x);
})
app.listen(3000,function(){
  console.log("Started on PORT 3000");
});
// console.log(tools.get_recomm("Drago"));
