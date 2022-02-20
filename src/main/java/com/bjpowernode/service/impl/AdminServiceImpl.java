package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.AdminExample;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    //在业务逻辑层中，一定有数据访问，访问层的对象
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {

        AdminExample example=new AdminExample();

        example.createCriteria().andANameEqualTo(name);

        List<Admin> list=adminMapper.selectByExample(example);

        if (list.size()>0 && list != null){

            Admin admin=list.get(0);

            String MDpwd= MD5Util.getMD5(pwd);

            if (MDpwd.equals(admin.getaPass())){
                return admin;
            }

        }

        return null;
    }
}
