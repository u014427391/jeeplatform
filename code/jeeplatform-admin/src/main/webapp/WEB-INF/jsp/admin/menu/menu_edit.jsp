<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath %>">
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>Insert title here</title>
    <!-- bootstrap样式-->
    <link type="text/css" rel="stylesheet"
          href="<%=basePath%>plugins/page/css/bootstrap-3.3.5.min.css" />
</head>
<body style="padding: 20px;">
<br>
<form class="form-inline" role="form">
    <div class="form-group">
        <label class="sr-only" for="menuId">编号</label>
        <input type="text" class="form-control" id="menuId" placeholder="菜单编号">
    </div>
    <div class="form-group">
        <label class="sr-only" for="menuName">名称</label>
        <input type="text" class="form-control" id="menuName" placeholder="菜单名称">
    </div>
    <div class="form-group">
        <label class="sr-only" for="menuUrl">地址</label>
        <input type="text" class="form-control" id="menuUrl" placeholder="菜单地址">
    </div>
    <div class="form-group">
        <label class="sr-only" for="menuType">类型</label>
        <input type="text" class="form-control" id="menuType" placeholder="菜单类型">
    </div>
    <div class="form-group">
        <label class="sr-only" for="menuStatus">状态</label>
        <input type="text" class="form-control" id="menuStatus" placeholder="菜单状态">
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-default btn-sm">提交</button>
    </div>
</form>
</body>
</html>