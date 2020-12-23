package com.xunfang.service.impl;

import com.xunfang.dao.OrderInfoDao;
import com.xunfang.pojo.OrderDetail;
import com.xunfang.pojo.OrderInfo;
import com.xunfang.pojo.Pager;
import com.xunfang.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Override
    public int saveOrder(OrderInfo orderInfo) {
        try{
            orderInfoDao.saveOrder(orderInfo);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int saveOrderDetail(OrderDetail orderDetail) {
        return orderInfoDao.saveOrderDetail(orderDetail);
    }

    @Override
    public List<OrderInfo> selectByPage(Pager pager, OrderInfo orderInfo) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("orderInfo",orderInfo);
        int recordcount = orderInfoDao.count(map);
        if (recordcount>0){
            map.put("pager",pager);
        }
        return orderInfoDao.selectByPage(map);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        return orderInfoDao.count(params);
    }

    @Override
    public List<OrderDetail> getDetailByOrderId(int oid) {
        return orderInfoDao.getDetailByOrderId(oid);
    }

    @Override
    public OrderInfo getOrderInfoById(int id) {
        return orderInfoDao.getOrderInfoById(id);
    }

    @Override
    public int delete(String ids) {
        try{
            orderInfoDao.deleteOrderDetail(ids);
            orderInfoDao.deleteOrder(ids);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
