package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.MongoDBService;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

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
        MongoCollection col = db.getCollection("season");

        BasicDBObject searchQuery = new BasicDBObject();
        DBObject matchBasicDBObjet = new BasicDBObject("$match",new BasicDBObject("Account type","Casual"));
        DBObject groupFields = new BasicDBObject("_id",new BasicDBObject("Start station","$Start station"));
        groupFields.put("num",new BasicDBObject("$sum",1));
        DBObject group = new BasicDBObject("$group", groupFields);
        DBObject sortchBasicDBObjet = new BasicDBObject("$sort", new BasicDBObject("count",-1));
        DBObject limitBasicDBObjet = new BasicDBObject("$limit",3);
        List<DBObject> list = new ArrayList<DBObject>();
        list.add(matchBasicDBObjet);
        list.add(group);
        list.add(sortchBasicDBObjet);
        list.add(limitBasicDBObjet);
        System.out.println(col.aggregate(list).toString());

//      System.out.println("success!!!");
    }







}
