const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const locationSchema = new Schema ({
    id: { 
        type: Number,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    latitude: {
        type: String,
        required: true
    },
    longitude: {
        type: String,
        required: true
    },
    people_near: [{
        type: Schema.Types.ObjectId,
        ref: 'user',
        required: false
    }]
});

module.exports = mongoose.model('location', locationSchema);

//get the location

module.exports.get_location = function(id){
    for (let i of locationsDB){
      if( i.id == id)
        return i;
    }
  }

// get the people near

module.exports.get_people_near = function(location_name){
    data = []
    for (let loc of locationsDB){
      if (loc.name == location_name){
        for (var i = 0; i < loc.people_near.length; i++){
          data.push(this.get_user(loc.people_near[i]))
        }
      }
    }
    return data;
  }
