package com.xunfang.dao;

import com.xunfang.pojo.Functions;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FunctionsDao {
//    根据管理员id 获取功能权限
    @Select("select * from functions where id in (select fid from powers where aid=#{aid})")
    public List<Functions> selectByAdminId(Integer aid);
}
