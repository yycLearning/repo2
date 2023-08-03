package com.yycprojects.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yycprojects.entity.Orders;

public interface OrderService extends IService<Orders> {
    public void submit(Orders orders);
}
