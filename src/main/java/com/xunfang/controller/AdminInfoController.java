package com.xunfang.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunfang.pojo.*;
import com.xunfang.service.UserInfoService;
import com.xunfang.util.TreeBuild;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

//import com.xunfang.pojo.TreeNode;
import com.xunfang.service.AdminInfoService;
//import com.xunfang.util.JsonFactory;

@SessionAttributes(value = { "admin" })
@Controller
@RequestMapping("/admininfo")
public class AdminInfoController {
	@Autowired
	private AdminInfoService adminInfoService;
	@Autowired
	private UserInfoService userInfoService;

	@RequestMapping(value = "/login", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login(AdminInfo ai, ModelMap model) throws JsonProcessingException {

		// 后台登录验证
		AdminInfo admininfo = adminInfoService.login(ai);
		if (admininfo != null && admininfo.getName() != null) {
			// 验证通过后，再判断是否已为该管理员分配功能权限
			if (adminInfoService.getAdminInfoAndFunctions(admininfo.getId()).getFs().size() > 0) {
				// 验证通过且已分配功能权限，则将admininfo对象存入model中
				model.put("admin", admininfo);
				// 以JSON格式向页面发送成功信息
//				return "{\"success\":\"true\",\"message\":\"登录成功\"}";
//				拓展 返回 json格式的字符串 数据
				Map map = new HashMap();
				map.put("success","true");
				map.put("message","登录成功");
				String jsonString = new ObjectMapper().writeValueAsString(map);
				System.out.println(jsonString);
				return jsonString;
			} else {
				return "{\"success\":\"false\",\"message\":\"您没有权限，请联系超级管理员设置权限！\"}";
			}
		} else
			return "{\"success\":\"false\",\"message\":\"登录失败\"}";
	}

@RequestMapping("getTree")
@ResponseBody
public List<TreeNode> getTree(@RequestParam(value = "adminid") String adminid) {
	// 根据管理员编号，获取AdminInfo对象
	AdminInfo admininfo = adminInfoService.getAdminInfoAndFunctions(Integer.parseInt(adminid));
	List<TreeNode> nodes = new ArrayList<TreeNode>();
	// 获取关联的Functions对象集合
	List<Functions> functionsList = admininfo.getFs();
	// 对List<Functions>类型的Functions对象集合排序
	//Collections.sort(functionsList);
	// 将排序后的Functions对象集合转换到List<TreeNode>类型的列表nodes
	for (Functions functions : functionsList) {
		TreeNode treeNode = new TreeNode();
		treeNode.setId(functions.getId());
		treeNode.setFid(functions.getParentid());
		treeNode.setText(functions.getName());
		nodes.add(treeNode);
	}
	// 调用自定义的工具类JsonFactory的buildtree方法，为nodes列表中的各个TreeNode元素中的
	// children属性赋值(该节点包含的子节点)
	List<TreeNode> treeNodes = TreeBuild.buildtree(nodes, 0);
	return treeNodes;
}

	// 退出
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public String logout(SessionStatus status) {
		// @SessionAttributes清除
		status.setComplete();
		return "{\"success\":\"true\",\"message\":\"注销成功\"}";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> list(Integer page, Integer rows, AdminInfo adminInfo){
//        条件map
		Map<String,Object> params = new HashMap<String, Object>();
//        初始化一个 pager 对象
		Pager pager = new Pager();
		pager.setCurPage(page);
		pager.setPerPageRows(rows);

//        存入 页面信息
		params.put("pager",pager);
//         存入 条件信息
		params.put("adminInfo",adminInfo);
//      获取 总数
		int totalCount = adminInfoService.count(params);
//        获取  所有数据
		List<AdminInfo> adminInfos = adminInfoService.selectByPage(params);
//        返回结果 map
		Map<String,Object> result = new HashMap<String, Object>();
//        固定 传 total  rows
		result.put("total",totalCount);
		result.put("rows",adminInfos);
		return result;
	}

	@RequestMapping(value = "/addAdmin", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public  String addAdmin(@Param("str1") String str1, @Param("str2") String str2, String name, String pwd) throws JsonProcessingException {
		if (name==null||"".equals(name)){
			return "{\"success\":\"false\",\"message\":\"添加失败，请输入用户名\"}";
		}
		else{
			if (pwd==null||"".equals(pwd)){
				return "{\"success\":\"false\",\"message\":\"添加失败，请输入密码\"}";
			}
			else {
				if (adminInfoService.selectname(name)!=0){
					return "{\"success\":\"false\",\"message\":\"添加失败，用户名已存在\"}";
				}
				else {
					String str3 = str1+str2;
					str3=str3.replace("][",",");
					str3=str3.replace("[","");
					str3=str3.replace("]","");
					String[] str4=str3.split(",");
					AdminInfo adminInfo = new AdminInfo();
					adminInfo.setName(name);
					adminInfo.setPwd(pwd);
					adminInfoService.addUser(adminInfo);
					UserInfo userInfo = new UserInfo();
					userInfo.setUserName(name);
					userInfo.setPassword(pwd);
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date(System.currentTimeMillis());
					userInfo.setRegDate(simpleDateFormat.format(date));
					userInfo.setStatus(1);
					userInfoService.addUser(userInfo);
					int[] array = Arrays.stream(str4).mapToInt(Integer::parseInt).toArray();
					for (int i:array) {
						adminInfoService.addpowers(adminInfo.getId(),i);
					}
					return "{\"success\":\"true\",\"message\":\"添加成功\"}";
				}
			}
		}
	}

	//删除用户
	@RequestMapping(value = "/deleAdmin", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String deleAdmin(String ids){
		System.out.println(ids.substring(0,ids.length()-1));
		try {
			adminInfoService.deleAdmin(ids.substring(0,ids.length()-1));
			return "{\"success\":\"true\",\"message\":\"操作成功\"}";
		}catch (Exception e){
			System.out.println(e);
			return "{\"success\":\"false\",\"message\":\"操作失败\"}";
		}
	}


}
