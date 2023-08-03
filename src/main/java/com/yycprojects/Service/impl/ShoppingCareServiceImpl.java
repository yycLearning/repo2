package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.ShoppingCartMapper;
import com.yycprojects.Service.ShoppingCartService;
import com.yycprojects.entity.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCareServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
