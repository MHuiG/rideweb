package cn.controller;

import cn.service.MySQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MySQLController {


    @Autowired
    private MySQLService MySQLService;

    @RequestMapping(value = "/getRatingSum", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getRatingSum() {
        System.out.println("getRatingSum..");
        String data = MySQLService.getRatingSum(5);
        return data;
    }


}
