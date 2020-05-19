const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const bookSchema = new Schema({
    id: { 
        type: Number,
        required: true
    },
    titlu: {
        type: String,
        required: true
    },
    autor: {
        type: String,
        required: true
    },
    descriere: {
        type: String,
        required: true
    },
    locatii: [{
        type: Schema.Types.ObjectId,
        ref: 'location',
        required: false
    }],
    cover_photo_url: {
        type: String,
        required: true
    }

});

module.exports = mongoose.model('book', bookSchema);

//search for books

module.exports.search_books = function(title){
    var data =[];
    for (let i of bookDB){
      if ( i.titlu.toLowerCase().search(title.toLowerCase()) != -1 || i.autor.toLowerCase().search(title.toLowerCase()) != -1 )
        data.push(i);
    }
    return data;
  }

  //seacrh for a specific book

  module.exports.search_book = function(title,author){
    for (let i of bookDB){
      if ( i.titlu.toLowerCase() == title.toLowerCase() && i.autor.toLowerCase() == author.toLowerCase())
        return i;
    }
  }

  //get a book

  module.exports.get_book = function(id){
    for ( var i=0;i<bookDB.length;i++){
      if(bookDB[i].id == id)
      return bookDB[i];
    }
  }

  
