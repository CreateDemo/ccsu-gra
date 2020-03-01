package com.ccsu.feng.test.service.user.Impl;

import com.alibaba.fastjson.JSONObject;
import com.ccsu.feng.test.dao.UserAuthsMapper;
import com.ccsu.feng.test.dao.UserBasesMapper;
import com.ccsu.feng.test.service.user.ThirdLoginService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author admin
 * @create 2020-02-29-22:16
 */
public class ThirdLoginServiceImpl implements ThirdLoginService {

    @Autowired
    UserBasesMapper userBasesMapper;

    @Autowired
    UserAuthsMapper userAuthsMapper;

    @Override
    public void saveThirdUserInfo(JSONObject jsonObject) {

    }
}
