<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	if (session.getAttribute("admin") == null)
		response.sendRedirect("admin_login.jsp");
%>
<html>
<head>
	<meta charset="UTF-8">
	<base href="<%=basePath%>">
	<title>用户列表页</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
<!-- 定义table, 用于创建easy ui的datagrid控件 -->
<table id="dg_admininfo" class="easyui-datagrid"></table>
<!-- 创建datagrid控件的工具栏 -->
<div id="tb_admininfo" style="padding: 2px 5px;">
	<a href="javascript:void(0)" class="easyui-linkbutton"
	   iconCls="icon-add" plain="true" onclick="addAdmin();">添加</a>  <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-remove" onclick="deleAdmin();" plain="true">删除</a>
</div>

<!-- 创建查询工具栏 -->
<div id="searchtb_Admininfo" style="padding: 2px 5px;">
	<form id="searchForm_admininfo" method="post">
		<div style="padding: 3px">
			用户名称&nbsp;&nbsp;<input class="easyui-textbox"
								   name="adminInfo_search_name" id="adminInfo_search_name"
								   style="width: 110px" />
			&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
						   iconCls="icon-search" plain="true" onclick="searchAdmin();">查找</a>
		</div>
	</form>
</div>

<!-- 添加商品信息对话框 -->
<div id="dlg_admininfo" class="easyui-dialog" title="用户信息"
	 closed="true" style="width: 500px;">
	<div style="padding: 10px 60px 20px 60px">
		<form id="ff_admininfo" method="POST" action=""
			  enctype="multipart/form-data" name="ff_admininfo">
			<table cellpadding="5">
				<tr>
					<td>用户名称:</td>
					<td><input class="easyui-textbox" type="text" id="name"
							   name="name" data-options="required:true"></input></td>
				</tr>
				<tr>
					<td>用户密码:</td>
					<td><input class="easyui-textbox" type="text" id="pwd"
							   name="pwd" data-options="required:true"></input></td>
				</tr>
				<tr>
					请选择权限：
					<ul id="test" class="easyui-tree" name="test"
						url="admininfo/getTree?adminid=${sessionScope.admin.id}"
						checkbox="true">
					</ul>
				</tr>
			</table>
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
			   onclick="saveAdmin();">确定</a> <a href="javascript:void(0)"
												  class="easyui-linkbutton" onclick="clearForm();">清空</a>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$('#dg_admininfo').datagrid({
			singleSelect : false, //设置datagrid为单选
			url : 'admininfo/list', //为datagrid设置数据源
			pagination : true, //启用分页
			pageSize : 10, //设置初始每页记录数（页大小）
			pageList : [ 10, 15, 20 ], //设置可供选择的页大小
			rownumbers : true, //显示行号
			fit : true, //设置自适应
			toolbar : '#tb_admininfo', //为datagrid添加工具栏
			header : '#searchtb_Admininfo', //为datagrid标头添加搜索栏
			columns : [ [ { //编辑datagrid的列
				title : '序号',
				field : 'id',
				align : 'center',
				checkbox : true
			}, {
				field : 'name',
				title : '用户名',
				width : 200
			}, {
				field : 'pwd',
				title : '用户密码',
				width : 100
			} ] ]
		});
	});


	// 查询用户
	function searchAdmin() {
		var adminInfo_search_name = $('#adminInfo_search_name').textbox("getValue");
		$("#dg_admininfo").datagrid('load', {
			"name" : adminInfo_search_name,
		});
	}

	// 打开新增用户对话框
	function addAdmin() {
		$('#dlg_admininfo').dialog('open').dialog('setTitle', '新增用户');
		$('#dlg_admininfo').form('clear');
		urls = 'admininfo/addAdmin';
	}

	function clearForm() {
		$('#ff_admininfo').form('clear');
	}

	$('#test').tree({
		onLoadSuccess:function() {
			var n = $('#test').tree('find', 13);
			$('#test').tree('check', n.target);
		}
	});

	// 保存用户信息
	function saveAdmin() {
		var name = $("#name").val();
		var pwd = $("#pwd").val();
		var result = [];
		var result2 = [];
		var nodes = $('#test').tree('getChecked');
		var fuandgengnode = $('#test').tree('getChecked', 'indeterminate');
		if(nodes.length > 0){

			for(i=0;i<nodes.length;i++){
				result.push(nodes[i].id);
			}
		}
		if(fuandgengnode.length > 0){

			for(i=0;i<fuandgengnode.length;i++){
				result2.push(fuandgengnode[i].id);
			}
		}
		var str1 = JSON.stringify(result);
		var str2 = JSON.stringify(result2);
		$.ajax({
			url : "admininfo/addAdmin",
			traditional:true,
			type : "post",
			data : {
				dataType : "json",
				'name' : name,
				'pwd' : pwd,
				'str1' : str1,
				'str2' : str2
			},
			success : function(result) {
				// data1="{\"success\":\"true\",\"message\":\"添加成功\"}";
				var result = eval('(' + result + ')');
				if (result.success == 'true') {
					$.messager.show({
						title : "提示信息",
						msg : result.message
					});
					$("#dg_admininfo").datagrid("reload");
					$("#dlg_admininfo").dialog("close");
				} else {
					$.messager.show({
						title : "提示信息",
						msg : result.message
					});
				}
			}
		});
	}


	//删除用户
	function deleAdmin() {
		// 获取选中的订单记录行
		var rows = $("#dg_admininfo").datagrid('getSelections');
		if (rows.length > 0) {
			$.messager.confirm('Confirm', '确认要删除么?', function(r) {
				if (r) {
					var ids = "";
					// 获取选中订单记录的订单id, 保存到ids中
					for (var i = 0; i < rows.length; i++) {
						ids += rows[i].id + ",";
					}
					// 发送请求
					$.post('admininfo/deleAdmin', {
						ids : ids
					}, function(result) {
						if (result.success == 'true') {
							$("#dg_admininfo").datagrid('reload');
							$.messager.show({
								title : '提示信息',
								msg : result.message
							});
						} else {
							$.messager.show({
								title : '提示信息',
								msg : result.message
							});
						}
					}, 'json');

				}
			});
		} else {
			$.messager.alert('提示', '请选择要删除的行', 'info');
		}
	}


</script>
</body>
</html>