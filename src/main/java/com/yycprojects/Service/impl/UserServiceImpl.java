package com.yycprojects.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yycprojects.Mapper.UserMapper;
import com.yycprojects.Service.UserService;
import com.yycprojects.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
