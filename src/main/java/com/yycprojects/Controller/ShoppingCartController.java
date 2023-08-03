package com.yycprojects.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yycprojects.Service.ShoppingCartService;
import com.yycprojects.common.BaseContext;
import com.yycprojects.common.R;
import com.yycprojects.entity.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("shoppingCart:{}",shoppingCart);
        Long currentID = BaseContext.getCurrentID();
        shoppingCart.setUserId(currentID);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentID);
        if(shoppingCart.getDishId()!=null){
            queryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else{
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);
        if(cartServiceOne!=null){
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number+1);
            shoppingCartService.updateById(cartServiceOne);
        }else{
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne );
    }
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        Long id = BaseContext.getCurrentID();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,id);
        queryWrapper.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> result = shoppingCartService.list(queryWrapper);
        return R.success(result);
    }
    @DeleteMapping("/clean")
    public R<String> clean(){
        Long id = BaseContext.getCurrentID();
        LambdaQueryWrapper<ShoppingCart> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,id);
        shoppingCartService.remove(queryWrapper);
        return R.success("deleted all");
    }
}
