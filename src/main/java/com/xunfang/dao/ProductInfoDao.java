package com.xunfang.dao;

import com.xunfang.dao.provider.ProductInfoDynaSqlProvider;
import com.xunfang.pojo.ProductInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

public interface ProductInfoDao {
    /* 商品列表 */
    @Results({
            @Result(column = "tid",property = "type",one = @One(select = "com.xunfang.dao.TypeDao.selectById",fetchType = FetchType.EAGER))
    })
    @SelectProvider(type = ProductInfoDynaSqlProvider.class,method = "selectWithParam")
    public List<ProductInfo> selectByPage(Map<String,Object> params);

    // 动态条件获取总数
    @SelectProvider(type = ProductInfoDynaSqlProvider.class,method = "count")
    public Integer count(Map<String,Object> params);

    //添加商品
    @Insert("insert into product_info(code,name,tid,brand,pic,num,price,intro,status) "
            + "values(#{code},#{name},#{type.id},#{brand},#{pic},#{num},#{price},#{intro},#{status})")
    public void add(ProductInfo productInfo);

    //修改商品
    @Update("update product_info set code=#{code},name=#{name},tid=#{type.id},brand=#{brand} "
            + ",pic=#{pic},num=#{num},price=#{price},intro=#{intro},status=#{status} where id=#{id}")
    public void updateProduct(ProductInfo productInfo);

    //下架商品
    @Update("update product_info set status=#{flag} where id in (${ids})")
    public int updatestatus(@Param("ids")String ids,@Param("flag")int flag);

    //根据id查商品
    @Select("select * from product_info where id = #{id}")
    public ProductInfo selectById(int id);

    //在售商品
    @Select("select * from product_info where status = 1")
    public  List<ProductInfo> selectByStatus();
}
