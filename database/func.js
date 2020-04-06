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
    var data =[];
    for (let i of bookDB){
      if ( i.titlu.toLowerCase() == title.toLowerCase() && i.autor.toLowerCase() == author.toLowerCase())
        data.push(i);
        return data;
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
        userDB[i].latitude = Number(lat);
        userDB[i].longitude = Number(lon);
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
    for (let i of bookDB){
      if ( i.titlu.toLowerCase() == title.toLowerCase() && i.autor.toLowerCase() == author.toLowerCase())
        for ( var j=0;j<userDB.length;j++){
          if(userDB[j].name == username){
            switch(flag){
              case 1:
                userDB[j].read.push(i.id);
                break;
              case 2:
                userDB[j].currently.push(i.id);
                break;
              case 3:
                userDB[j].wishlist.push(i.id);
                break;
            }
            fs.writeFile('dummyusersdb.json', JSON.stringify(userDB), (err) => {
              if (err) throw err;
              console.log('Data written to file');
          });
          }
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
        var l = this.get_location(userDB[i].currently[j]);
        for(let k of l.people_near)
          data.push(this.get_user(k));

      }
      }
    }
    return data;
  }
}