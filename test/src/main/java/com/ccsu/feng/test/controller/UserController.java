package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.entity.AdminUser;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.service.user.AdminUserService;
import com.ccsu.feng.test.utils.RedisUtil;
import com.ccsu.feng.test.utils.Result;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author admin
 * @create 2020-02-26-12:26
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping("/admin/login")
    public Result<Boolean> adminLogin(@Valid @RequestBody AdminUser adminUser, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()){
             throw  new Exception("传入参数有误！");
        }
        Boolean userLogin = adminUserService.adminUserLogin(adminUser.getUsername(), adminUser.getPassword());
        if (userLogin) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userLogin);
        } else {
            return Result.error(ResultEnum.ADMIN_NOT_USER.getCode(),ResultEnum.ADMIN_NOT_USER.getMsg());
        }
    }

    @RequestMapping("/admin/loginOut")
    public String adminLoginOut(){
        redisUtil.hdel("admin_user_key","adminUser");
        return "redirect:/admin/login";
    }

}
