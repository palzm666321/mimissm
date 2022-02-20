package com.bjpowernode.service;

import com.bjpowernode.pojo.Admin;
import org.springframework.stereotype.Service;


public interface AdminService {
    /**
     * 完成登陆判断
     *
     * @return
     */
    Admin login(String name, String password);
}
