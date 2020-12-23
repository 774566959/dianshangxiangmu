package com.xunfang.controller;

import com.xunfang.pojo.Type;
import com.xunfang.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("/getType/{flag}")
    @ResponseBody
    public List<Type> getType(@PathVariable(value = "flag") Integer flag) {
        List<Type> all = typeService.getAll();
        if (flag == 1) {
           Type type = new Type();
           type.setId(0);
           type.setName("请选择商品类型");
           all.add(0,type);
        }
        return all;
    }

    @RequestMapping(value ="/addType",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addType(Type type) {
        int i = typeService.addType(type);
        if (i > 0) {
            return "{\"success\":\"true\",\"message\":\"添加成功\"}";
        } else
            return "{\"success\":\"false\",\"message\":\"添加失败\"}";
    }

    @RequestMapping(value ="/updateType",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateType(Type type) {
        try {
            typeService.update(type);
            return "{\"success\":\"true\",\"message\":\"修改成功\"}";
        } catch (Exception e) {
            System.out.println(e);
            return "{\"success\":\"false\",\"message\":\"修改失败\"}";
        }
    }

    //删除商品类别
    @RequestMapping(value = "/deleType", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deleType(String ids){
        try {
            typeService.deleType(ids.substring(0,ids.length()-1));
            return "{\"success\":\"true\",\"message\":\"删除成功\"}";
        }catch (Exception e){
            System.out.println(e);
            return "{\"success\":\"false\",\"message\":\"删除失败\"}";
        }
    }
}
