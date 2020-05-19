// pentru a instala cele necesare trebuie "npm install"
// pentru a porni serverel :"node main.js"
var myDB = require("./dummydb")
// exemplu de acces in bd


var tools = require("./func.js");//s-ar putea sa nu mai fie nevoie de asta, si atunci o sa facem require doar la fisierele user.js, location.js, book.js si apelam functia necesara direct(adica fara 'tools.'), nu stiu exact, asa am vazut pe net ca merge
var express        =        require("express");
var bodyParser     =        require("body-parser");

const User = require('../models/User');

//conectarea la mongodb
var mongoose       =        require("mongoose");

var mongoDB = 'insert_your_database_url_here, aici trebuie creata o baza de date locala in compass, utilitar care trebuie descrcat, impreuna cu mongodb ';
mongoose.connect(mongoDB/* mongoDB e numele bazei de date, poate avea orice nume*/, { useNewUrlParser: true });

let db = mongoose.connection;

// Check connection
db.once('open', function () {
  console.log('Connected to MongoDB');
});

// Check for DB errors
db.on('error', function (err) {
  console.log(err);
});

// Init App
var app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/',function(req,res){
  res.sendfile("index.html");
});
// // exemplu de folosire a stringurilor
// app.post('/login',function(req,res){
//   var user_name=req.body.user;
//   var password=req.body.password;
//   console.log("User name = "+user_name+", password is "+password);
//   var id = tools.check_user(user_name,password)
//   if(id != false){
//     res.send("True");
//     if(req.body.latitude != undefined && req.body.longitude != undefined){
//       tools.set_location(id,req.body.latitude,req.body.longitude);
//     }
//   }
//   else
//     res.send("False");
// });

// am facut 2 functii de login si register, care trebuie configurate sa mearga pe android, dupa ce e configurata bd

router.post('/login', function(req, res){
  console.log(req.body);

  //Check in the database if user exist
  User.findOne({email: req.body.email}, function (err, user) {
    if(err){
      console.log('POST /users/login : ' + err);
      res.render('500');
    }
    //if user don't found or password incorrect 
        //-> redirect to login page with error message
    else if(!user || !user.isValidPassword(req.body.password)){
      req.flash('error', "Email or password is incorrect");
      res.redirect('/login');
    }
    //connected -> redirect to home page
    else{
      req.login(user, function(err) {
        if (err) { 
          res.render('500'); 
        } else {

          req.flash('success', "You're now logged in.");
        }
      });
    }
  });
});

app.post('/register', function(req, res){

  const { name, email, password, password2 } = req.body;

  let errors = [];

  if (!name || !email || !password || !password2) {
    errors.push({ msg: 'Please enter all fields' });
  }

  if (password != password2) {
    errors.push({ msg: 'Passwords do not match' });
  }

  if (password.length < 6) {
    errors.push({ msg: 'Password must be at least 6 characters' });
  }

  if (errors.length > 0) {
    res.render('register', {
      errors,
      name,
      email,
      password,
      password2
    });
  } else {
    User.findOne({ email: email }).then(user => {
      if (user) {
        errors.push({ msg: 'Email already exists' });
        res.render('register', {
          errors,
          name,
          email,
          password,
          password2
        });
      } else {
        const newUser = new User({
          name,
          email,
          password
        });

        bcrypt.genSalt(10, (err, salt) => {
          bcrypt.hash(newUser.password, salt, (err, hash) => {
            if (err) throw err;
            newUser.password = hash;
            newUser
              .save()
              .then(user => {
                req.flash(
                  'success_msg',
                  'You are now registered and can log in'
                );
                res.redirect('/login');
              })
              .catch(err => console.log(err));
          });
        });
      }
    });
  }
});

//pana aici

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
});
app.post("/fetch_user",function(req, res){
  var user = req.body[0].user;
  var asking = req.body[0].asking;
  console.log(user);
  var x = tools.get_user_data(user,asking);
  res.send(x)
  console.log(x);
});
app.post("/remove_book_from_list",function(req, res){
  var user = req.body[0].user;
  var book = req.body[0].book;
  var list = req.body[0].list;
  console.log(user);
  tools.remove_book(user,book,list);
});
app.listen(3000,function(){
  console.log("Started on PORT 3000");
});
// console.log(tools.get_user_data("Drago"));
// console.log(tools.remove_book("Drago","Ion",2));
