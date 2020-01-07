package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.MongoDBService;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.testng.annotations.Test;
import cn.pojo.RatingSumToPageBean;

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
    @Test
    public void test(){
        MongoClient mongo = MongoClients.create("mongodb://worker02:27017");
        MongoDatabase db = mongo.getDatabase("ywh");
        MongoCollection col = db.getCollection("text");
        Document document = new Document("name","张三").append("sex", "男").append("age", 18);//---------------增
        col.insertOne(document);
        Bson filter = Filters.eq("age",18); //------------------删
        //删除与筛选器匹配的单个文档
        col.deleteOne(filter);
        Document document2 = new Document("$set", new Document("age", 100));//------------------------改
        //修改单个文档
        col.updateOne(filter, document2);
        FindIterable findIterable = col.find(); // ------------------------------查
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

        System.out.println("Success!!!");
    }
        public ArrayList casual_start(MongoCollection col){

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
            int i = 0;
            String[] name = new String[3];
            double[] count = new double[3];
            for (Document document : iterable) {
                Object users = document.get("_id");
                Object num = document.get("num");
                RatingSumToPageBean titles = new RatingSumToPageBean();
                name[i] = users.toString();
                count[i] = Double.parseDouble(num.toString());
                i++;
            }
                RatingSumToPageBean title = new RatingSumToPageBean();
                title.setTitle(name);
                String[] a = new String[3];
                a = title.getTitle();
                title.setSum(count);
                double[] b = new double[3];
                b= title.getSum();
                ArrayList a1 = new ArrayList();
                for(i=0;i<3;i++){
                    a1.add(a[i]);
                    a1.add(b[i]);
                }
                return a1;
        }
    public ArrayList member_start_data(MongoCollection col){

        BasicDBObject query = new BasicDBObject(); //-----------------------------会员车+出发站+时间
        query.append("Account type", "Member");
        BasicDBObject match = new BasicDBObject("$match", query);
        BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$Start station").append("num", new BasicDBObject("$sum", "$Total duration (ms)"))); //出发站点
        //BasicDBObject sum = new BasicDBObject("sum","$Total duration (ms)");
        BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("num", -1));
        BasicDBObject limit = new BasicDBObject("$limit", 4);
        List<DBObject> queryList = new ArrayList<>();
        queryList.add(match);
        queryList.add(group);
        queryList.add(sort);
        queryList.add(limit);
        AggregateIterable<Document> iterable = col.aggregate(queryList);
        int i = 0;
        double[] count = new double[4];
        for (Document document : iterable) {
            Object num = document.get("num");
            RatingSumToPageBean titles = new RatingSumToPageBean();
            count[i] = Double.parseDouble(num.toString());
            i++;
        }
        RatingSumToPageBean title = new RatingSumToPageBean();

        title.setSum(count);
        double[] b = new double[4];
        b= title.getSum();
        ArrayList a1 = new ArrayList();
        for(i=0;i<4;i++){
            a1.add(b[i]);
        }
        return a1;
    }
    public ArrayList casual_start_data(MongoCollection col){

        BasicDBObject query = new BasicDBObject(); //-----------------------------会员车+出发站+时间
        query.append("Account type", "Casual");
        BasicDBObject match = new BasicDBObject("$match", query);
        BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id", "$Start station").append("num", new BasicDBObject("$sum", "$Total duration (ms)"))); //出发站点
        //BasicDBObject sum = new BasicDBObject("sum","$Total duration (ms)");
        BasicDBObject sort = new BasicDBObject("$sort", new BasicDBObject("num", -1));
        BasicDBObject limit = new BasicDBObject("$limit", 4);
        List<DBObject> queryList = new ArrayList<>();
        queryList.add(match);
        queryList.add(group);
        queryList.add(sort);
        queryList.add(limit);
        AggregateIterable<Document> iterable = col.aggregate(queryList);
        int i = 0;
        double[] count = new double[4];
        for (Document document : iterable) {
            Object num = document.get("num");
            RatingSumToPageBean titles = new RatingSumToPageBean();
            count[i] = Double.parseDouble(num.toString());
            i++;
        }
        RatingSumToPageBean title = new RatingSumToPageBean();

        title.setSum(count);
        double[] b = new double[4];
        b= title.getSum();
        ArrayList a1 = new ArrayList();
        for(i=0;i<4;i++){
            a1.add(b[i]);
        }
        return a1;
    }
        public ArrayList casual_end(MongoCollection col){
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
            int i = 0;
            String[] name = new String[3];
            double[] count = new double[3];
            for (Document document : iterable) {
                Object users = document.get("_id");
                Object num = document.get("num");
                RatingSumToPageBean titles = new RatingSumToPageBean();
                name[i] = users.toString();
                count[i] = Double.parseDouble(num.toString());
                i++;
            }

            RatingSumToPageBean title = new RatingSumToPageBean();
            title.setTitle(name);
            String[] a = new String[3];
            a = title.getTitle();
            title.setSum(count);
            double[] b = new double[3];
            b= title.getSum();
            ArrayList a1 = new ArrayList();
            for(i=0;i<3;i++){
                a1.add(a[i]);
                a1.add(b[i]);
            }
            return a1;
        }

    }
