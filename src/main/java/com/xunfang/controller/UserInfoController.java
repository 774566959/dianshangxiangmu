package com.xunfang.controller;

import com.xunfang.pojo.Pager;
import com.xunfang.pojo.ProductInfo;
import com.xunfang.pojo.UserInfo;
import com.xunfang.service.UserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/userinfo")
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;

    //获取合法客户
    @RequestMapping("/getValidUser")
    @ResponseBody
    public List<UserInfo> getValidUser(){
        List<UserInfo> userInfos = userInfoService.selectByStatus();
        UserInfo userInfo = new UserInfo();
        userInfo.setId(0);
        userInfo.setUserName("请选择");
        userInfos.add(0,userInfo);
        return userInfos;
    }

    //id获取客户

    //    分页查询   datagrid 传两个参数  page，rows   得 两个参数  total  rows
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(Integer page, Integer rows, UserInfo userInfo){
//        条件map
        System.out.println(userInfo.getStatus());
        Map<String,Object> params = new HashMap<String, Object>();
//        初始化一个 pager 对象
        Pager pager = new Pager();
        pager.setCurPage(page);
        pager.setPerPageRows(rows);
//        存入 页面信息
        params.put("pager",pager);
//         存入 条件信息
        params.put("userInfo",userInfo);
//      获取 总数
        int totalCount = userInfoService.count(params);
//        获取  所有数据
        List<UserInfo> userInfos = userInfoService.selectByPage(pager,userInfo);
//        返回结果 map
        Map<String,Object> result = new HashMap<String, Object>();
//        固定 传 total  rows
        result.put("total",totalCount);
        result.put("rows",userInfos);
        return result;
    }

    //状态修改
    @RequestMapping(value = "/setIsEnableUser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateStatus(@Param(value = "uids") String uids, @Param(value = "flag")int flag){
        try {
            userInfoService.updateStatus(uids.substring(0, uids.length() - 1), flag);
            return "{\"success\":\"true\",\"message\":\"操作成功\"}";
        }catch (Exception e){
            System.out.println(e);
            return "{\"success\":\"false\",\"message\":\"操作失败\"}";
        }
    }

    // 修改
    @RequestMapping(value = "/updateuser", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateProduct(UserInfo userInfo) {
        userInfoService.updateuser(userInfo);
        return "{\"success\":\"true\",\"message\":\"修改成功\"}";
    }
}
