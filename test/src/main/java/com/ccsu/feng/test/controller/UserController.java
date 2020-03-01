package com.ccsu.feng.test.controller;

import com.ccsu.feng.test.entity.AdminUser;
import com.ccsu.feng.test.entity.UserBases;
import com.ccsu.feng.test.entity.vo.UserVO;
import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.exception.BaseException;
import com.ccsu.feng.test.service.user.UserService;
import com.ccsu.feng.test.utils.RedisUtil;
import com.ccsu.feng.test.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author admin
 * @create 2020-02-26-12:26
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @ResponseBody
    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    public Result<Boolean> adminLogin(@Valid @RequestBody AdminUser adminUser, BindingResult bindingResult) throws Exception {
        int a=3/0;
        if (bindingResult.hasErrors()){
             throw  new BaseException("传入参数有误！");
        }
        Boolean userLogin = userService.adminUserLogin(adminUser.getUsername(), adminUser.getPassword());
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


    @ResponseBody
    @RequestMapping(value = "/page/register",method = RequestMethod.POST)
    public  Result<Boolean> pageRegister(@Valid @RequestBody UserVO userVO, BindingResult bindingResult)  {
        if (bindingResult.hasErrors()){
            throw  new BaseException("传入参数有误！");
        }
        Boolean aBoolean = userService.pageUserRegister(userVO);
        if (aBoolean) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/login",method = RequestMethod.POST)
    public  Result<UserBases> pageLogin(String username,String password)  {
        UserBases userBases = userService.pageLogin(username, password);
        if (userBases!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userBases);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/page/smsLogin", method = RequestMethod.POST)
    public  Result<UserBases> smsLogin(String phone,String smsCode)  {
        UserBases userBases = userService.smsLogin(phone, smsCode);
        if (userBases!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), userBases);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/sendPhoneMsg",method = RequestMethod.POST)
    public  Result<Boolean> sendPhoneMsg(String phone)  {
        Boolean aBoolean = userService.sendPhoneMsg(phone);
        if (aBoolean!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/updatePassword",method = RequestMethod.POST)
    public  Result<Boolean> updatePassword(String phone, String password)  {
        Boolean aBoolean = userService.updatePassword(phone,password);
        if (aBoolean!=null) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), aBoolean);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }

    }

    @ResponseBody
    @RequestMapping(value = "/page/smsEditPassword", method = RequestMethod.POST)
    public  Result<Boolean> smsEditPassword(String phone,String smsCode)  {
        Boolean aBoolean = userService.smsEditPassword(phone, smsCode);
        if (aBoolean) {
            return Result.build(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), true);
        } else {
            return Result.error(ResultEnum.REGISTER_FAIL.getCode(),ResultEnum.REGISTER_FAIL.getMsg());
        }
    }



}
