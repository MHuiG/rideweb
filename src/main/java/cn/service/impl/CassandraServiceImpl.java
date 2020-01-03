package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.CassandraService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class CassandraServiceImpl implements CassandraService {

    @Autowired
    private Mapper mapper;


}
