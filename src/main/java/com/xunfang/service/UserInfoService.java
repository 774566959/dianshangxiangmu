package com.xunfang.service;

import com.xunfang.pojo.Pager;
import com.xunfang.pojo.ProductInfo;
import com.xunfang.pojo.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoService {
    //id获取客户
    public  UserInfo selectById(int id);
    //status获取合法客户
    public  List<UserInfo> selectByStatus();
    //分页显示证书
    public List<UserInfo> selectByPage(Pager pager,UserInfo userInfo);
    //客户总数
    public Integer count(Map<String,Object> params);
    //修改状态
    public void updateStatus(String uids,int flag);
    //添加客户
    public void addUser(UserInfo userInfo);
    //修改信息
    public void updateuser(UserInfo userInfo);
}
