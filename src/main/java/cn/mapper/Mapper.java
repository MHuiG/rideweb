package cn.mapper;

import cn.pojo.RatingSum;

import java.util.List;

public interface Mapper {
    List<RatingSum> selectRatingSum(int top);

}
