
var assert = require('assert'); 
var mongo = require('mongodb');

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
    assert; 
            


var server = new Server('localhost',27017,{auto_reconnect:true}) ; 

db = new Db('libdb',server) ; 


db.open(function(err,db){
   
   //database 
    if(!err){
        console.log("Connected to 'books.db' database");
        db.collection('books',{strict:true},function(err,collection){
            if(err){
                console.log("The 'books' collection doesn't exist"); 
                populateDB();
            }
        }
       );
    }
    /*

    //grid fs 
    var fileId = new ObjectID();    
    var gridStore = new GridStore(db,fileId,'rango.jpg' ,'w');
    gridStore.open(function(err,gridStore){
        gridStore.writeFile('~/Downlods/rango.jpg',function(err,result){
            if(err){
                console.log("Write error");
            }
            else{
                console.log("Write succesul");
            }
        });
    });

    //var filesize = fs.statSync('~/Downloads/rango.jpg').size;
    //var data = fs.readFileSync('~/Downloads/rango.jpg');
    //var fd = fs.openSync('~/Downloads/rango.jpg','r',0666);

   /* gridStore.open(function(err,gridStore){ 
                gridStore.writeFile(fd,function(err,doc){
                    
                        GridStore.read(db,fileId,function(err,fileData){
                                    
                                    assert.equal(data.toString('base64'),fileData.toString('base64') );
                                    assert.equal(filesize,fileData.length);
                            
                             });
                    });
        });*/
    
});


exports.findAll = function(req,res){ 
    db.collection('books',function(err,collection){ 
            collection.find().toArray(function(err,items){
                        res.send(items);
                })
        });
}



exports.findByTitle = function(req,res){
    var query  = decodeURIComponent(req.query.q) ; 
    console.log('Retrieving title:'+query) ;
    /* 
    db.collection('books',function(err,collection){
            collection.find({'title':query}).toArray(function(err,items){
                res.send(items) ; 
                console.log('Items in Book:'+items);
            });
        });*/
   
     db.ensureIndex("books",{
                             title:"text",
                             author:"text",
                             publication:"text",
                             categories:"text"

              },function(err,indexname){
                       assert.equal(null,err);
                  });

      db.collection('books').find({
            "$text":{
                "$search":query

            }
        },
        {
            author:1,

            title:1,

            _id:1,
            
            publication:1,

            categories:1,

            textScore:{
                $meta:"textScore"
            }
        },
        {
             sort:{
                textScore:{
                    $meta:"textScore"
                }
             }
        }
    
    ).toArray(function(err,items){
        res.send(items);
    });


}


exports.addBook = function(req,res){
    var book = req.body ; 
    console.log('Adding Book:'+JSON.stringify(book));
    db.collection('books',function(err,collection){ 
               collection.insert(book,{safe:true},function(err,result){ 
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


exports.updateBook= function(req,res){
    var id = req.params.id ; 
    var book = req.body ; 
    console.log('Updating book:'+id);
    console.log(JSON.stringify(book));

    db.collection('books',function(err,collection){
        collection.update({'_id':new BSON.ObjectID(id)},book,{safe:true},function(err,result){
            if(err){
                console.log('Error updating book:'+err);
            }
            else{
                console.log(''+result+'document(s) updated');
                res.send(book);
            }

        });
    }
    );
}

exports.deleteBook = function(req,res){
    var id  = req.params.id ; 
    console.log('Deleting wine:'+id);

    db.collection('book',function(err,collection){
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


var populateDB = function(){
    var books = [{
                    title:"C++",
                    author:"Bjarne",
                    publication:"Pearson",
                    categories:["Programming","Academic"],
                         
                 },
                 {
                     title:"java",
                     author:"Swamy",
                     publication:"McGraw Hill",
                     categories:["Programming","Web","GUI","Database"],


                 },
                 {
                       title:"Computer_Networks",
                       author:"Tannenbaum",
                       publication:"Pearson",
                       categories:["Networks","Hardware"], 

                 },
                 {
                        title:"Theory_of_Computation",
                        author:"Sipser",
                        publication:"Oxford",
                        categories:["Compilers","Theory","Mathematics" ],
                             
                 },
                 {
                        title:"Introduction_to_algorithms",
                        author:"CLRS",
                        publication:"PHI",
                        categories:["Algorithms","Mathematics","Theory"],
                 },

                 ];

                 db.collection('books',function(err,collection){
                    collection.insert(books,{safe:true},function(err,result){})
                 });
};
