package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.DishFlavorMapper;
import com.yycprojects.Service.DishFlavorService;
import com.yycprojects.entity.DishFlavor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
