package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class MongoDBServiceImpl implements MongoDBService {

    @Autowired
    private Mapper mapper;


}
