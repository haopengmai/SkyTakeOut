package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 菜品ids查询套餐Ids
     * @param dishIds
     * @return
     */
    List<Long>getSetmealIdsByDishIds(List<Long> dishIds);
}
