
var assert = require('assert'); 
var mongo = require('mongodb');
var hash = require('./pass').hash;
var Server = mongo.Server,
    Db = mongo.Db, 
    BSON = mongo.BSONPure,
    Grid = mongo.Grid,
    ObjectID = mongo.ObjectID,
    GridStore=mongo.GridStore,
    Binary=mongo.Binary,
    Code=mongo.Code,
    BSON=mongo.pure().BSON, 
    ReplSetServers=mongo.ReplSetServers,
    MongoClient=mongo.MongoClient,
    assert,
    format=require('util').format; 
            


var server = new Server('localhost',27017,{auto_reconnect:true}) ; 

db = new Db('libdb',server) ; 


db.open(function(err,db){
   
   //database 
    if(!err){
        console.log("Connected to 'user.db' database");
        db.collection('user',{strict:true},function(err,collection){
        if(err){
                console.log("The 'user' collection doesn't exist"); 
                populateDB();
            
/*
        hash('1234',function(err,salt,hash){
                     if(err){
                      throw err;
                     }
                     db.collection('user',function(err,collection){
                            collection.update({'username':'akash0509'},{$push:{'salt':salt,'hash':hash }},{safe:true},function(err,result){
                             if(err){
                                console.log('Error storing password'+err);    
                             }
                             else{
                                  console.log(''+result+'document(s) updated');
                                  //res.send(user);
                              }

                    
                    });
                });
            });

        
        hash('5678',function(err,salt,hash){
                     if(err){
                      throw err;
                     }
                     db.collection('user',function(err,collection){
                            collection.update({'username':'vdshelke'},{$push:{'salt':salt,'hash':hash }},{safe:true},function(err,result){
                             if(err){
                                console.log('Error storing password'+err);    
                             }
                             else{
                                  console.log(''+result+'document(s) updated');
                                  //res.send(user);
                              }

                    
                    });
                });
            });

            */
              
        }
        });
    }
});
    


exports.findAll = function(req,res){ 
    db.collection('user',function(err,collection){ 
            collection.find().toArray(function(err,items){
                        res.send(items);
                })
        });
}

exports.addUser = function(req,res){
    var user = req.body ; 
    console.log('Adding Book:'+JSON.stringify(user));
    db.collection('user',function(err,collection){ 
               collection.insert(user,{safe:true},function(err,result){ 
                        if(err){
                            res.send({'error':'An error has occured'});
                        }
                        else{
                            console.log('Success:'+JSON.stringify(result[0]));
                            res.send(result[0]);
                        }
                   });
        });
}


exports.updateUser= function(req,res){
    var id = req.params.id ; 
    var user = req.body ; 
    console.log('Updating user:'+id);
    console.log(JSON.stringify(user));

    db.collection('user',function(err,collection){
        collection.update({'_id':new BSON.ObjectID(id)},user,{safe:true},function(err,result){
            if(err){
                console.log('Error updating book:'+err);
            }
            else{
                console.log(''+result+'document(s) updated');
                res.send(user);
            }

        });
    }
    );
}

exports.deleteUser = function(req,res){
    var id  = req.params.id ; 
    console.log('Deleting user:'+id);

    db.collection('user',function(err,collection){
            collection.remove({'_id':new BSON.ObjectID(id)},{safe:true},function(err,result){
                if(err){
                    res.send({'error':'An error has occured -'+err});
                }

                else{
                    console.log(''+result+'document(s) deleted' );
                    res.send(req.body);
                }
            });
         });
}




exports.authenticate = function(name, pass,fn) {
  //if (!module.parent) {
      console.log('authenticating %s:%s', name, pass);
  //}
  //var user = users[name];
  var user ;  
  db.collection('user',function(err,collection){
        collection.find({'username':name})
        .toArray(function(err,items){
                //res.send(items);
               // console.log('items:'+items); 
        user = items[0];  
        return fn(null,user);           
            });
    });

  //cursor.nextObject(function(err,item){
    //    user = item;
  //});

 /* console.log('Fetched user:'+user);

 
	 if (!user) return fn(new Error('cannot find user'));
  // apply the same algorithm to the POSTed password, applying
  // the hash against the pass / salt, if there is a match we
  // found the user
   hash(pass, user.salt, function(err, hash){
    if (err) return fn(err);
    if (hash == user.hash) return fn(null, user);
    fn(new Error('invalid password'));


  });
   */
}





var populateDB = function(){
    var users = [
                 {
                      username:'akash0509',
                 },

                 {
                       username:'vdshelke',
                 }


                 ];


                 

                

                 db.collection('user',function(err,collection){
                    collection.insert(users,{safe:true},function(err,result){})
                 });
};
