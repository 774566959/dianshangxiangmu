package com.xunfang.dao;

import com.xunfang.dao.provider.OrderInfoDynaSqlProvider;
import com.xunfang.dao.provider.ProductInfoDynaSqlProvider;
import com.xunfang.pojo.OrderDetail;
import com.xunfang.pojo.OrderInfo;
import com.xunfang.pojo.ProductInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

public interface OrderInfoDao {
    //添加订单
    @Insert("insert into order_info(uid,status,ordertime,orderprice)"+
    " values(#{uid},#{status},#{ordertime},#{orderprice})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int saveOrder(OrderInfo orderInfo);

    //添加订单详情
    @Insert("insert into order_detail(oid,pid,num) values(#{oid},#{pid},#{num}) ")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int saveOrderDetail(OrderDetail orderDetail);

    /* 订单列表 */
    @Results({
            @Result(column = "uid",property = "ui",one = @One(select = "com.xunfang.dao.UserInfoDao.selectById",fetchType = FetchType.EAGER))
    })
    @SelectProvider(type = OrderInfoDynaSqlProvider.class,method = "selectWithParam")
    public List<OrderInfo> selectByPage(Map<String,Object> params);

    // 动态条件获取总数
    @SelectProvider(type = OrderInfoDynaSqlProvider.class,method = "count")
    public Integer count(Map<String,Object> params);

    //   根据订单id 获取 订单明细
    @Results({
            @Result(column = "pid",property = "pi",one = @One(select = "com.xunfang.dao.ProductInfoDao.selectById",fetchType = FetchType.EAGER))
    })
    @Select("select * from order_detail where oid = #{oid}")
    public List<OrderDetail> getDetailByOrderId(int oid);

    //    通过 id 获取 订单信息
    @Results({
            @Result(column = "uid",property = "ui",one = @One(select = "com.xunfang.dao.UserInfoDao.selectById",fetchType = FetchType.EAGER))
    })
    @Select("select * from order_info where id = #{id}")
    public OrderInfo getOrderInfoById(int id);

    //    非Param注解  只能使用#   有Param #，$ 都能用
//    通过订单 id  删除 订单
    @Delete("delete from order_info where id in(#{ids})")
    public int deleteOrder(@Param("ids") String ids);
    //    通过订单 id  删除 订单明细
    @Delete("delete from order_detail where oid in(#{oids})")
    public int deleteOrderDetail(@Param("oids") String oids);

}
