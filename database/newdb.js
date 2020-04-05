const mongoose = require ('mongoose');
const config = require('../config');
const conn = mongoose.createConnection(config.MONGO_CONNECTION, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    useFindAndModify: false
})

mongoose.set('useFindAndModify', false);
conn.on('error', error => {
    if (error) throw error;
})

conn.on('open', _ => {
    console.log(`MongoDB connected successfully`);
})

const Schema = mongoose.Schema;

const userSchema = new Schema({
    username: {
        type: String,
        required: true
    },
    firstname: {
      type: String,
      required: true
    },
    lastname: {
      type: String,
      required: true
    },
    email: {
        type: String,
        required: true
    },
    password: {
        type: String,
        required: true
    }
})

const bookSchema = new Schema({
    title:{
        type: String,
        required: true
    },
    author:{
        type: String,
        required: true
    },
    description:{
        type: String,
        required: true
    }
})


const locationsSchema = new Schema({
    name:{
        type: String,
        required: true
    },
    longitude:{
        type: String,
        required: true
    },
    latitude: {
        type: String,
        required: true
    }
})

module.exports = mongoose.model('User', userSchema);
module.exports = mongoose.model('Books', bookSchema);
module.exports = mongoose.model('Locations', locationsSchema);

// mongoose.Promise = global.Promise;
// mongoose.connect('mongodb://localhost:27017/TILN');

const userModel = conn.model('users', userSchema);

class User {
  constructor(username, email, password) {
    this.username = username;
    this.email = email;
    this.password=password;
    this.firstname=fristname;
    this.lastname=lastname;
  }

  save() {
    const db = getDb();
    db.collection('User').insertOne(this);
    console.log("user saved");
  }
}

module.exports.GetProfile = (email) => {
    return new Promise((resolve, reject) => {
        const query = userModel.findOne({ _email: email });

        query.lean().exec((err, res) => {
            if (res) resolve(res)
            else
                reject(err)
        })
    })
}