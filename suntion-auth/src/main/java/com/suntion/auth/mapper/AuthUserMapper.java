package com.suntion.auth.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.suntion.auth.model.AuthUser;

@Mapper
@Repository
public interface AuthUserMapper extends BaseMapper<AuthUser> {

}