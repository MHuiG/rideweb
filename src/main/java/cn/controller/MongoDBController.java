package cn.controller;

import cn.pojo.RatingSumToPageBean;
import cn.service.MongoDBService;
import cn.service.impl.MongoDBServiceImpl;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MongoDBController {


    @Autowired
    private MongoDBService MongoDBService;

    @RequestMapping(value = "/getMongoDBService")
    @ResponseBody
    @Test
    public List getMongoDBService() {
        MongoDBServiceImpl service = new MongoDBServiceImpl();
        MongoCollection col = service.connection();
        RatingSumToPageBean title = new RatingSumToPageBean();
        ArrayList a1 = new ArrayList();
        ArrayList b1 = new ArrayList();
        ArrayList c1 = new ArrayList();
        a1 = service.casual_start(col);
        b1 = service.casual_end(col);
        double one = (double) a1.get(3) + (double) b1.get(1);
        double two = (double) a1.get(1) + (double) b1.get(3);
        double three = (double) a1.get(5) + (double) b1.get(5);
        c1.add(a1.get(2));
        c1.add(one);
        c1.add(a1.get(0));
        c1.add(two);
        c1.add(a1.get(4));
        c1.add(three);
        System.out.println(c1.get(0));
        return c1;
    }
    @RequestMapping(value = "/getList")
    @ResponseBody
    public List getList(){
        MongoDBServiceImpl service = new MongoDBServiceImpl();
        MongoCollection col = service.connection();
        RatingSumToPageBean title = new RatingSumToPageBean();
        ArrayList a1 = new ArrayList();
        ArrayList b1 = new ArrayList();
        ArrayList c1 = new ArrayList();
        a1 = service.member_start_data(col);
        b1 = service.casual_start_data(col);
        for(int i = 0;i<4;i++){
            double tt = 1.0;
            tt = 1.0 + ((double)(a1.get(i))/9000000.0)*1.0;
            int a = new Double(tt).intValue();
            c1.add(a);
        }
        for(int i = 0;i<4;i++){
            double tt = 1.0;
            tt = 1.0 + ((double)(b1.get(i))/9000000.0)*1.0;
            int a = new Double(tt).intValue();
            c1.add(a);
        }

      return c1;
    }

}
