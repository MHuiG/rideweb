package cn.service;

import cn.pojo.Location;

import java.util.List;

public interface CassandraService {

    void insertLocation(Location o);

    void deleteByTerminal(Location o);

    List<Location> getLocationAll();

}