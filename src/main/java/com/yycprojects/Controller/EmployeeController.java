package com.yycprojects.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yycprojects.Service.EmployeeService;
import com.yycprojects.common.R;
import com.yycprojects.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp==null){
            return R.error("Login Failed");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("Login Failed");
        }
        if(emp.getStatus()==0){
            return R.error("Account Disabled");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("Quitting Safely");
    }
    @PostMapping
    public R<String> save( HttpServletRequest request,@RequestBody Employee employee){
        log.info("Add employee:{}",employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return R.success("Add employee successfully");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        Page pageinfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request ,@RequestBody Employee employee){
        Long id = Thread.currentThread().getId();
        log.info("Thread ID :{}",id);
        employeeService.updateById(employee);
        return R.success("Employee Data Modified Successfully");
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("QueryById");
        Employee employee = employeeService.getById(id);
        if(employee!=null) {
            return R.success(employee);
        }
        return R.error("No Specific Data");
    }
}
