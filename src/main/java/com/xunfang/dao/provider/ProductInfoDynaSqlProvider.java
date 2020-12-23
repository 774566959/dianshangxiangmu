package com.xunfang.dao.provider;

import com.xunfang.pojo.ProductInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ProductInfoDynaSqlProvider {
    //    生成 分页 动态  查询 语句
    public String selectWithParam(final Map<String,Object> params){
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("product_info");
//                判断是否有条件
                if (params.get("productInfo")!=null){
                    ProductInfo productInfo = (ProductInfo) params.get("productInfo");
//                    判断是否有 具体的 条件
                    if (productInfo.getCode()!=null&&!productInfo.getCode().equals("")){
                        WHERE(" code = #{productInfo.code} ");
                    }
                    if (productInfo.getName()!=null&&!productInfo.getName().equals("")){
                        WHERE(" name like CONCAT('%',#{productInfo.name},'%') ");
                    }
                    if (productInfo.getBrand()!=null&&!productInfo.getBrand().equals("")){
                        WHERE(" brand like CONCAT('%',#{productInfo.brand},'%') ");
                    }
                    if (productInfo.getType()!=null&&productInfo.getType().getId()>0){
                        WHERE(" tid=#{productInfo.type.id} ");
                    }
                    if (productInfo.getPriceFrom()>0){
                        WHERE(" price >= #{productInfo.priceFrom} ");
                    }
                    if (productInfo.getPriceTo()>0){
                        WHERE(" price <= #{productInfo.priceTo} ");
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
                FROM("product_info");
//                判断是否有条件
                if (params.get("productInfo")!=null){
                    ProductInfo productInfo = (ProductInfo) params.get("productInfo");
//                    判断是否有 具体的 条件
                    if (productInfo.getCode()!=null&&!productInfo.getCode().equals("")){
                        WHERE(" code = #{productInfo.code} ");
                    }
                    if (productInfo.getName()!=null&&!productInfo.getName().equals("")){
                        WHERE(" name like CONCAT('%',#{productInfo.name},'%') ");
                    }
                    if (productInfo.getBrand()!=null&&!productInfo.getBrand().equals("")){
                        WHERE(" brand like CONCAT('%',#{productInfo.brand},'%') ");
                    }
                    if (productInfo.getType()!=null&&productInfo.getType().getId()>0){
                        WHERE(" tid=#{productInfo.type.id} ");
                    }
                    if (productInfo.getPriceFrom()>0){
                        WHERE(" price >= #{productInfo.priceFrom} ");
                    }
                    if (productInfo.getPriceTo()>0){
                        WHERE(" price <= #{productInfo.priceTo} ");
                    }
                }
            }
        }.toString();
    }
}