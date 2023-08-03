package com.yycprojects.Controller;

import com.yycprojects.Service.OrderService;
import com.yycprojects.common.R;
import com.yycprojects.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order){
        log.info("order detail:{}",order);
        orderService.submit(order);
        return R.success("Ordered");
    }
}
