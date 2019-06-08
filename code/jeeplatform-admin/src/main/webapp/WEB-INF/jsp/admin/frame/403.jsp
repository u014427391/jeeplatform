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
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>403无权访问</title>
    <style type="text/css">
        body,h1,p{margin:0;padding:0;}
        body{background:#fcfbfb;}
        .nofound{width:470px;margin:0 auto;padding:180px 0 100px;text-align:center;color:#5c6048;font-size:16px;line-height:30px;font-family:"Microsoft Yahei";background:url(images/nofound-bg.png) right 62px no-repeat;}
        .nofound p{padding-top:20px;}
        .nofound-tit{padding-bottom:32px;font-weight:normal;font-size:150px;line-height:120px;}
        .nofound-why{font-size:30px;line-height:30px;}
        .sorry{font-size:30px;}
        .back-homepage{color:#ff6000;font-size:14px;}
    </style>
</head>

<body>
<div class="nofound">
    <h1 class="nofound-tit">403 <span class="nofound-why">why</span></h1>
    <p class="sorry">非常抱歉...</p>
    <p>您访问的页面无权访问</p>
    <p>您可以  <a class="back-homepage" href="/jeeplatform">返回登录页</a></p>
</div>
</body>
</html>