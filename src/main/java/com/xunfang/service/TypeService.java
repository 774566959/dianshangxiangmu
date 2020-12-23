package com.xunfang.service;

import com.xunfang.pojo.Type;

import java.util.List;

public interface TypeService {
    // 获取所示商品类型
    public List<Type> getAll();
    //添加商品类型
    public int addType(Type type);
    //修改商品类型
    public void update(Type type);
    //删除商品类型
    public int deleType(String ids);
}
