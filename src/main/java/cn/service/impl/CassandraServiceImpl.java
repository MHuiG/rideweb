package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.Location;
import cn.service.CassandraService;
import com.datastax.driver.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

@org.springframework.stereotype.Service
public class CassandraServiceImpl implements CassandraService {

    @Autowired
    private Mapper mapper;

    public Session session;

    @Test
    public void main() {
        connectcass(session);
    }


    @Override
    public void connectcass(Session session) {
        Cluster culster = Cluster.builder().withClusterName("Test Cluster").addContactPoint("worker01").build();
        session = culster.connect();
    }

    @Override
    public void insertLocation(Session session, Location o) {
        String cql = "INSERT INTO mydb.loca (Terminal,Station,Latitude,Longitude,Nbdocks) VALUES (" + o.getTerminal() + "," + ");";
        session.execute(cql);
    }


    @Override
    public void deletecass(Session session) {
        String cql = "DELETE FROM mydb.test WHERE a='aa';";
        session.execute(cql);
    }

    @Override
    public void querycass(Session session) {
        String cql = "SELECT * FROM mydb.test;";
        String cql2 = "SELECT a,b,c,d FROM mydb.test;";

        ResultSet resultSet = session.execute(cql);
        System.out.print("这里是字段名：");
        for (ColumnDefinitions.Definition definition : resultSet.getColumnDefinitions()) {
            System.out.print(definition.getName() + " ");
        }
        System.out.println();
        System.out.println(String.format("%s\t%s\t%s\t%s\t\n%s", "a", "b", "c", "d",
                "--------------------------------------------------------------------------"));
        for (Row row : resultSet) {
            System.out.println(String.format("%s\t%d\t%s\t%d\t", row.getString("a"), row.getInt("b"),
                    row.getString("c"), row.getInt("d")));
        }
    }


}
