package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.Cassandra.Location;
import cn.pojo.Cassandra.Sea;
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

    /**
     * 创建键空间
     */
    @Test
    public void createKeyspace() {
        connectDB();
        /**单数据中心 复制策略 ：1**/
        String cql = "CREATE KEYSPACE if not exists mydb WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}";
        session.execute(cql);
    }

    /**
     * 创建表loca
     */
    @Test
    public void createTableloca() {
        connectDB();
        String cql = "CREATE TABLE if not exists mydb.loca (Terminal text,Station text,Latitude text,Longitude text,Nbdocks text,PRIMARY KEY (Terminal))";
        session.execute(cql);
    }

    /**
     * 导入数据
     */

    /*
    COPY mydb.loca(Terminal,Station,Latitude,Longitude,Nbdocks) FROM '/export/data/ride/locations/Locations' WITH HEADER = false;
    */

    /**
     * 创建表season
     */
    @Test
    public void createTableseason() {
        connectDB();
        String cql = "CREATE TABLE if not exists mydb.season (id text,StartDate text,StartStation text,StartStationNumber text,EndDate text,EndStation text,EndStationNumber text,TotalDuration text,AccountType text,PRIMARY KEY (id))";
        session.execute(cql);
    }

    /**
     * 导入数据
     */

    /*
    COPY mydb.season(id,StartDate,StartStation,StartStationNumber,EndDate,EndStation,EndStationNumber,TotalDuration,AccountType) FROM '/export/data/ride/seasons' WITH HEADER = false;
    */

    /**
     * 创建表sea
     */
    @Test
    public void createTablesea() {
        connectDB();
        String cql = "CREATE TABLE if not exists mydb.sea (id text,StartDate text,StartStation text,StartStationNumber text,EndDate text,EndStation text,EndStationNumber text,TotalDuration text,AccountType text,StartDateTemp int,EndDateTemp int,PRIMARY KEY (id))";
        session.execute(cql);
    }

    /**
     * 导入数据
     */

    /*
    COPY mydb.sea(id,StartDate,StartStation,StartStationNumber,EndDate,EndStation,EndStationNumber,TotalDuration,AccountType,StartDateTemp,EndDateTemp) FROM '/export/data/ride/seasons1' WITH HEADER = false;
    */

    /**
     * 插入/更新Location
     */
    @Override
    public void insertLocation(Location o) {
        connectDB();
        String cql = "INSERT INTO mydb.loca (Terminal,Station,Latitude,Longitude,Nbdocks) VALUES ('" + o.getTerminal() + "','" + o.getStation() + "','" + o.getLatitude() + "','" + o.getLongitude() + "','" + o.getNbdocks() + "');";
        session.execute(cql);
    }

    /**
     * 删除Location
     */

    @Override
    public void deleteLocationByTerminal(Location o) {
        connectDB();
        String cql = "DELETE FROM mydb.loca WHERE Terminal='" + o.getTerminal() + "';";
        session.execute(cql);
    }

    /**
     * 查询Location
     */
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

    /**
     * 查询LocationByStation
     */
    @Override
    public List<Location> getLocationByStation(Location location) {
        connectDB();
        List<Location> s = new ArrayList<>();
        String cql = "SELECT * FROM mydb.loca where Station='" + location.getStation() + "'allow filtering;";
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

    /**
     * 查询LocationByTerminal
     */
    @Override
    public List<Location> getLocationByTerminal(Location location) {
        connectDB();
        List<Location> s = new ArrayList<>();
        String cql = "SELECT * FROM mydb.loca where Terminal='" + location.getTerminal() + "'allow filtering;";
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

    public String seacountyear(String y1, String y2, String AccountType) {
        connectDB();
        String c = "0";
        List<Location> s = new ArrayList<>();
        String cql = "select count(*) from mydb.sea WHERE StartDateTemp > " + y1 + " and  StartDateTemp < " + y2 + " and AccountType='" + AccountType + "'   ALLOW FILTERING;";
        ResultSet resultSet = session.execute(cql);
        for (Row row : resultSet) {
            c = row.getToken(0).toString();
            System.out.println(c);
        }
        return c;

    }
/*
    public String id;
    public String StartDate;
    public String StartStation;
    public String StartStationNumber;
    public String EndDate;
    public String EndStation;
    public String EndStationNumber;
    public String TotalDuration;
    public String AccountType;
    public int StartDateTemp;
    public int EndDateTemp;
 */

    /**
     * 查询Sea
     */
    @Override
    public List<Sea> getSeaAll() {
        connectDB();
        List<Sea> s = new ArrayList<>();
        String cql = "SELECT * FROM mydb.sea;";
        ResultSet resultSet = session.execute(cql);
        for (Row row : resultSet) {
            Sea o = new Sea();
            o.setId(row.getString("id"));
            o.setStartDate(row.getString("StartDate"));
            o.setStartStation(row.getString("StartStation"));
            o.setStartStationNumber(row.getString("StartStationNumber"));
            o.setEndDate(row.getString("EndDate"));
            o.setEndStation(row.getString("EndStation"));
            o.setEndStationNumber(row.getString("EndStationNumber"));
            o.setTotalDuration(row.getString("TotalDuration"));
            o.setAccountType(row.getString("AccountType"));
            o.setStartDateTemp(row.getInt("StartDateTemp"));
            o.setEndDateTemp(row.getInt("EndDateTemp"));
            s.add(o);
        }
        return s;
    }

    @Test
    public void main() {
        connectDB();
        List<Location> s = new ArrayList<>();
        String cql = "select count(*) from mydb.sea WHERE StartDateTemp > 1262275200 and  StartDateTemp < 1293811200 and AccountType='Casual'   ALLOW FILTERING;";
        ResultSet resultSet = session.execute(cql);
        for (Row row : resultSet) {
            System.out.println(row.getToken(0));
        }

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
}

