package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.Cassandra.Location;
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
     * 创建表locations
     */
    @Test
    public void createTablelocations() {
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
     * 创建表seasons
     */
    @Test
    public void createTableseasons() {
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
     * 插入Location
     */
    @Override
    public void insertLocation(Location o) {
        connectDB();
        String cql = "INSERT INTO mydb.loca (Terminal,Station,Latitude,Longitude,Nbdocks) VALUES (" + "," + o.getTerminal() + "," + o.getStation() + "," + o.getLatitude() + "," + o.getLongitude() + "," + o.getNbdocks() + ");";
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

    @Test
    public void main() {
//        connectDB(session);
        List<Location> o = getLocationAll();
        System.out.println(o.toString());
    }
}
