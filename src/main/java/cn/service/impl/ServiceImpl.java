package cn.service.impl;

import cn.mapper.Mapper;
import cn.pojo.RatingSum;
import cn.pojo.RatingSumToPageBean;
import cn.service.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private Mapper mapper;

    @Override
    public String getRatingSum(int top) {
        List<RatingSum> list = mapper.selectRatingSum(top);
        String[] title = new String[top];
        double[] sum = new double[top];
        int i = 0;
        for (RatingSum ratingSum : list) {
            title[i] = ratingSum.getTitle();
            sum[i] = ratingSum.getSum();
            i++;
        }
        RatingSumToPageBean bean = new RatingSumToPageBean();
        bean.setTitle(title);
        bean.setSum(sum);
        //Jackson提供的类，用于把对象转换成Json字符串
        ObjectMapper om = new ObjectMapper();
        String beanJson = null;
        try {
            beanJson = om.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //返回Json格式的对象
        return beanJson;
    }

}
