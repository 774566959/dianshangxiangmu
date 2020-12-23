package com.xunfang.controller;

import com.xunfang.pojo.Pager;
import com.xunfang.pojo.ProductInfo;
import com.xunfang.service.ProductInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/productinfo")
public class ProductInfoController {
    @Autowired
    ProductInfoService productInfoService;

    //    分页查询   datagrid 传两个参数  page，rows   得 两个参数  total  rows
    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(Integer page, Integer rows, ProductInfo productInfo){
//        条件map
        Map<String,Object> params = new HashMap<String, Object>();
//        初始化一个 pager 对象
        Pager pager = new Pager();
        pager.setCurPage(page);
        pager.setPerPageRows(rows);
//        存入 页面信息
        params.put("pager",pager);
//         存入 条件信息
        params.put("productInfo",productInfo);
//      获取 总数
        int totalCount = productInfoService.count(params);
//        获取  所有数据
        List<ProductInfo> productInfos = productInfoService.selectByPage(pager,productInfo);
//        返回结果 map
        Map<String,Object> result = new HashMap<String, Object>();
//        固定 传 total  rows
        result.put("total",totalCount);
        result.put("rows",productInfos);
        return result;
    }

    // 添加商品
    @RequestMapping(value = "/addProduct", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addProduct(ProductInfo pi, @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) {
        //判断所上传文件是否存在
        if(!file.isEmpty()) {
            // 获取上传文件的原始名称
            String fileName = file.getOriginalFilename();
            // 设置上传文件的保存地址目录
            String path = request.getSession().getServletContext().getRealPath("upload");
            //UUID随机生成字符即文件名放覆盖处理 通过日期生成对应的文件夹
            String uuidName = UUID.randomUUID().toString().substring(0,10);
            int index = fileName.lastIndexOf('.');
            StringBuffer buffer = new StringBuffer(fileName);
            buffer.insert(index,uuidName);
            fileName = buffer.toString();

            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            fileName = format.format(date)+"/"+fileName;
            // 实例化一个File对象，表示目标文件（含物理路径）
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            try {
                // 使用multipartfile接口的方法完成文件上传到指定位置
                file.transferTo(targetFile);
                pi.setPic(fileName);
                productInfoService.addProductInfo(pi);
                return "{\"success\":\"true\",\"message\":\"商品添加成功\"}";
            } catch (Exception e) {
                System.out.println(e);
                return "{\"success\":\"false\",\"message\":\"商品添加失败\"}";
            }
        }
        else
            return "{\"success\":\"false\",\"message\":\"你加图片啊\"}";
    }

    // 修改商品
    @RequestMapping(value = "/updateProduct", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateProduct(ProductInfo pi, @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) {
        //判断所上传文件是否存在
        if(!file.isEmpty()) {
            // 获取上传文件的原始名称
            String fileName = file.getOriginalFilename();
            // 设置上传文件的保存地址目录
            String path = request.getSession().getServletContext().getRealPath("upload");
            //UUID随机生成字符即文件名放覆盖处理 通过日期生成对应的文件夹
            String uuidName = UUID.randomUUID().toString().substring(0,10);
            int index = fileName.lastIndexOf('.');
            StringBuffer buffer = new StringBuffer(fileName);
            buffer.insert(index,uuidName);
            fileName = buffer.toString();

            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            fileName = format.format(date)+"/"+fileName;
            // 实例化一个File对象，表示目标文件（含物理路径）
            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            try {
                // 使用multipartfile接口的方法完成文件上传到指定位置
                file.transferTo(targetFile);
                pi.setPic(fileName);
                productInfoService.updateProductInfo(pi);
                return "{\"success\":\"true\",\"message\":\"商品修改成功\"}";
            } catch (Exception e) {
                System.out.println(e);
                return "{\"success\":\"false\",\"message\":\"商品修改失败\"}";
            }
        }
        else
            return "{\"success\":\"false\",\"message\":\"你加图片啊\"}";
    }

    //上下架商品
    @RequestMapping(value = "/deleteProduct", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleteProduct(@Param(value = "id") String id,@Param(value = "flag")int flag){
        try {
            productInfoService.deleteProductInfo(id.substring(0, id.length() - 1), flag);
            return "{\"success\":\"true\",\"message\":\"操作成功\"}";
        }catch (Exception e){
            System.out.println(e);
            return "{\"success\":\"false\",\"message\":\"操作失败\"}";
        }
    }

    //   获取在售商品   getOnSaleProduct
    @RequestMapping("/getOnSaleProduct")
    @ResponseBody
    public List<ProductInfo> getOnSaleProduct(){
        List<ProductInfo> productInfos = productInfoService.selectByStatus();
        return productInfos;
    }

    //    通过id 获取 商品信息(价格)   getPriceById
    @RequestMapping("/getPriceById")
    @ResponseBody
    public String getPriceById(@RequestParam("pid") String pid){
        ProductInfo productInfo = productInfoService.selectByid(Integer.parseInt(pid));
        return productInfo.getPrice()+"";
    }
}
