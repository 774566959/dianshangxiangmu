package com.xunfang.service;

import com.xunfang.pojo.OrderDetail;
import com.xunfang.pojo.OrderInfo;
import com.xunfang.pojo.Pager;
import com.xunfang.pojo.ProductInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

public interface OrderInfoService {
    //    添加订单
    public int saveOrder(OrderInfo orderInfo);
    //    添加订单详情
    public int saveOrderDetail(OrderDetail orderDetail);
    //    分页
    public List<OrderInfo> selectByPage(Pager pager, OrderInfo orderInfo);
    //    总数
    public Integer count(Map<String,Object> params);
    //   根据订单id 获取 订单明细
    public List<OrderDetail> getDetailByOrderId(int oid);
    //    通过 id 获取 订单信息
    public OrderInfo getOrderInfoById(int id);
    //    删除 订单 和 订单明细
    public int delete(String ids);
}
