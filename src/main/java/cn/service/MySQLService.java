package cn.service;

import cn.pojo.mysql.Rideyear;
import cn.pojo.mysql.yearpeople;

import java.util.List;

public interface MySQLService {

    List<Rideyear> selectrideyear();
    List<yearpeople> selectpp();

}