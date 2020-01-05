package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.MongoDBService;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.MongoCollection;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.gridfs.*;
import com.mongodb.client.gridfs.model.*;
import org.testng.annotations.Test;

@org.springframework.stereotype.Service
public class MongoDBServiceImpl implements MongoDBService {

    @Autowired
    private Mapper mapper;
    @Test
    public void main(){
        MongoClient mongo = MongoClients.create("mongodb://worker02:27017");
        MongoDatabase db = mongo.getDatabase("ywh");
        MongoCollection col = db.getCollection("location");

        MongoCursor<Document> cursor = col.find().iterator();
        System.out.println("success!!!");
    }






}
