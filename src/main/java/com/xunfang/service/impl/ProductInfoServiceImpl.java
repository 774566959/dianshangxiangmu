package com.xunfang.service.impl;

import com.xunfang.dao.ProductInfoDao;
import com.xunfang.pojo.Pager;
import com.xunfang.pojo.ProductInfo;
import com.xunfang.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productInfoService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)//立即挂载
public class ProductInfoServiceImpl  implements ProductInfoService {
    @Autowired
    ProductInfoDao productInfoDao;


    @Override
    public List<ProductInfo> selectByPage(Pager pager, ProductInfo productInfo) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("productInfo",productInfo);
        int recordcount = productInfoDao.count(map);
        if (recordcount>0){
            map.put("pager",pager);
        }
        return productInfoDao.selectByPage(map);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        return productInfoDao.count(params);
    }

    //添加商品
    @Override
    public void addProductInfo(ProductInfo productInfo) {
         productInfoDao.add(productInfo);
    }

    //修改商品
    @Override
    public void updateProductInfo(ProductInfo productInfo) {
        productInfoDao.updateProduct(productInfo);
    }

    @Override
    public void deleteProductInfo(String ids, int flag) {
        productInfoDao.updatestatus(ids,flag);
    }

    @Override
    public List<ProductInfo> selectByStatus() {
        return productInfoDao.selectByStatus();
    }

    @Override
    public ProductInfo selectByid(int id) {
        return productInfoDao.selectById(id);
    }
}
