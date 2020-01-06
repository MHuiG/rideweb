package cn.service;

import cn.pojo.Cassandra.Season;

import java.io.IOException;

public interface HBaseService {

    void addSeasonData(Season o) throws IOException;
}