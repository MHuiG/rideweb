package cn.service.impl;

import cn.mapper.Mapper;
import cn.service.HBaseService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class HBaseServiceImpl implements HBaseService {

    @Autowired
    private Mapper mapper;


}
