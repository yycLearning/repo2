package com.yycprojects.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yycprojects.Service.CategoryService;
import com.yycprojects.common.R;
import com.yycprojects.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("Successful");
    }
    @GetMapping("/page")
    public R<Page> getPage(int page,int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);

    }
    @DeleteMapping
    public R<String> delete(Long id){
//        categoryService.removeById(id);
        categoryService.remove(id);
        return R.success("Successfully deleted");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("Modify category info:{}",category);
        categoryService.updateById(category);
        return R.success("Done");
    }
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);

    }
 }
