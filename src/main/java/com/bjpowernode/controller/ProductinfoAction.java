package com.bjpowernode.controller;


import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.service.ProductinfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductinfoAction {

    private static final int PAGE_SIZE = 5;

    String saveFileName="";

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

    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){

        //提取生成文件名UUID+上传tu图片的后缀.jpg .png
        saveFileName= FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储的路径
        String path= request.getServletContext().getRealPath("/image_big");
        //转存
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端JSON对象，封装图片的路径，为了在页面实现立即回显
        JSONObject object=new JSONObject();
        object.put("imgurl",saveFileName);

        return object.toString();
    }


    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){

        info.setpImage(saveFileName);
        info.setpDate(new Date());
        //info对象中有表单提交上来的五个数据，有异步ajax上来的图片名称数据，有上架时间的数据
        int num=-1;
        try {
            num=productinfoService.save(info);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (num>0){
            request.setAttribute("msg","增加成功！");
        }else{
            request.setAttribute("msg","增加失败！");
        }
        //增加成功后应该重新访问数据库，所以跳转到分页显示的action上
        return "forward:/prod/split.action";
    }


}
