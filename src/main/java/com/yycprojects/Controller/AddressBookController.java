package com.yycprojects.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.yycprojects.Service.AddressBookService;
import com.yycprojects.common.BaseContext;
import com.yycprojects.common.R;
import com.yycprojects.entity.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.LambdaMetafactory;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentID());
        log.info("addressBook:{}",addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);

    }
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        log.info("addressBoos:{}",addressBook);
        LambdaUpdateWrapper<AddressBook> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentID());
        updateWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(updateWrapper);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);

    }
    @GetMapping("/{id}")
    public R get(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        if(addressBook!=null){
            return R.success(addressBook);
        }else{
            return R.error("no specific data");
        }
    }
    @GetMapping("default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentID());
        queryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getById(queryWrapper);
        if(null==addressBook){
            return R.error("No specific data");
        }else{
            return R.success(addressBook);
        }
    }
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentID());
        log.info("addressBoos:{}",addressBook);
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(null!=addressBook.getUserId(),AddressBook::getUserId,addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        return R.success(addressBookService.list(queryWrapper));
    }
}
