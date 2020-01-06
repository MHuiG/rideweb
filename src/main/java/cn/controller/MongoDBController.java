package cn.controller;

import cn.service.MongoDBService;
import cn.service.impl.MongoDBServiceImpl;
import com.mongodb.client.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.annotations.Test;


@Controller
public class MongoDBController {


    @Autowired
    private MongoDBService MongoDBService;

    @RequestMapping(value = "/getMongoDBService", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Test
    public void getMongoDBService() {
        MongoDBServiceImpl service = new MongoDBServiceImpl();
        MongoCollection col = service.connection();
        service.casual_start_data(col);
        System.out.println("--------------------------------");
//        service.casual_end_data(col);
//        System.out.println("--------------------------------");

    }


}
