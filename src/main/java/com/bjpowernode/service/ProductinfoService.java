package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductinfoService {


    List<ProductInfo> getAll();

    PageInfo splitPage(int pageNum,int pageSize);


    int save(ProductInfo info);
}
