package com.yycprojects.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> EXPHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        if(ex.getMessage().contains("Duplicate entry")){
            String[] spilt = ex.getMessage().split(" ");
            String msg = spilt[2]+"already exists";
            return R.error(msg);
        }
        return R.error("Unknow Error");
    }
    @ExceptionHandler(CustomerException.class)
    public R<String> EXPHandle(CustomerException ex){
        log.error(ex.getMessage());

        return R.error(ex.getMessage());
    }
}
