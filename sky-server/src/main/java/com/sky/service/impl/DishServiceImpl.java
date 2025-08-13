package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Override
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 向dish表插入一条数据
        dishMapper.insert(dish);

        // 获取插入的菜品的id
        Long dishId = dish.getId();

        // 获取前端传来的口味数组
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            // 为每一行口味设置对应菜品id
            for (DishFlavor flavor :
                    flavors) {
                flavor.setDishId(dishId);
            }
            dishMapper.insertBatch(flavors);
        }
    }
}
