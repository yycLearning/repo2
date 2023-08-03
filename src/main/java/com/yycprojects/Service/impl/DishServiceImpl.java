package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.DishMapper;
import com.yycprojects.Service.DishFlavorService;
import com.yycprojects.Service.DishService;
import com.yycprojects.dto.DishDto;
import com.yycprojects.entity.Dish;
import com.yycprojects.entity.DishFlavor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dto) {
        this.save(dto);
        Long dishId =dto.getId();
        List<DishFlavor> flavors = dto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
        // dishFlavorService.saveBatch(dto.getFlavors());
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish byId = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(byId,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,byId.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dto) {
        this.updateById(dto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dto.getId());
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> flavors = dto.getFlavors();
        flavors = flavors.stream().map((item)->{
            item.setDishId(dto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
