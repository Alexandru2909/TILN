// pentru a instala cele necesare trebuie "npm install"
// pentru a porni serverel :"node main.js"
var myDB = require("./dummydb")
// exemplu de acces in bd
var mongodb = require('mongodb')
var ObjectID = mongodb.ObjectID;
var express        =        require("express");
var bodyParser     =        require("body-parser");
var app            =        express();
const UserP = require('../database/newdb')

var generateRandomString = function(length){  
  return crypto.randomBytes(Math.ceil(length/2))  
  .toString('hex') /* Convert to hexa formate */  
  .slice(0,length);  
};  

var sha512 = function(password, salt){  
  var hash = crypto.createHmac('sha512',salt);  
  hash.update(password);  
  var value = hash.digest('hex');  
  return{  
      salt:salt,  
      passwordHash:value  
  }  
};  

function saltHashPassword(userPassword){  
  var salt = generateRandomString(16);  
  var passwordData = sha512(userPassword,salt);  
  return passwordData;  
}  

function checkHashPassword(userPassword,salt){  
  var passwordData = sha512(userPassword,salt);  
  return passwordData;  
} 


function search_books(title,db){
  var data =[];
  for (let i of db){
    if ( i.titlu.toLowerCase().search(title.toLowerCase()) != -1 || i.autor.toLowerCase().search(title.toLowerCase()) != -1 )
      data.push(i);
  }
  return data;
}

function search_book(title,author,db){
  var data =[];
  for (let i of db){
    if ( i.titlu.toLowerCase() == title.toLowerCase() && i.autor.toLowerCase() == author.toLowerCase())
      data.push(i);
      return data;
  }
}

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

var MongoClient = mongodb.MongoClient

var url = 'mongodb://localhost:27017'

MongoClient.connect(url,{useNewUrlParser:true, useUnifiedTopology:true},function(err, client)  
{  
    if(err)  
    {  
        console.log('Unable to connect to MongoDB server.Error',err);  
    }  
    else  
    {  
        //Start Web Server  
        app.listen(3000,()=> {console.log('Connected to MongoDb server, Webservice running on on port 3000');  
    });  
}

    app.post('/register',(request,response,next)=>  
    {  
        var post_data = request.body;  

        var plain_password = post_data.password;  
        var hash_data = saltHashPassword(plain_password);  
  
        var password = hash_data.passwordHash;  
        var salt = hash_data.salt;
  
        var firstname = post_data.firstname;  
        var lastname = post_data.lastname;   
        var email = post_data.email; 
  
        var insertJson = { 
            'username':username, 
            'firstname':firstname,  
            'lastname' : lastname,  
            'email': email,   
            'password': password, 
            'salt': salt  
        };  
  
        var db = client.db('newdb');
   
        db.collection('user').find({'email':email}).count(function(err,number){  
            if(number != 0){  
                console.log('User Email already exist!');  
                response.json('User Email already exist!');  
            }else{  
                db.collection('user').insertOne(insertJson,function(err,res){  
                    console.log('User Registeration Successful..');  
                    response.json('User Registeration Successful..');  
                });  
            }  
        });  
  
    });

    app.post('/login',(request,response,next)=>  
    {  
        var post_data = request.body;  
  
        var username = post_data.username;  
        var userPassword = post_data.password;  
  
        var db = client.db('newdb');  
          
        db.collection('user').find({'username':username}).count(function(err,number){  
            if(number == 0){  
                console.log('username not exist!');  
                response.json('username not exist!');  
            }else{   
                db.collection('user').findOne({'email':email},function(err,user)  
                {  
                    var salt = user.salt;  
                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash; 
                    var encrypted_password = user.password; 
                    if(hashed_password == encrypted_password)  
                    {  
                        console.log('User Login Successful..');  
                        response.json('User Login Successful..');  
                    }else  
                    {  
                        console.log('Login Failed Wrong Password..');  
                        response.json('Login Failed Wrong Password..');  
                    }  
                });  
            }  
        });  
  
    });


    module.exports.GetProfile = async (res, body, email) => {
      try {
          const user = await UserP.GetProfile(email);
          return Utils.returnSuccess(res, user, 200);
      } catch (error) {
          return Utils.returnError(res, '[ERROR] User with email' + email + ' not found in DB.', 404)
      }
  }

app.get('/',function(req,res){
  res.sendfile("index.html");
});
// exemplu de folosire a stringurilor
app.post('/login',function(req,res){
  var user_name=req.body.user;
  var password=req.body.password;
  console.log("User name = "+user_name+", password is "+password);
  res.end("yes");
});
app.post("/get_book",function(req,res){
  var title = req.body[0].title;
  var author = req.body[0].author;
  var x = search_book(title,author,myDB);
  console.log(x);
  res.send(x);
});
app.post("/get_books",function(req,res){
  // Dat fiind ca rezultatul e trimis ca obiect arrayJSON,trebuie sa apelam req.body[0].ceva;
  // console.log(req,req.body[0],req.body.title,req.body[0].title);
  var title = req.body[0].title;
  var x = search_books(title,myDB);
  console.log(x);
  res.send(x);
});
app.listen(3000,function(){
  console.log("Started on PORT 3000");
})
