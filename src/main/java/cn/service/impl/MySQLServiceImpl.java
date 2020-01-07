package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.mysql.Rideyear;
import cn.pojo.mysql.yearpeople;
import cn.service.MySQLService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class MySQLServiceImpl implements MySQLService {

    @Autowired
    private Mapper mapper;


    @Override
    public List<Rideyear> selectrideyear() {
        List<Rideyear> o = mapper.selectrideyear();
        return o;
    }

    public List<yearpeople> selectpp() {
        List<yearpeople> o = mapper.selectpp();
        return o;
    }
}
