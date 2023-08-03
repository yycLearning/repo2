package com.yycprojects.Controller;

import com.yycprojects.Service.OrderDetailService;
import com.yycprojects.common.R;
import com.yycprojects.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@Slf4j
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

}
