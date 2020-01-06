package cn.controller;

import cn.pojo.Cassandra.Location;
import cn.service.CassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class CassandraController {


    @Autowired
    private CassandraService CassandraService;

    @RequestMapping(value = "/getLocationAll", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Location> getLocationAll() {
        List<Location> o = CassandraService.getLocationAll();
        return o;
    }

    @RequestMapping(value = "/getLocationByStation", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Location> getLocationByStation(String Station) {
        Location o = new Location();
        o.setStation(Station);
        System.out.println("getLocationByStation " + o.getStation());
        List<Location> s = CassandraService.getLocationByStation(o);
        return s;
    }

    @RequestMapping(value = "/getLocationByTerminal", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Location> getLocationByTerminal(String Terminal) {
        Location o = new Location();
        o.setTerminal(Terminal);
        System.out.println("getLocationByTerminal " + o.getTerminal());
        List<Location> s = CassandraService.getLocationByTerminal(o);
        return s;
    }

    @RequestMapping(value = "/insertLocation", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int insertLocation(String Terminal, String Station, String Latitude, String Longitude, String Nbdocks) {
        Location o = new Location();
        o.setTerminal(Terminal);
        o.setStation(Station);
        o.setLatitude(Latitude);
        o.setLongitude(Longitude);
        o.setNbdocks(Nbdocks);
        System.out.println("insertLocation " + o.getTerminal());
        CassandraService.insertLocation(o);
        return 1;
    }

    @RequestMapping(value = "/deleteLocationByTerminal", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int deleteLocationByTerminal(String Terminal) {
        Location o = new Location();
        o.setTerminal(Terminal);
        CassandraService.deleteLocationByTerminal(o);
        System.out.println("deleteLocationByTerminal " + o.getTerminal());
        return 1;
    }

    /*
    ChangeTime('1/01/2010 00:00')#1262275200
    ChangeTime('1/01/2011 00:00')#1293811200
    ChangeTime('1/01/2012 00:00')#1325347200
    ChangeTime('1/01/2013 00:00')#1356969600
    ChangeTime('1/01/2014 00:00')#1388505600
    ChangeTime('1/01/2015 00:00')#1420041600
    ChangeTime('1/01/2016 00:00')#1451577600
    ChangeTime('1/01/2017 00:00')#1483200000
     */
    @RequestMapping(value = "/main2", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List main2() {
        List list = new ArrayList();
        List Member = new ArrayList();
        Member.add(Integer.parseInt(CassandraService.seacountyear("1262275200", "1293811200", "Member")));
        Member.add(Integer.parseInt(CassandraService.seacountyear("1293811200", "1325347200", "Member")));
        Member.add(Integer.parseInt(CassandraService.seacountyear("1325347200", "1356969600", "Member")));
        Member.add(Integer.parseInt(CassandraService.seacountyear("1356969600", "1388505600", "Member")));
        Member.add(Integer.parseInt(CassandraService.seacountyear("1388505600", "1420041600", "Member")));
        Member.add(Integer.parseInt(CassandraService.seacountyear("1420041600", "1451577600", "Member")));
        Member.add(Integer.parseInt(CassandraService.seacountyear("1451577600", "1483200000", "Member")));
        List Casual = new ArrayList();
        Casual.add(Integer.parseInt(CassandraService.seacountyear("1262275200", "1293811200", "Casual")));
        Casual.add(Integer.parseInt(CassandraService.seacountyear("1293811200", "1325347200", "Casual")));
        Casual.add(Integer.parseInt(CassandraService.seacountyear("1325347200", "1356969600", "Casual")));
        Casual.add(Integer.parseInt(CassandraService.seacountyear("1356969600", "1388505600", "Casual")));
        Casual.add(Integer.parseInt(CassandraService.seacountyear("1388505600", "1420041600", "Casual")));
        Casual.add(Integer.parseInt(CassandraService.seacountyear("1420041600", "1451577600", "Casual")));
        Casual.add(Integer.parseInt(CassandraService.seacountyear("1451577600", "1483200000", "Casual")));
        list.add(Member);
        list.add(Casual);
        return list;
    }

    @RequestMapping(value = "/sum", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List getsum() {
        List list = new ArrayList();
        int SumNbdocks = 0;
        List<Location> o = CassandraService.getLocationAll();
        for (Location i : o) {
            SumNbdocks += Integer.parseInt(i.getNbdocks());
        }
        list.add(SumNbdocks);
        list.add(o.size());
        return list;
    }

    @RequestMapping(value = "/getCassandraService", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getCassandraService() {
        System.out.println("getCassandraService..");
        return 1;
    }


}
