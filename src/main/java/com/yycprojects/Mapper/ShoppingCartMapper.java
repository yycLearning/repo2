package com.yycprojects.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yycprojects.common.BaseContext;
import com.yycprojects.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
