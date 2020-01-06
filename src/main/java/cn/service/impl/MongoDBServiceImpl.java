package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.RatingSumToPageBean;
import cn.service.MongoDBService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList casual_start_data(MongoCollection col) {

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
        b = title.getSum();
        ArrayList a1 = new ArrayList();
        for (i = 0; i < 3; i++) {
            a1.add(a[i]);
            a1.add(b[i]);
        }
        return a1;


    }

    public ArrayList casual_end_data(MongoCollection col) {
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
        b = title.getSum();
        ArrayList a1 = new ArrayList();
        for (i = 0; i < 3; i++) {
            a1.add(a[i]);
            a1.add(b[i]);
        }
        return a1;
    }

}
