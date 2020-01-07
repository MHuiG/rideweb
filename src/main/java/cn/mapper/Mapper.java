package cn.mapper;

import cn.pojo.mysql.Rideyear;
import cn.pojo.mysql.yearpeople;

import java.util.List;

public interface Mapper {
    List<Rideyear> selectrideyear();
    List<yearpeople> selectpp();

}
