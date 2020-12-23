package com.xunfang.dao.provider;

import com.xunfang.pojo.OrderInfo;
import com.xunfang.pojo.ProductInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OrderInfoDynaSqlProvider {
    //    生成 分页 动态  查询 语句
    public String selectWithParam(final Map<String,Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("order_info");
//                判断是否有条件
                if (params.get("orderInfo")!=null){
                    OrderInfo orderInfo = (OrderInfo) params.get("orderInfo");
//                    判断是否有 具体的 条件
                    if (orderInfo.getId()!=null&&orderInfo.getId()>0){
                        WHERE(" id = #{orderInfo.id} ");
                    }
                    else{
                        if (orderInfo.getUid()>0){
                            WHERE(" uid=#{orderInfo.uid} ");
                        }
                        if (orderInfo.getStatus()!=null&&!orderInfo.getStatus().equals("请选择")){
                            WHERE(" status=#{orderInfo.status} ");
                        }
                        if (orderInfo.getOrderTimeFrom()!=null&&!orderInfo.getOrderTimeFrom().equals("")){
                            WHERE(" ordertime>=#{orderInfo.orderTimeFrom} ");
                        }
                        if (orderInfo.getOrderTimeTo()!=null&&!"".equals(orderInfo.getOrderTimeTo())){
                            WHERE(" ordertime<=#{orderInfo.orderTimeTo} ");
                        }
                    }
                }

            }
        }.toString();
        if (params.get("pager")!=null){
            sql+=" limit #{pager.firstLimitParam},#{pager.perPageRows} ";
        }
        return sql;
    }

    //    根据条件动态查询 总数
    public String count(final Map<String,Object> params){
        return  new SQL(){
            {
                SELECT("count(*)");
                FROM("order_info");
//                判断是否有条件
                if (params.get("orderInfo")!=null){
                    OrderInfo orderInfo = (OrderInfo) params.get("orderInfo");
//                    判断是否有 具体的 条件
                    if (orderInfo.getId()!=null&&!"".equals(orderInfo.getId())){
                        WHERE(" id = #{orderInfo.id} ");
                    }
                    else{
                        if (orderInfo.getUid()>0){
                            WHERE(" uid=#{orderInfo.uid} ");
                        }
                        if (orderInfo.getStatus()!=null&&orderInfo.getStatus().equals("请选择")){
                            WHERE(" status=#{orderInfo.status} ");
                        }
                        if (orderInfo.getOrderTimeFrom()!=null&&!orderInfo.getOrderTimeFrom().equals("")){
                            WHERE(" ordertime>=#{orderInfo.orderTimeFrom} ");
                        }
                        if (orderInfo.getOrderTimeTo()!=null&&!orderInfo.getOrderTimeTo().equals("")){
                            WHERE(" ordertime<#{orderInfo.orderTimeTo} ");
                        }
                    }
                }

            }
        }.toString();
    }
}
