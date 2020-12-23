package com.xunfang.dao.provider;

import com.xunfang.pojo.ProductInfo;
import com.xunfang.pojo.UserInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserInfoDynaSqlProvider {
    //    生成 分页 动态  查询 语句
    public String selectWithParam(final Map<String,Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("user_info");
//                判断是否有条件
                if (params.get("userInfo")!=null){
                    UserInfo userInfo = (UserInfo) params.get("userInfo");
//                    判断是否有 具体的 条件
                    if (userInfo.getUserName()!=null&&!userInfo.getUserName().equals("")){
                        WHERE(" userName LIKE CONCAT('%',#{userInfo.userName},'%') ");
                    }
                    if (userInfo.getSex()!=null&&!userInfo.getSex().equals("请选择")){
                        WHERE(" sex = #{userInfo.sex} ");
                    }
                    if (userInfo.getStatus()!=null&&userInfo.getStatus()<2){
                        WHERE(" status=#{userInfo.status} ");
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
                FROM("user_info");
//                判断是否有条件
                if (params.get("userInfo")!=null){
                    UserInfo userInfo = (UserInfo) params.get("userInfo");
//                    判断是否有 具体的 条件
                    if (userInfo.getUserName()!=null&&!userInfo.getUserName().equals("")){
                        WHERE(" userName LIKE CONCAT('%',#{userInfo.userName},'%') ");
                    }
                }
            }
        }.toString();
    }
}
