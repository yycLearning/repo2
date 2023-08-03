package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.OrderMapper;
import com.yycprojects.Service.*;
import com.yycprojects.common.BaseContext;
import com.yycprojects.common.CustomerException;
import com.yycprojects.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Override
    @Transactional
    public void submit(Orders orders) {
        Long id = BaseContext.getCurrentID();
        orders.setUserId(id);
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,id);
        List<ShoppingCart> cartList = shoppingCartService.list(queryWrapper);
        if(cartList==null||cartList.size()==0){
            throw new CustomerException("Cart is empty");
        }
        User user = userService.getById(id);

        AddressBook address = addressBookService.getById(orders.getAddressBookId());
        if(address==null){
            throw new CustomerException("No address info");
        }
        long orderId = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetails=  cartList.stream().map((item)->{
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setNumber(item.getNumber());
                orderDetail.setDishFlavor(item.getDishFlavor());
                orderDetail.setDishId(item.getDishId());
                orderDetail.setSetmealId(item.getSetmealId());
                orderDetail.setName(item.getName());
                orderDetail.setImage(item.getImage());
                orderDetail.setAmount(item.getAmount());
                amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
                return orderDetail;
        }).collect(Collectors.toList());
        orders.setNumber(String.valueOf(orderId));
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserName(user.getName());

        this.save(orders);
        orderDetailService.saveBatch(orderDetails);
        shoppingCartService.remove(queryWrapper);
    }
}
