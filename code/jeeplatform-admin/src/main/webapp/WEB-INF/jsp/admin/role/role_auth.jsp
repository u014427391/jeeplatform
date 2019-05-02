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
    <style>
        body { background: #ffffff; color: #444;font-size:12px; }
        a { color: #07c; text-decoration: none; border: 0; background-color: transparent; }
        body, div, q, iframe, form, h5 { margin: 0; padding: 0; }
        img, fieldset { border: none 0; }
        body, td, textarea { word-break: break-all; word-wrap: break-word; line-height:1.6; }
        body, input, textarea, select, button { margin: 0; font-size: 12px; font-family: Tahoma, SimSun, sans-serif; }
        div, p, table, th, td { font-size:1em; font-family:inherit; line-height:inherit; }
        h5 { font-size:12px; }
        ol li,ul li{ margin-bottom:0.5em;}
        pre, code { font-family: "Courier New", Courier, monospace; word-wrap:break-word; line-height:1.4; font-size:12px;}
        pre{background:#f6f6f6; border:#eee solid 1px; margin:1em 0.5em; padding:0.5em 1em;}
        #content { padding-left:50px; padding-right:50px; }
        #content h2 { font-size:20px; color:#069; padding-top:8px; margin-bottom:8px; }
        #content h3 { margin:8px 0; font-size:14px; COLOR:#693; }
        #content h4 { margin:8px 0; font-size:16px; COLOR:#690; }
        #content div.item { margin-top:10px; margin-bottom:10px; border:#eee solid 4px; padding:10px; }
        hr { clear:both; margin:7px 0; +margin: 0;
            border:0 none; font-size: 1px; line-height:1px; color: #069; background-color:#069; height: 1px; }
        .infobar { background:#fff9e3; border:1px solid #fadc80; color:#743e04; }
        .buttonStyle{width:64px;height:22px;line-height:22px;color:#369;text-align:center;background:url(${basePath}plugins/zDialog/images/buticon.gif) no-repeat left top;border:0;font-size:12px;}
        .buttonStyle:hover{background:url(${basePath}plugins/zDialog/images/buticon.gif) no-repeat left -23px;}

    </style>

</head>
<body >
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</div>
&nbsp;&nbsp;
<input type="button" onClick="doSave()" value="保存" class="buttonStyle" />
<input onClick="dialogClose();" class="buttonStyle" type="button" value="关闭" />
<!-- 引入JQuery库 start -->
<script type="text/javascript" src="${basePath}static/js/jquery-1.8.3.js"></script>
<!-- 引入JQuery库 end -->
<script type="text/javascript" src="<%=basePath%>plugins/zDialog/zDialog.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zDialog/zDrag.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zDialog/zProgress.js"></script>
<link rel="stylesheet" href="<%=basePath%>plugins/zTree/3.5/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=basePath%>plugins/zTree/3.5/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zTree/3.5/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zTree/3.5/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
    <!--
    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback:{
            onClick: {

            }
        }
    };


    /*[
     { id:1, pId:0, name:"随意勾选 1", open:true},
     { id:11, pId:1, name:"随意勾选 1-1", open:true},
     { id:12, pId:1, name:"随意勾选 1-2", open:true}
     ];*/

    var json = ${menus};
    var zNodes = eval(json);

    var code;

    function setCheck() {
        debugger;
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            py = $("#py").attr("checked")? "p":"",
            sy = $("#sy").attr("checked")? "s":"",
            pn = $("#pn").attr("checked")? "p":"",
            sn = $("#sn").attr("checked")? "s":"",
            type = { "Y":py + sy, "N":pn + sn};
        zTree.setting.check.chkboxType = type;
        showCode('setting.check.chkboxType = { "Y" : "' + type.Y + '", "N" : "' + type.N + '" };');
    }
    function showCode(str) {
        if (!code) code = $("#code");
        code.empty();
        code.append("<li>"+str+"</li>");
    }

    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        setCheck();
        $("#py").bind("change", setCheck);
        $("#sy").bind("change", setCheck);
        $("#pn").bind("change", setCheck);
        $("#sn").bind("change", setCheck);
    });
    //-->

    function dialogClose()
    {
        parentDialog.close();
    }

    function doSave() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = zTree.getCheckedNodes();
        var tmpNode;
        var ids = "";
        for(var i=0; i<nodes.length; i++){
            tmpNode = nodes[i];
            if(i!=nodes.length-1){
                ids += tmpNode.id+",";
            }else{
                ids += tmpNode.id;
            }
        }
        var roleId = ${roleId};
        var params = roleId +";"+ids;
        alert(ids);
        $.ajax({
            type: "POST",
            url: 'role/authorise.do',
            data: {params:params,tm:new Date().getTime()},
            dataType:'json',
            cache: false,
            success: function(data){
                if("success" == data.result){
                    alert('授权成功!请重新登录!');
                    parent.location.reload();
                    doDialogClose();
                }else{
                    alert("授权失败!");
                }
            }
        });
    }

</script>
</body>
</html>