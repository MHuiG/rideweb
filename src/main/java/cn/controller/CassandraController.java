package cn.controller;

import cn.pojo.Cassandra.Location;
import cn.service.CassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/insertLocation", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int insertLocation(String Terminal, String Station, String Latitude, String Longitude, String Nbdocks) {
        Location o = new Location();
        o.setTerminal(Terminal);
        o.setStation(Station);
        o.setLatitude(Latitude);
        o.setLongitude(Longitude);
        o.setNbdocks(Nbdocks);
        CassandraService.insertLocation(o);
        return 1;
    }

    @RequestMapping(value = "/deleteLocationByTerminal", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int deleteLocationByTerminal(String Terminal) {
        Location o = new Location();
        o.setTerminal(Terminal);
        CassandraService.deleteLocationByTerminal(o);
        return 1;
    }

    @RequestMapping(value = "/getCassandraService", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getCassandraService() {
        System.out.println("getCassandraService..");
        return 1;
    }


}
