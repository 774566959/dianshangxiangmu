package com.xunfang.dao;

import com.xunfang.dao.provider.AdminInfoDynaSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import com.xunfang.pojo.AdminInfo;

import java.util.List;
import java.util.Map;

public interface AdminInfoDao {

	// 根据登录名和密码查询管理员
	@Select("select * from admin_info where name = #{name} and pwd = #{pwd}")
	public AdminInfo selectByNameAndPwd(AdminInfo ai);

	// 根据管理员id获取管理员对象及关联的功能集合
	@Select("select * from admin_info where id = #{id}")
	@Results({ @Result(id = true, column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "pwd", property = "pwd"),
			@Result(column = "id", property = "fs",
					many = @Many(select = "com.xunfang.dao.FunctionsDao.selectByAdminId",
							fetchType = FetchType.EAGER)) })
	AdminInfo selectById(Integer id);


	//添加用户
	@Insert("insert into admin_info(name,pwd)values(#{name},#{pwd})")
	@Options(useGeneratedKeys = true,keyProperty = "id")
	public int addUser(AdminInfo adminInfo);

	//添加权限
	@Insert("insert into powers(aid,fid)" +
			"values(#{aid},#{fid})")
	public int addpowers(@Param(("aid"))int aid,@Param(("fid"))int fid);

	//判断用户名是否为空
	@Select("select count(*) from admin_info where name =#{name}")
	public int selectname(String name);

	//    分页条件动态查询
	@SelectProvider(type = AdminInfoDynaSqlProvider.class,method = "selectWithParam")
	public List<AdminInfo> selectByPage(Map<String,Object> params);

	//    动态条件获取总数
	@SelectProvider(type = AdminInfoDynaSqlProvider.class,method = "count")
	public Integer count(Map<String,Object> params);


	//修改用户
	@Update("update  admin_info set name=#{name},pwd=#{pwd} where id=#{id}")
	@Options(useGeneratedKeys = true,keyProperty = "id")
	public int updateProduct(AdminInfo adminInfo);

	//删除用户
	@Delete("delete from admin_info where id in (${ids})")
	public int deleType(@Param("ids") String ids);

	//删除用户权限
	@Delete("delete from powers where aid in (${ids})")
	public int delePow(@Param("ids") String ids);
}
