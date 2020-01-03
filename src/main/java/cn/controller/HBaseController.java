package cn.controller;

import cn.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HBaseController {


    @Autowired
    private Service Service;

    @RequestMapping(value = "/getRatingSum", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getRatingSum() {
        System.out.println("getRatingSum..");
        String data = Service.getRatingSum(5);
        return data;
    }


}
