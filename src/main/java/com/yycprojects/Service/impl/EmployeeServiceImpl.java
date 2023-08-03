package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.EmployeeMapper;
import com.yycprojects.Service.EmployeeService;
import com.yycprojects.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
