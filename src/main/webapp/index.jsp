<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
    <head>
        <meta charset="UTF-8">
        <base href="<%=basePath%>">
        <link href="EasyUI/themes/default/easyui.css" rel="stylesheet"
              type="text/css" />
        <link href="EasyUI/themes/icon.css" rel="stylesheet" type="text/css" />
        <link href="EasyUI/demo.css" rel="stylesheet" type="text/css" />
        <script src="EasyUI/jquery.min.js" type="text/javascript"></script>
        <script src="EasyUI/jquery.easyui.min.js" type="text/javascript"></script>
        <script src="EasyUI/easyui-lang-zh_CN.js" type="text/javascript"></script>
    </head>
<body>
<h2>中文</h2>
<ul id="test" class="easyui-tree" name="test"
    checkbox="true">
</ul>

<script>
$('#test').tree({
    url:'admininfo/getpowerTree?adminid=2',
    onLoadSuccess:function(node,data) {
        console.log(data[1].id);
        if(data.length>0){
            var n = $('#test').tree('find',data[1].id);
            $('#test').tree('check',n.target);
        }
       /* alert(1+"."+data);
        console.log("asdasdascsa");
        for(i=0;i<data.length;i++){
            console.log(1);
        }
        var node = $('#test').tree('find',3);
        $('#test').tree('check',node.target);*/
    }
})

</script>
</body>
</html>
