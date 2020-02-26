package com.ccsu.feng.test.service.user.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccsu.feng.test.dao.AdminUserMapper;
import com.ccsu.feng.test.entity.AdminUser;
import com.ccsu.feng.test.service.user.AdminUserService;
import com.ccsu.feng.test.utils.EncryptUtil;
import com.ccsu.feng.test.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

/**
 * @author admin
 * @create 2020-02-26-12:45
 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    private final  static  String PASSWORD_KEY="feng";
    private final  static  String AdMIN_USER_KEY="admin_user_key";
    @Autowired
    AdminUserMapper adminUserMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public Boolean adminUserLogin(String username, String password) {
        String decrptPassword = EncryptUtil.getInstance().MD5(password,PASSWORD_KEY);
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("username",username)
                     .eq("password",decrptPassword);
        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);
        if (adminUser==null) {
            return false;
        }
        redisUtil.hset(AdMIN_USER_KEY,"adminUser",adminUser,60*60*24*7);
        return true;
    }
}
