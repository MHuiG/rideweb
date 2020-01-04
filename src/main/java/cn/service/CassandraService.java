package cn.service;

import cn.pojo.Location;
import com.datastax.driver.core.Session;

public interface CassandraService {
    void connectcass(Session session);

    void insertLocation(Session session, Location o);

    void deletecass(Session session);

    void querycass(Session session);

}