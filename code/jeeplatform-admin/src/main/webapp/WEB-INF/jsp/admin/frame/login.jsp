<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>企业信息化基础平台</title>
	<!-- 页面logo设置 start-->
	<link rel="icon" type="image/png" href="static/favicon.ico">
	<!-- 页面logo设置 end -->
	<!-- 页面样式设置，使用bootstrap前端框架 start-->
	<link href="static/css/login.css" rel="stylesheet" />
	<!-- 页面样式设置，使用bootstrap前端框架 end-->

</head>

<body>

<div class="login">
	<div class="box png">
		<div class="input">
			<div class="log">
				<div class="name">
					<label>用户名</label><input type="text" class="text" id="username" placeholder="用户名" value="admin" />
				</div>
				<div class="pwd">
					<label>密　码</label><input type="password" class="text" id="password" placeholder="密码" value="123" />
				</div>
				<div class="rcode">
					<label><img style="height:22px;" id="codeImg" alt="点击更换"
								title="点击更换" src="" /></label>
					<input type="text" class="text" name="code" id="code"  placeholder="验证码" value="code" />
				</div>
				<input type="button" class="submit" onclick="loginCheck();" value="登录">
			</div>
		</div>
	</div>
	<div class="air-balloon ab-1 png"></div>
	<div class="air-balloon ab-2 png"></div>
	<div class="footer"></div>
</div>
<!-- 引入JQuery库 start -->
<script type="text/javascript" src="${basePath}static/js/jquery-1.8.3.js"></script>
<!-- 引入JQuery库 end -->
<script type="text/javascript" src="${basePath}static/js/fun.base.js"></script>
<script type="text/javascript" src="${basePath}static/js/login.js"></script>
<!-- 引入JQuery提示库 start-->
<script type="text/javascript" src="${basePath}static/js/jquery.tips.js"></script>
<!-- 引入JQuery提示库 end-->
<!-- 引入Cookie库 start-->
<script type="text/javascript" src="${basePath}static/js/jquery.cookie.js"></script>
<!-- 引入Cookie库 end -->
<script type="text/javascript">

    /**加载页面时获取验证码**/
    $(document).ready(function() {
        changeCode();
        $("#codeImg").bind("click", changeCode);
    });

    /**按回车键触发登录按钮事件**/
    $(document).keyup(function(event) {
        if (event.keyCode == 13) {
            $(".submit").trigger("click");
        }
    });

    /**获取时间戳**/
    function genTimestamp() {
        var time = new Date();
        return time.getTime();
    }

    /**刷新验证码的脚本处理**/
    function changeCode() {
        $("#codeImg").attr("src", "code?t=" + genTimestamp());
    }

    /**客户端校验**/
    function checkValidity() {

        if ($("#username").val() == "") {

            $("#username").tips({
                side : 2,
                msg : '用户名不得为空',
                bg : '#AE81FF',
                time : 3
            });

            $("#username").focus();
            return false;
        }

        if ($("#password").val() == "") {
            $("#password").tips({
                side : 2,
                msg : '密码不得为空',
                bg : '#AE81FF',
                time : 3
            });

            $("#password").focus();
            return false;
        }
        if ($("#code").val() == "") {

            $("#code").tips({
                side : 1,
                msg : '验证码不得为空',
                bg : '#AE81FF',
                time : 3
            });

            $("#code").focus();
            return false;
        }

        return true;
    }

    /**服务器校验**/
    function loginCheck(){
        if(checkValidity()){
            var username = $("#username").val();
            var password = $("#password").val();
            var code = username+","+password+","+$("#code").val();
            $.ajax({
                type: "POST",//请求方式为POST
                url: 'logincheck',//检验url
                data: {LOGINDATA:code,tm:new Date().getTime()},//请求数据
                dataType:'json',//数据类型为JSON类型
                cache: false,//关闭缓存
                success: function(data){//响应成功
                    if(1 == data.status){
                        $("#login").tips({
                            side : 1,
                            msg : '正在登录 , 请稍后 ...',
                            bg : '#68B500',
                            time : 10
                        });
                        window.location.href="index";
                    }else if("uerror" == data.result){
                        $("#username").tips({
                            side : 1,
                            msg : "用户名或密码有误",
                            bg : '#FF5080',
                            time : 15
                        });
                        $("#username").focus();
                    }else if("codeerror" == data.result){
                        $("#code").tips({
                            side : 1,
                            msg : "验证码输入有误",
                            bg : '#FF5080',
                            time : 15
                        });
                        $("#code").focus();
                    }else if("locked" == data.result){
                        alert('您的账号被锁定了，呜呜');
                    }else{
                        $("#username").tips({
                            side : 1,
                            msg : "缺少参数",
                            bg : '#FF5080',
                            time : 15
                        });
                        $("#username").focus();
                    }
                }
            });
        }
    }
</script>
</body>
</html>
