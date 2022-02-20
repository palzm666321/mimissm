package com.bjpowernode.controller;


import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.service.ProductinfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductinfoAction {

    private static final int PAGE_SIZE = 5;
    //在界面层中，一定会有业务逻辑层的对象
    @Autowired
    ProductinfoService productinfoService;


    public String getAll(HttpServletRequest request){
        List<ProductInfo> list=productinfoService.getAll();

        request.setAttribute("list",list);

        return "product";

    }

    //显示第一页的五条数据
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        //得到第一页的数据
        PageInfo info=productinfoService.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }


    //ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxsplit")
    public void ajaxsplit(int page, HttpSession session){
        //取得当前page参数的页面数据
        PageInfo info=productinfoService.splitPage(page,PAGE_SIZE);
        session.setAttribute("info",info);
    }
}
