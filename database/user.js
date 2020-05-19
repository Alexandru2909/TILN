const mongoose = require('mongoose');

require('../location.js')();
require('../book.js')();

const Schema = mongoose.Schema;

const userSchema = new Schema({
    id: {
        type: Number,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    reacheable: {
        type:[ String ],
        required: false
    },
    wishlist: [{
        type: Schema.Types.ObjectId,
        ref: 'book',
        required: false
    }],
    currently: [{
        type: Schema.Types.ObjectId,
        ref: 'book',
        required: false
    }],
    read: [{
        type: Schema.Types.ObjectId,
        ref: 'book',
        required: false
    }],
    locations: [{
            lat:{
                type: String,
                required: true
                },
            long:{
                type: String,
                required: true
                }
    }]
});

module.exports.generateHash = function (password) {
    return bcrypt.hashSync(password, bcrypt.genSaltSync(8), null);
};

module.exports.isValidPassword = function (password) {
   return bcrypt.compareSync(password, this.password);
};

const User = mongoose.model('user', userSchema);

module.exports = User;
//verify users

// module.exports.check_user = function(user,pass){
//     for (var i=0;i<userDB.length;i++){
//       if(userDB[i].name == user && userDB[i].password == pass){
//         return userDB[i].id;
//       }
//     }
//     return false;
//   }


//get the user

module.exports.get_user = function(id){
    for ( var i=0;i<userDB.length;i++){
      if(userDB[i].id == id)
        return userDB[i];
    }
  }

//set the location for any user

module.exports.set_location = function(id,lat,lon){
    for ( var i=0;i<userDB.length;i++){
      if(userDB[i].id == id){
        loc = {};
        loc.lat = Number(lat);
        loc.long = Number(lon);
        userDB[i].locations.push(loc);
        fs.writeFile('dummyusersdb.json', JSON.stringify(userDB), (err) => {
          if (err) throw err;
          console.log('Data written to file');
      });
      for(var j =0;j<locationsDB.length;j++){
        if (Math.sqrt(Math.pow(locationsDB[j].latitude - lat,2)- Math.pow(locationsDB.longitude - lon,2)) < 1){
          locationsDB[j].people_near.push(userDB[i].id);
        }
      }
    }
    }
  }

//add a book to user

  module.exports.add_book = function(title,author,username,flag){
    var book = this.search_book(title,author);
    for ( var j=0;j<userDB.length;j++){
      if(userDB[j].name == username){
        console.log(userDB[j].name,book.id,flag);
        switch(Number(flag)){
          case 1:
            console.log(j + ' read ' + book.id + userDB[j].read);
            userDB[j].read.push(book.id);
            break;
          case 2:
            console.log(j + ' reads ' + book.id + userDB[j].currently);
            userDB[j].currently.push(book.id);
            break;
          case 3:
            console.log(j + ' willread ' + book.id +userDB[j].wishlist);
            userDB[j].wishlist.push(book.id);
            break;
        }
        fs.writeFile('dummyusersdb.json', JSON.stringify(userDB), (err) => {
          if (err) throw err;
          console.log('Data written to file');
      });
      }
    }
  }

//get recomm of user

module.exports.get_recomm = function(username){
    data = [];
    for ( var i=0;i<userDB.length;i++){
      if(userDB[i].name == username){
      for (var j =0;j<userDB[i].currently.length;j++){
        console.log(userDB[i].currently[j]);
        var l = this.get_location(userDB[i].currently[j]);
        for(let k of l.people_near)
          if (k != userDB[i].id)
            data.push(this.get_user(k));

      }
      }
    }
    return data;
  }

// get the books of user

module.exports.get_user_books = function(user, flag){
    data = [];
    for (let usr of userDB){
      if (usr.name == user){
        switch (flag){
          case 0:
            data = usr.read;
            break;
          case 1:
            data = usr.currently;
            break;
          case 2:
            data = usr.wishlist;
            break;
        }
      }
    }
    users = []
    for (let x of data){
      users.push(this.get_book(x))
    }
    return users;
  }

// get the user profile

module.exports.get_user_data = function(user,asking){
    data = {};
    for (let usr of userDB){
      if (usr.name == asking){
        who = usr.locations[usr.locations.length-1];
      }}

    for (let usr of userDB){
      if (usr.name == user){
          data.mail = usr.email;
          data.contact = usr.reachable;
          data.books = [];
          for ( let l of usr.currently){
            data.books.push(this.get_book(l).titlu.concat("-", this.get_book(l).autor));
          }
          data.locations = [];
          for( let loc of usr.locations){
            data.locations.push(Math.sqrt(Math.pow(loc.lat - who.lat,2)- Math.pow(loc.long - who.long,2)).toString() + "km from your last location."); 
          }
      }
    }
    ret = [data]
    return ret;
  }

  //remove a book from user

  module.exports.remove_book = function(user,book,list){

    for ( var i=0;i<userDB.length;i++){
      if(userDB[i].name == user){

        switch(Number(list)){
          case 0:
            userDB[i].wishlist.splice(userDB[i].wishlist.indexOf(this.search_books(book)[0].id),1);
            break;
          case 1:
            userDB[i].currently.splice(userDB[i].currently.indexOf(this.search_books(book)[0].id),1);
            break;
          default:
            userDB[i].read.splice(userDB[i].read.indexOf(this.search_books(book)[0].id),1);
            break;
          }
        fs.writeFile('dummyusersdb.json', JSON.stringify(userDB), (err) => {
          if (err) throw err;
          console.log('Data written to file');
      });
    }
    }
  }

