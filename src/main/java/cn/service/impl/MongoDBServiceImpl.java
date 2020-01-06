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
@Test
public class MongoDBServiceImpl implements MongoDBService {

    @Autowired
    private Mapper mapper;

    public MongoCollection connection() {

        MongoClient mongo = MongoClients.create("mongodb://worker02:27017");
        MongoDatabase db = mongo.getDatabase("ywh");
        MongoCollection col = db.getCollection("season");
        return col;
    }
        public void casual_start_data (MongoCollection col){
            BasicDBObject query = new BasicDBObject(); //------------临时车+出发站
            query.append("Account type", "Casual");
            BasicDBObject match = new BasicDBObject("$match", query);
            BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$Start station").append("num", new BasicDBObject("$sum", 1))); //出发站点
            BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("num", -1));
            BasicDBObject limit = new BasicDBObject("$limit", 3);
            List<DBObject> queryList = new ArrayList<>();
            queryList.add(match);
            queryList.add(group);
            queryList.add(sort);
            queryList.add(limit);
            AggregateIterable<Document> iterable = col.aggregate(queryList);
            for (Document document : iterable) {
                Object users = document.get("_id");
                Object num = document.get("num");
                System.out.println(users + ":" + num);
            }
        }
        public void casual_end_data (MongoCollection col){
            BasicDBObject query = new BasicDBObject(); //--------------临时车+结束站
            query.append("Account type", "Casual");
            BasicDBObject match = new BasicDBObject("$match", query);
            BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$End station").append("num", new BasicDBObject("$sum", 1))); //结束站点
            BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("num", -1));
            BasicDBObject limit = new BasicDBObject("$limit", 3);
            List<DBObject> queryList = new ArrayList<>();
            queryList.add(match);
            queryList.add(group);
            queryList.add(sort);
            queryList.add(limit);
            AggregateIterable<Document> iterable = col.aggregate(queryList);
            for (Document document : iterable) {
                Object users = document.get("_id");
                Object num = document.get("num");
                System.out.println(users + ":" + num);
            }
        }
        public void member_start_data (MongoCollection col){

            BasicDBObject query = new BasicDBObject(); //--------------------会员车+开始站
            query.append("Account type", "Member");
            BasicDBObject match = new BasicDBObject("$match", query);
            BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$Start station").append("num", new BasicDBObject("$sum", 1))); //开始站点
            BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("num", -1));
            BasicDBObject limit = new BasicDBObject("$limit", 3);
            List<DBObject> queryList = new ArrayList<>();
            queryList.add(match);
            queryList.add(group);
            queryList.add(sort);
            queryList.add(limit);
            AggregateIterable<Document> iterable = col.aggregate(queryList);
            for (Document document : iterable) {
                Object users = document.get("_id");
                Object num = document.get("num");
                System.out.println(users + ":" + num);
            }
        }
        public void member_end_data (MongoCollection col){

            BasicDBObject query = new BasicDBObject(); //--------------------会员车+结束站
            query.append("Account type", "Member");
            BasicDBObject match = new BasicDBObject("$match", query);
            BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$End station").append("num", new BasicDBObject("$sum", 1))); //结束站点
            BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("num", -1));
            BasicDBObject limit = new BasicDBObject("$limit", 3);
            List<DBObject> queryList = new ArrayList<>();
            queryList.add(match);
            queryList.add(group);
            queryList.add(sort);
            queryList.add(limit);
            AggregateIterable<Document> iterable = col.aggregate(queryList);
            for (Document document : iterable) {
                Object users = document.get("_id");
                Object num = document.get("num");
                System.out.println(users + ":" + num);
            }
        }
    }
