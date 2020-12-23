package com.xunfang.dao.provider;

import com.xunfang.pojo.AdminInfo;
import com.xunfang.pojo.ProductInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class AdminInfoDynaSqlProvider {
    //生成分页动态查询语句
    public String selectWithParam(final Map<String,Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("admin_info");
                if (params.get("adminInfo")!=null){
                    AdminInfo adminInfo = (AdminInfo) params.get("adminInfo");
//                    判断是否有 具体的 条件
                    if (adminInfo.getName()!=null&&!adminInfo.getName().equals("")){
                        WHERE(" name = #{adminInfo.name} ");
                    }
                }
            }
        }.toString();
        if (params.get("pager")!=null){
            sql+=" limit #{pager.firstLimitParam},#{pager.perPageRows} ";
        }
        return sql;
    }
    public String count(final Map<String,Object> params){
        String sql = new SQL(){
            {
                SELECT("count(*)");
                FROM("admin_info");
//                判断是否有条件
                if (params.get("adminInfo")!=null){
                    AdminInfo adminInfo = (AdminInfo) params.get("adminInfo");
//                    判断是否有 具体的 条件
                    if (adminInfo.getName()!=null&&!adminInfo.getName().equals("")){
                        WHERE(" name = #{adminInfo.name} ");
                    }
                }
            }
        }.toString();
        return sql;
    }
}
