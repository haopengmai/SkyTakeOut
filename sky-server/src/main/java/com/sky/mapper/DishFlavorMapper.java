package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据dishId去删除口味
     * @param dishid
     */
    @Delete("delete from sky_take_out.dish_flavor where dish_id = #{dishid}")
    void deleteBydishId(Long dishid);


    void deleteBydishIds(List<Long> ids);

    @Select("select * from sky_take_out.dish_flavor where dish_id = #{id}")
    List<DishFlavor> getFlavorsByDishId(Long id);
}
