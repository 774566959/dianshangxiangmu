package com.xunfang.service.impl;

import com.xunfang.dao.UserInfoDao;
import com.xunfang.pojo.Pager;
import com.xunfang.pojo.UserInfo;
import com.xunfang.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)//立即挂载
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;

    @Override
    public UserInfo selectById(int id) {
        return userInfoDao.selectById(id);
    }

    @Override
    public List<UserInfo> selectByStatus() {
        return userInfoDao.selectByStatus();
    }

    @Override
    public List<UserInfo> selectByPage(Pager pager, UserInfo userInfo) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userInfo",userInfo);
        int recordcount = userInfoDao.count(map);
        if (recordcount>0){
            map.put("pager",pager);
        }
        return userInfoDao.selectByPage(map);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        return userInfoDao.count(params);
    }

    @Override
    public void updateStatus(String uids,int flag) {
        userInfoDao.updatestatus(uids,flag);
    }

    @Override
    public void addUser(UserInfo userInfo) { userInfoDao.addUser(userInfo); }

    @Override
    public void updateuser(UserInfo userInfo) { userInfoDao.updateuser(userInfo);
    }

}
