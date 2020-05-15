var bookDB = require('./dummybooksdb.json');
var userDB = require('./dummyusersdb.json');
var locationsDB = require('./dummylocationsdb.json');
const fs = require('fs');
module.exports = {
  search_books:function(title){
    var data =[];
    for (let i of bookDB){
      if ( i.titlu.toLowerCase().search(title.toLowerCase()) != -1 || i.autor.toLowerCase().search(title.toLowerCase()) != -1 )
        data.push(i);
    }
    return data;
  },

  search_book:function(title,author){
    for (let i of bookDB){
      if ( i.titlu.toLowerCase() == title.toLowerCase() && i.autor.toLowerCase() == author.toLowerCase())
        return i;
    }
  },
  check_user:function(user,pass){
    for (var i=0;i<userDB.length;i++){
      if(userDB[i].name == user && userDB[i].password == pass){
        return userDB[i].id;
      }
    }
    return false;
  },
  get_user:function(id){
    for ( var i=0;i<userDB.length;i++){
      if(userDB[i].id == id)
        return userDB[i];
    }
  },
  get_book:function(id){
    for ( var i=0;i<bookDB.length;i++){
      if(bookDB[i].id == id)
      return bookDB[i];
    }
  },
  set_location:function(id,lat,lon){
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
  },
  add_book:function(title,author,username,flag){
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
  },
  get_location:function(id){
    for (let i of locationsDB){
      if( i.id == id)
        return i;
    }
  },
  get_recomm:function(username){
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
  },
  get_people_near:function(location_name){
    data = []
    for (let loc of locationsDB){
      if (loc.name == location_name){
        for (var i = 0; i < loc.people_near.length; i++){
          data.push(this.get_user(loc.people_near[i]))
        }
      }
    }
    return data;
  },
  get_user_books:function(user, flag){
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
  },
  get_user_data:function(user){
    data = {};
    for (let usr of userDB){
      if (usr.name == user){
          data.mail = usr.email;
          data.contact = usr.reachable;
          data.books = [];
          for ( let l of usr.currently){
            data.books.push(this.get_book(l).titlu.concat("-", this.get_book(l).autor));
          }
          data.locations = usr.locations;

      }
    }
    ret = [data]
    return ret;
  }
}