package com.xunfang.service.impl;

import com.xunfang.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xunfang.dao.AdminInfoDao;
import com.xunfang.pojo.AdminInfo;
import com.xunfang.service.AdminInfoService;

import java.util.List;
import java.util.Map;

@Service("adminInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)//立即挂载
public class AdminInfoServiceImpl implements AdminInfoService {

	@Autowired
	private AdminInfoDao adminInfoDao;
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public AdminInfo login(AdminInfo ai) {
		return adminInfoDao.selectByNameAndPwd(ai);
	}

	@Override
	public AdminInfo getAdminInfoAndFunctions(Integer id) {
		return adminInfoDao.selectById(id);
	}

	@Override
	public void addUser(AdminInfo adminInfo) {
		adminInfoDao.addUser(adminInfo);
	}


	@Override
	public void addpowers(int aid, int fid) {
		adminInfoDao.addpowers(aid,fid);
	}

	@Override
	public int selectname(String name) {
		return adminInfoDao.selectname(name);
	}

	@Override
	public List<AdminInfo> selectByPage(Map<String,Object> params) {
		return adminInfoDao.selectByPage(params);
	}

	@Override
	public Integer count(Map<String, Object> params) {
		return adminInfoDao.count(params);
	}

	@Override
	public int deleAdmin(String id) {
		userInfoDao.deleuser(id);
		adminInfoDao.delePow(id);
		return adminInfoDao.deleType(id);
	}

}
