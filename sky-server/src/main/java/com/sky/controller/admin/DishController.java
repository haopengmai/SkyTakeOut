package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.mapper.DishMapper;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.EmployeeService;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result register(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询")
    public Result page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("新增菜品：{}", dishPageQueryDTO);
        PageResult pageResult =dishService.pageQuery(dishPageQueryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation(value = "菜品批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("新增菜品：{}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "菜品回显")
    public Result<DishVO> get(@PathVariable Long id)
    {
        log.info("菜品回显：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation(value = "菜品修改")
    public Result get(@RequestBody DishDTO dishDTO)
    {
        log.info("菜品修改：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改套餐")
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }
}
