<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
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
    
    <title>My JSP 'typelist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <table id="typeDg" class="easyui-datagrid"></table>
	<div id="typeTb" style="padding:2px 5px;">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="addType();">添加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editType();">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleType();">删除</a>
	</div>
	
	<div id="typeDlg" class="easyui-dialog" title="New Type" closed="true"
		style="width:500px;">
		<div style="padding:10px 60px 20px 60px">
			<form id="typeForm" method="POST" action="">
				<table cellpadding="5">					
					<tr>
						<td>产品名称:</td>
						<td><input class="easyui-textbox" type="text" id="name"
							name="name" data-options="required:true"></input></td>
					</tr>					
				</table>
			</form>
			<div style="text-align:center;padding:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					onclick="saveType();">保存</a> <a href="javascript:void(0)"
					class="easyui-linkbutton" onclick="clearForm();">清空</a>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#typeDg').datagrid({
				singleSelect : false, //设置datagrid为单选
				url : 'type/getType/0', //为datagrid设置数据源
				rownumbers : true, //显示行号
				fit : true, //设置自适应
				toolbar : '#typeTb', //为datagrid添加工具栏
				columns : [ [ { //编辑datagrid的列
					title : '序号',
					field : 'id',
					align : 'center',
					checkbox : true
				}, {
					field : 'name',
					title : '商品类型',
					width : 200
				}] ]
			});
		});

		var urls;
		var data;

		
		function addType() {
			$('#typeDlg').dialog('open').dialog('setTitle', '新增商品类型');
			$('#typeDlg').form('clear');
			urls = 'type/addType';
		}

		function saveType() {
			$("#typeForm").form("submit", {
				url : urls, //使用参数				
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.success == 'true') {
						$("#typeDg").datagrid("reload");
					}
					$("#typeDlg").dialog("close");
					$.messager.show({
						title : "提示信息",
						msg : result.message
					});
				}
			});
		}
		function clearForm() {
			$('#typeForm').form('clear');
		}

		function editType() {
			var row = $("#typeDg").datagrid("getSelected");
			if (row) {
				$("#typeDlg").dialog("open").dialog('setTitle', '修改产品信息');
				$("#typeForm").form("load", {
					"name" : row.name
				});
				urls = "type/updateType?id=" + row.id;
			}
		}

		function deleType() {
			var rows = $("#typeDg").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm('Confirm', '确认要删除么?', function(r) {
					if (r) {
						var ids = "";
						// 获取选中订单记录的订单id, 保存到ids中
						for (var i = 0; i < rows.length; i++) {
							ids += rows[i].id + ",";
						}
						// 发送请求
						$.post('type/deleType', {
							ids : ids
						}, function(result) {
							if (result.success == 'true') {
								$("#typeDg").datagrid('reload');
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
