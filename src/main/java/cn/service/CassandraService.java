package cn.service;

import cn.pojo.Cassandra.Location;

import java.util.List;

public interface CassandraService {

    void insertLocation(Location o);

    void deleteLocationByTerminal(Location o);

    List<Location> getLocationAll();

    List<Location> getLocationByStation(Location o);

    List<Location> getLocationByTerminal(Location o);

}