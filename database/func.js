var bookDB = require('./dummybooksdb.json');
var userDB = require('./dummyusersdb.json');
var locationsDB = require('./dummylocationsdb.json');
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
  search_user:function(user,pass){
    for (var i=0;i<userDB.length;i++){
      console.log(userDB[i]);
      if(userDB[i].name == user && userDB[i].password == pass){
        return true;
      }
    }
    return false;
  }
}