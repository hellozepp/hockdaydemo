package com.daojia.hockday.mapper;

import com.daojia.hockday.entity.UserInfo;

/**
 * @author Dawei 2018/11/17
 */
public interface UserInfoMapper {

    UserInfo getUserByMd5Key(String md5Key);

    Integer insertSelective(UserInfo userInfo);


}
