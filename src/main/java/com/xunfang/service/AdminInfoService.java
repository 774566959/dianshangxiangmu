package com.xunfang.service;

import com.xunfang.pojo.AdminInfo;

import java.util.List;
import java.util.Map;

public interface AdminInfoService {
	// 登录验证
	public AdminInfo login(AdminInfo ai);

	// 根据管理员编号，获取管理员对象及关联的功能权限
	public AdminInfo getAdminInfoAndFunctions(Integer id);

	//添加用户
	public void addUser(AdminInfo adminInfo);

	//添加权限
	public void addpowers(int aid,int fid);

	//判断名字是否为空
	public int selectname(String name);

	//用户分页查询
	public List<AdminInfo> selectByPage(Map<String,Object> params);

	//查询结果总数
	public Integer count(Map<String,Object> params);

	//删除用户
	public int deleAdmin(String id);

}
