package com.xunfang.dao;

import com.xunfang.dao.provider.UserInfoDynaSqlProvider;
import com.xunfang.pojo.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

public interface UserInfoDao {
    //id获取客户
    @Select("select * from user_info where id=#{id}")
    public  UserInfo selectById(int id);

    //获取合法客户
    @Select("select * from user_info where status = 1")
    public  List<UserInfo> selectByStatus();

    /* 客户列表 */
    @Results({
            @Result(column = "tid",property = "type",one = @One(select = "com.xunfang.dao.TypeDao.selectById",fetchType = FetchType.EAGER))
    })
    @SelectProvider(type = UserInfoDynaSqlProvider.class,method = "selectWithParam")
    public List<UserInfo> selectByPage(Map<String,Object> params);

    // 动态条件获取客户总数
    @SelectProvider(type = UserInfoDynaSqlProvider.class,method = "count")
    public Integer count(Map<String,Object> params);

    //客户状态改变
    @Update("update user_info set status=#{flag} where id in (${uids})")
    public int updatestatus(@Param("uids")String uids,@Param("flag")int flag);

    //添加客户
    @Insert("insert into user_info(userName,password,regDate,status)" +
            "values(#{userName},#{password},#{regDate},#{status})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public int addUser(UserInfo userInfo);

    //客户信息改变
    @Update("update user_info set userName=#{userName},realName=#{realName},sex=#{sex},address=#{address} "
            + ",email=#{email},regDate=#{regDate},status=#{status} where id=#{id}")
    public void updateuser(UserInfo userInfo);

    //删除用户
    @Delete("delete from user_info where id in (${ids})")
    public int deleuser(@Param("ids") String ids);
}
