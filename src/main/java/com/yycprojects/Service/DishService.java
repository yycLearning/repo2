package com.yycprojects.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yycprojects.dto.DishDto;
import com.yycprojects.entity.Dish;

public interface DishService  extends IService<Dish> {
    public void saveWithFlavor(DishDto dto);
    public DishDto getByIdWithFlavor(Long id);

   public void updateWithFlavor(DishDto dto);
}
