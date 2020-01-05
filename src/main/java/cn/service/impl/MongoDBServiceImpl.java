package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.MongoDBService;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
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
        BasicDBObject query= new BasicDBObject();
        query.append("Account type","Casual");
        BasicDBObject match = new BasicDBObject("$match", query);
        BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$Start station").append("num", new BasicDBObject("$sum", 1)));
        BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("num", -1));
        BasicDBObject limit = new BasicDBObject("$limit",3);
        List<DBObject> queryList = new ArrayList<>();
        queryList .add(match);
        queryList .add(group);
        queryList .add(sort);
        queryList .add(limit);
        AggregateIterable<Document> iterable =  col.aggregate(queryList);
        for (Document document : iterable){
            Object users = document.get("_id");
            Object num = document.get("num");
            System.out.println(users+":"+num);
        }
    }







}
