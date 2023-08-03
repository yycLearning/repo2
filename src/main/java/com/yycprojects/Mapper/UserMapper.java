package com.yycprojects.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yycprojects.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
