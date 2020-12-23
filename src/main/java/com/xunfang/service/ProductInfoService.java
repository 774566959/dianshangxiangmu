package com.xunfang.service;

import com.xunfang.pojo.Pager;
import com.xunfang.pojo.ProductInfo;

import java.util.List;
import java.util.Map;

public interface ProductInfoService {
    //    分页
    public List<ProductInfo> selectByPage(Pager pager, ProductInfo productInfo);

    //    总数
    public Integer count(Map<String,Object> params);

    // 添加商品
    public void addProductInfo(ProductInfo productInfo);

    //修改商品
    public void updateProductInfo(ProductInfo productInfo);

    //下架商品
    public void deleteProductInfo(String ids,int flag);

    //在售商品
    public List<ProductInfo> selectByStatus();

    //id获取商品
    public ProductInfo selectByid(int id);
}
