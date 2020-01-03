package cn.controller;

import cn.service.HBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HBaseController {


    @Autowired
    private HBaseService HBaseService;

    @RequestMapping(value = "/getHBaseService", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getHBaseService() {
        System.out.println("getHBaseService..");
        return 1;
    }


}
