package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService
{
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    /**
     * 新增菜品及其对应的口味
     * @param dishDTO
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        //向菜品表插入1条数据
        dishMapper.insert(dish);

        //获取刚刚插入的id
        Long dishId = dish.getId();

        //向口味表插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size()>0)
        {
            for (DishFlavor flavor : flavors)
            {
                flavor.setDishId(dishId);
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO)
    {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        //判断当前菜品是否为起售菜品？起售菜品无法删除
        //先查询当前菜品的status
        for (Long id:ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE))
            {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否被套餐关联了？被关联了无法删除
        List<Long> setmealIdsByDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIdsByDishIds.size()>0)
        {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品表中的菜品数据
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteBydishIds(ids);
    }

    /**
     * 通过id来查dish和flavor
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        DishVO dishVO = new DishVO();
        Dish dish = dishMapper.getById(id);
        BeanUtils.copyProperties(dish,dishVO);
        List<DishFlavor> flavors = dishFlavorMapper.getFlavorsByDishId(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    /**
     * 通过dishDTO来更改 dish 和flavor
     * @param dishDTO
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO)
    {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!= null && flavors.size()>0)
        {
            dishFlavorMapper.deleteBydishId(dishDTO.getId());
            dishFlavorMapper.insertBatch(flavors);
        }
    }
}
