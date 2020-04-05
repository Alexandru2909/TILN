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
  if( tools.check_user(user_name,password))
    res.send("True");
  else
    res.send("False");
});
app.post("/get_book",function(req,res){
  var title = req.body[0].title;
  var author = req.body[0].author;
  var x = tools.search_book(title,author,myDB);
  console.log(x);
  res.send(x);
});
app.post("/get_books",function(req,res){
  // Dat fiind ca rezultatul e trimis ca obiect arrayJSON,trebuie sa apelam req.body[0].ceva;
  // console.log(req,req.body[0],req.body.title,req.body[0].title);
  var title = req.body[0].title;
  var x = tools.search_books(title,myDB);
  console.log(x);
  res.send(x);
});
app.listen(3000,function(){
  console.log("Started on PORT 3000");
})
