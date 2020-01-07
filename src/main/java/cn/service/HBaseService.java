package cn.service;

import cn.pojo.Cassandra.Season;

import java.io.IOException;
import java.util.List;

public interface HBaseService {

    void addSeasonData(Season o) throws IOException;

    void deleteRow(String... rows) throws IOException;

    List<Season> getData(Season s) throws IOException;

    List<Season> queryTable() throws IOException;
}