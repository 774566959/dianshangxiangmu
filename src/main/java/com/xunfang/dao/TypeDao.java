package com.xunfang.dao;

import com.xunfang.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TypeDao {
    // 查询所有商品类型
    @Select("select * from type")
    public List<Type> findAll();
    //添加商品类型
    @Insert("insert into type(name) values (#{name})")
    //添加完整数据后将主键生成的id返回给type对象
    @Options(useGeneratedKeys =  true, keyProperty = "id")
    public int insertType(Type type);
    //修改商品类型
    @Update("update type set name=#{name} where id = #{id}")
    public int updateType(Type type);
    //根据id查商品类型
    @Select("select * from type where id = #{id}")
    public Type selectById(Type type);
    //删除商品类别
    @Delete("delete from type where id in (${ids})")
    public int deleType(@Param("ids")String ids);
}
