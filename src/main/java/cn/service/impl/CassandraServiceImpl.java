package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.Location;
import cn.service.CassandraService;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class CassandraServiceImpl implements CassandraService {

    @Autowired
    private Mapper mapper;

    public Session session;

    public void connectDB() {
        Cluster culster = Cluster.builder().withClusterName("Test Cluster").addContactPoint("worker01").build();
        session = culster.connect();
    }

    @Override
    public void insertLocation(Location o) {
        connectDB();
        String cql = "INSERT INTO mydb.loca (Terminal,Station,Latitude,Longitude,Nbdocks) VALUES (" + o.getTerminal() + "," + o.getStation() + "," + o.getLatitude() + "," + o.getLongitude() + "," + o.getNbdocks() + ");";
        session.execute(cql);
    }


    @Override
    public void deleteByTerminal(Location o) {
        connectDB();
        String cql = "DELETE FROM mydb.loca WHERE Terminal='" + o.getTerminal() + "';";
        session.execute(cql);
    }

    @Override
    public List<Location> getLocationAll() {
        connectDB();
        List<Location> s = new ArrayList<>();
        String cql = "SELECT * FROM mydb.loca;";
        ResultSet resultSet = session.execute(cql);
        for (Row row : resultSet) {
            Location o = new Location();
            o.setTerminal(row.getString("Terminal"));
            o.setStation(row.getString("Station"));
            o.setLatitude(row.getString("Latitude"));
            o.setLongitude(row.getString("Longitude"));
            o.setNbdocks(row.getString("Nbdocks"));
            s.add(o);
        }
        return s;
    }

    @Test
    public void main() {
//        connectDB(session);
        List<Location> o = getLocationAll();
        System.out.println(o.toString());
    }
}
