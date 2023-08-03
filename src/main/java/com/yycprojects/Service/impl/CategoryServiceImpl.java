package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.CategoryMapper;
import com.yycprojects.Service.CategoryService;
import com.yycprojects.Service.DishService;
import com.yycprojects.Service.SetmealService;
import com.yycprojects.common.CustomerException;
import com.yycprojects.entity.Category;
import com.yycprojects.entity.Dish;
import com.yycprojects.entity.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper= new LambdaQueryWrapper<Dish>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count>0){
            throw new CustomerException("current category has related with other dish");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if(count1>0){
            throw new CustomerException("current category has related with other setmeal");
        }
        super.removeById(id);
    }
}
