package cn.controller;

import cn.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MongoDBController {


    @Autowired
    private MongoDBService MongoDBService;

    @RequestMapping(value = "/getMongoDBService", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getMongoDBService() {
        System.out.println("getMongoDBService..");
        return 1;
    }


}
