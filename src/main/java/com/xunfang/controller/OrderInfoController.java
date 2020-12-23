package com.xunfang.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xunfang.pojo.OrderDetail;
import com.xunfang.pojo.OrderInfo;
import com.xunfang.pojo.Pager;
import com.xunfang.pojo.ProductInfo;
import com.xunfang.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orderinfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;

    //    分页查询   datagrid 传两个参数  page，rows   得 两个参数  total  rows
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(Integer page, Integer rows, OrderInfo orderInfo){
//        条件map
        Map<String,Object> params = new HashMap<String, Object>();
//        初始化一个 pager 对象
        Pager pager = new Pager();
        pager.setCurPage(page);
        pager.setPerPageRows(rows);
//        存入 页面信息
        params.put("pager",pager);
//         存入 条件信息
        params.put("orderInfo",orderInfo);
//      获取 总数
        int totalCount = orderInfoService.count(params);
//        获取  所有数据
        List<OrderInfo> orderInfos = orderInfoService.selectByPage(pager,orderInfo);
//        返回结果 map
        Map<String,Object> result = new HashMap<String, Object>();
//        固定 传 total  rows
        result.put("total",totalCount);
        result.put("rows",orderInfos);
        return result;
    }

//    创建订单 （两表同时 插入数据）commitOrder
    @RequestMapping("/commitOrder")
    @ResponseBody
    public String commitOrder(String inserted,String orderinfo) throws IOException {

        try {
            //        创建 ObjectMapper 对象  可以 将 对象 和 Json字符串  互相转化
            ObjectMapper mapper=new ObjectMapper();
//        orderinfo转化
            OrderInfo oi = mapper.readValue(orderinfo, OrderInfo[].class)[0];
            System.out.println(oi.toString());
//          inserted(集合)转化
            List<OrderDetail> orderDetails = mapper.readValue(inserted,new TypeReference<ArrayList<OrderDetail>>(){});

//        保存oi 同时获取 oi的id
            orderInfoService.saveOrder(oi);
//        遍历集合 将 oi的id 封装
            for (OrderDetail orderDetail : orderDetails) {
                orderDetail.setOid(oi.getId());
//            保存
                orderInfoService.saveOrderDetail(orderDetail);
            }

            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //    通过id 获取 oi  getOrderInfo?oid
    @RequestMapping("/getOrderInfo")
    public String getOrderInfo(Integer oid, ModelMap modelMap){
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(oid);
//        往request区域存入 orderinfo 信息
        modelMap.addAttribute("oi",orderInfo);
//        页面跳转
        return "orderdetail";
    }


    //    获取订单明细
//    getOrderDetails?oid=${requestScope.oi.id }
    @RequestMapping("/getOrderDetails")
    @ResponseBody
    public List<OrderDetail> getOrderDetails(Integer oid){
        System.out.println("oid是"+oid);
        return orderInfoService.getDetailByOrderId(oid);
    }

    //    删除订单
//    deleteOrder
    @ResponseBody
    @RequestMapping("/deleteOrder")
    public Map<String,Object> deleteOrder(String oids){
        System.out.println(oids);
        oids=oids.substring(0,oids.length()-1);
        int resultnum = orderInfoService.delete(oids);
        Map result = new HashMap();
        if (resultnum==1){
            result.put("success","true");
            result.put("message","删除成功");
        }else {
            result.put("success","false");
            result.put("message","删除失败");
        }
        return result;
    }
}
