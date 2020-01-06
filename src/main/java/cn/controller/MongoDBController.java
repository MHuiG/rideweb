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

    @RequestMapping(value = "/getMongoDBService", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Test
    public List getMongoDBService() {
        MongoDBServiceImpl service = new MongoDBServiceImpl();
        MongoCollection col = service.connection();
        RatingSumToPageBean title = new RatingSumToPageBean();
        ArrayList a1 = new ArrayList();
        ArrayList b1 = new ArrayList();
        ArrayList c1 = new ArrayList();
        a1 = service.casual_start_data(col);
        b1 = service.casual_end_data(col);
        double one = (double) a1.get(3) + (double) b1.get(1);
        double two = (double) a1.get(1) + (double) b1.get(3);
        double three = (double) a1.get(5) + (double) b1.get(5);
        c1.add(a1.get(2));
        c1.add(one);
        c1.add(a1.get(0));
        c1.add(two);
        c1.add(a1.get(4));
        c1.add(three);
        System.out.println(c1);
        return c1;
    }


}
