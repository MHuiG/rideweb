package cn.controller;

import cn.pojo.Cassandra.Location;
import cn.pojo.Cassandra.Sea;
import cn.pojo.Cassandra.Season;
import cn.service.HBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;


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
    //插入更新
    @RequestMapping(value = "/getinsertHBase", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getinsertHBase(String ID, String StartDate, String StartStation, String StartStationNumber, String EndDate,String EndStation,String EndStationNumber, String TotalDuration, String AccountType) throws IOException {
        Season o = new Season();
        o.setId(ID);
        o.setStartDate(StartDate);
        o.setStartStation(StartStation);
        o.setStartStationNumber(StartStationNumber);
        o.setEndDate(EndDate);
        o.setEndStation(EndStation);
        o.setEndStationNumber(EndStationNumber);
        o.setTotalDuration(TotalDuration);
        o.setAccountType(AccountType);
        System.out.println("insertLocation " + o.getId());
        HBaseService.addSeasonData(o);
        return 1;
    }
//删除
    @RequestMapping(value = "/getdeleteHBase", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getdeleteHBase(String ID) throws IOException {

        HBaseService.deleteRow(ID);
        System.out.println("deleterowByID " +ID);
        return 1;
    }

//    根据ID查
    @RequestMapping(value = "/getHBaseDatabyID", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List getHBaseDatabyID(String ID) throws IOException {
        Season o = new Season();
        o.setId(ID);
        List<Season> p =HBaseService.getData(o);
        System.out.println("getHBaseDatabyID " + o.getId());
        return p;
    }

//    查整表
    @RequestMapping(value = "/getHBaseAll", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Season> getHBaseAll() throws IOException {
        List<Season> o =HBaseService.queryTable();
        return o;
    }

}
