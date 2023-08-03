package com.yycprojects.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yycprojects.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper  extends BaseMapper<Category> {
}
