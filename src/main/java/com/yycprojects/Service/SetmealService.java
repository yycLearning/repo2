package com.yycprojects.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yycprojects.dto.DishDto;
import com.yycprojects.dto.SetmealDto;
import com.yycprojects.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto dot);
    public void removeWithDish(List<Long> ids);
}
