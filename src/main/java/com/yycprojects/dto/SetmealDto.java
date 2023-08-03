package com.yycprojects.dto;


import com.yycprojects.entity.Setmeal;
import com.yycprojects.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
