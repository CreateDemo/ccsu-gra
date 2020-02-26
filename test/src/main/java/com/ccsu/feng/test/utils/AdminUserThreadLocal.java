package com.ccsu.feng.test.utils;

import com.ccsu.feng.test.entity.AdminUser;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @create 2020-02-26-13:47
 */
@Component
public class AdminUserThreadLocal {
    //线程本地变量
    private static  ThreadLocal<AdminUser> users = new ThreadLocal<>();

    public AdminUser  getUser() {
        return users.get();
    }

    public  void setUser(AdminUser user) {
        users.set(user);
    }

    public  void clear(){
        users.remove();
    }
}
