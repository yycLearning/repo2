package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.SetmealDishMapper;
import com.yycprojects.Mapper.SetmealMapper;
import com.yycprojects.Service.SetmealDishService;
import com.yycprojects.entity.SetmealDish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
