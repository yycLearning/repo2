package com.yycprojects.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yycprojects.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
