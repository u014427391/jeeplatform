<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- zTree start-->
<link rel="stylesheet" href="<%=basePath %>static/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="<%=basePath %>plugins/zTree/3.5/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=basePath %>plugins/zTree/3.5/css/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=basePath %>plugins/zTree/3.5/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/zTree/3.5/js/jquery.ztree.core.js"></script>
<SCRIPT type="text/javascript">
    <!--
    var setting = {
        data: {
            key: {
                title:"name"
            },
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onClick
        }
    };

    //*菜单json*/
    var json = ${menus};
    var zNodes = eval(json);

    var log, className = "dark";
    function beforeClick(treeId, treeNode, clickFlag) {
        className = (className === "dark" ? "":"dark");
        showLog("[ "+getTime()+" beforeClick ]&nbsp;&nbsp;" + treeNode.name );
        return (treeNode.click != false);
    }

    function onClick(event, treeId, treeNode, clickFlag) {
        showLog("[ "+getTime()+" onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
    }

    function showLog(str) {
        if (!log) log = $("#log");
        log.append("<li class='"+className+"'>"+str+"</li>");
        if(log.children("li").length > 8) {
            log.get(0).removeChild(log.children("li")[0]);
        }
    }

    function getTime() {
        var now= new Date(),
            h=now.getHours(),
            m=now.getMinutes(),
            s=now.getSeconds();
        return (h+":"+m+":"+s);
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#menuTree"), setting, zNodes);
    });
    //-->
</SCRIPT>
<!-- zTree end-->
<br>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">树形菜单</h3>
	</div>
	<div class="panel-body">
		<ul id="menuTree" class="ztree"></ul>
	</div>
</div>

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">数据列表</h3>
	</div>
	<div class="panel-body">
		click log:<br/>
		<ul id="log" class="log"></ul></p>
	</div>
</div>

<script type="text/javascript">

</script>