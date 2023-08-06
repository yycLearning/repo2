package com.yycprojects.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yycprojects.Service.CategoryService;
import com.yycprojects.Service.SetmealDishService;
import com.yycprojects.Service.SetmealService;
import com.yycprojects.common.R;
import com.yycprojects.dto.SetmealDto;
import com.yycprojects.entity.Category;
import com.yycprojects.entity.Setmeal;
import com.yycprojects.entity.SetmealDish;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CacheManager cacheManager;
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto dto){
        setmealService.saveWithDish(dto);
        return R.success("successful");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null, Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto dto = new SetmealDto();
            BeanUtils.copyProperties(item,dto);
            Long categoryId = item.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            if(byId!=null){
                String name1 = byId.getName();
                dto.setCategoryName(name1);
            }
            return dto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("Successful");
    }
    @GetMapping("/list{}")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId+'_'+setmeal.status")
    public R<List<Setmeal>> list(Setmeal setmeal ){
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null!=setmeal.getCategoryId(),Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(null!=setmeal.getStatus(),Setmeal::getStatus,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> result = setmealService.list(queryWrapper);
        return R.success(result);
    }
}
