var express = require('express')  , 
books = require('./routes/books'),
users = require('./routes/user');
//var session = require('express-session');
var bodyParser = require('body-parser');
var hash = require('./routes/pass').hash;
 


var assert = require('assert');
var app = express(); 

app.configure(function(){
    app.use(express.logger('dev'));
    app.use(express.bodyParser()); 
}) ;
 
//app.get('/wines',wines.findAll); 

app.get('/books/search',books.findByTitle);
  
app.post('/books',books.addBook) ; 

app.put('/books/:id',books.updateBook); 

app.delete('/books/:id',books.deleteBook) ; 

app.get('/books',books.findAll);

app.get('/users',users.findAll);

app.post('/users',users.addUser);

app.put('/users/:id',users.updateUser);

app.delete('/users/:id',users.deleteUser);

/*
app.use(bodyParser.urlencoded({ extended: false }));

app.use(session({
  resave: false, // don't save session if unmodified
  saveUninitialized: false, // don't create session until something stored
  secret: 'shhhh, very secret'
}));
*/
/*
// Session-persisted message middleware

app.use(function(req, res, next){
  var err = req.session.error;
  var msg = req.session.success;
  delete req.session.error;
  delete req.session.success;
  res.locals.message = '';
  if (err) console.log('Success msg');
  if (msg) console.log('Failed msg');
  next();
});
*/
app.post('/login', function(req, res){

   var username = req.body.username;
   var pass = req.body.password;  
  users.authenticate(username,pass, function(err,user){
    console.log('Values:'+JSON.stringify(username)+' '+JSON.stringify(pass)); 
    console.log('USER '+user) ; 
    if (user) {
      
      console.log('Inside if') ; 
      console.log('xyz:'+user);
  
	  if(pass == user.password){
  	 console.log('Success');
 	 }
  
  else{
  	console.log('Password Incorrect!');
  }
      /*
      req.session.regenerate(function(){
        // Store the user's primary key
        // in the session store to be retrieved,
        // or in this case the entire user object
        req.session.user = user;
       /* req.session.success = 'Authenticated as ' + user.name
          + ' click to <a href="/logout">logout</a>. '
          + ' You may now access <a href="/restricted">/restricted</a>';
        console.log('Login  Success');
        res.send('Success'); 
        */
      

      
    } 
    else {
    //  req.session.error = 'Authentication failed, please check your '
      //  + ' username and password.'
       // + ' (use "tj" and "foobar")';
      console.log('Login  Failed');
      res.send('Failed');
    }
  });
});

/*app.post('/login',function(req,res){
    var user =  req.body.username ;
    var pass = req.body.password;
    console.log('Values:' + JSON.stringify(user)+':'+JSON.stringify(pass)); 

    
  
});*/


app.listen(3000) ; 
console.log('Listening on port 3000') ; 
