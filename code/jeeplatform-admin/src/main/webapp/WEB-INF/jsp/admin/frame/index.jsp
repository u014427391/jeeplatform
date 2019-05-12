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
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<title>企业信息化基础平台</title>
	<!-- 页面logo设置 start-->
	<link rel="icon" type="image/png" href="static/favicon.ico">
	<!-- 页面logo设置 end -->
	<link rel="stylesheet" type="text/css" href="<%=basePath %>static/css/main.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath %>static/skin/blue/skin.css" id="layout-skin"/>

</head>
<body>
<div>
	<header class="layout-header">
		<span class="header-logo">企业信息化基础平台</span>
		<a class="header-menu-btn" href="javascript:;"><i class="icon-font">&#xe600;</i></a>
		<ul class="header-bar">
			<li class="header-bar-role"><a href="javascript:;">${sessionUser.mark}</a></li>
			<li class="header-bar-nav">
				<a href="javascript:;">${username}<i class="icon-font" style="margin-left:5px;">&#xe60c;</i></a>
				<ul class="header-dropdown-menu">
					<li><a href="javascript:toUpdatePwdDialog();">修改密码</a></li>
					<li><a href="logout">切换账户</a></li>
					<li><a href="logout">退出</a></li>
				</ul>
			</li>
			<li class="header-bar-nav">
				<a href="javascript:;" title="换肤"><i class="icon-font">&#xe608;</i></a>
				<ul class="header-dropdown-menu right dropdown-skin">
					<li><a href="javascript:;" data-val="qingxin" title="清新">清新</a></li>
					<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
					<li><a href="javascript:;" data-val="molv" title="墨绿">墨绿</a></li>
				</ul>
			</li>
		</ul>
	</header>

	<aside class="layout-side">
		<ul class="side-menu">

		</ul>
	</aside>

	<div class="layout-side-arrow"><div class="layout-side-arrow-icon"><i class="icon-font">&#xe60d;</i></div></div>

	<section class="layout-main">
		<div class="layout-main-tab">
			<button class="tab-btn btn-left"><i class="icon-font">&#xe60e;</i></button>
			<nav class="tab-nav">
				<div class="tab-nav-content">
					<a href="javascript:;" class="content-tab active" data-id="#">首页</a>
				</div>
			</nav>
			<button class="tab-btn btn-right"><i class="icon-font">&#xe60f;</i></button>
		</div>
		<div class="layout-main-body">
			<iframe class="body-iframe" name="iframe0" width="100%" height="99%" src="#" frameborder="0" data-id="#" seamless></iframe>
		</div>
	</section>
	<div class="layout-footer">@2017 V.1.0 Muses Team </div>
</div>
<!-- 引入JQuery库 start -->
<script type="text/javascript" src="${basePath}static/js/jquery-1.8.3.js"></script>
<!-- 引入JQuery库 end -->
<script type="text/javascript" src="<%=basePath%>plugins/zDialog/zDialog.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zDialog/zDrag.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zDialog/zProgress.js"></script>
<script type="text/javascript">
	/*左侧菜单点击*/
    $(".side-menu").on('click', 'li a', function(e) {
        var animationSpeed = 300;
        var $this = $(this);
        var checkElement = $this.next();

        if (checkElement.is('.menu-item-child') && checkElement.is(':visible')) {
            checkElement.slideUp(animationSpeed, function() {
                checkElement.removeClass('menu-open');
            });
            checkElement.parent("li").removeClass("active");
        }
        //如果菜单是不可见的
        else if ((checkElement.is('.menu-item-child')) && (!checkElement.is(':visible'))) {
            //获取上级菜单
            var parent = $this.parents('ul').first();
            //从父级开始找所有打开的菜单并关闭
            var ul = parent.find('ul:visible').slideUp(animationSpeed);
            //在父级中移出menu-open标记
            ul.removeClass('menu-open');
            //获取父级li
            var parent_li = $this.parent("li");
            //打开菜单时添加menu-open标记
            checkElement.slideDown(animationSpeed, function() {
                //添加样式active到父级li
                checkElement.addClass('menu-open');
                parent.find('li.active').removeClass('active');
                parent_li.addClass('active');
            });
        }
        //防止有链接跳转
        e.preventDefault();

        addIframe($this);
    });

	/*添加iframe*/
    function addIframe(cur){
        var $this = cur;
        var h = $this.attr("href"),
            m = $this.data("index"),
            label = $this.find("span").text(),
            isHas = false;
        if (h == "" || $.trim(h).length == 0) {
            return false;
        }

        var fullWidth = $(window).width();
        if(fullWidth >= 750){
            $(".layout-side").show();
        }else{
            $(".layout-side").hide();
        }

        $(".content-tab").each(function() {
            if ($(this).data("id") == h) {
                if (!$(this).hasClass("active")) {
                    $(this).addClass("active").siblings(".content-tab").removeClass("active");
                    addTab(this);
                }
                isHas = true;
            }
        });

        if(isHas){
            $(".body-iframe").each(function() {
                if ($(this).data("id") == h) {
                    $(this).show().siblings(".body-iframe").hide();
                }
            });
        }
        if (!isHas) {
            var tab = "<a href='javascript:;' class='content-tab active' data-id='"+h+"'>"+ label +" <i class='icon-font'>&#xe617;</i></a>";
            $(".content-tab").removeClass("active");
            $(".tab-nav-content").append(tab);
            var iframe = "<iframe class='body-iframe' name='iframe"+ m +"' width='100%' height='99%' src='"+ h +"' frameborder='0' data-id='"+ h +"' seamless></iframe>";
            $(".layout-main-body").find("iframe.body-iframe").hide().parents(".layout-main-body").append(iframe);
            addTab($(".content-tab.active"));
        }
        return false;
    }

	/*添加tab*/
    function addTab(cur) {
        var prev_all = tabWidth($(cur).prevAll()),
            next_all = tabWidth($(cur).nextAll());
        var other_width =tabWidth($(".layout-main-tab").children().not(".tab-nav"));
        var navWidth = $(".layout-main-tab").outerWidth(true)-other_width;//可视宽度
        var hidewidth = 0;
        if ($(".tab-nav-content").width() < navWidth) {
            hidewidth = 0
        } else {
            if (next_all <= (navWidth - $(cur).outerWidth(true) - $(cur).next().outerWidth(true))) {
                if ((navWidth - $(cur).next().outerWidth(true)) > next_all) {
                    hidewidth = prev_all;
                    var m = cur;
                    while ((hidewidth - $(m).outerWidth()) > ($(".tab-nav-content").outerWidth() - navWidth)) {
                        hidewidth -= $(m).prev().outerWidth();
                        m = $(m).prev()
                    }
                }
            } else {
                if (prev_all > (navWidth - $(cur).outerWidth(true) - $(cur).prev().outerWidth(true))) {
                    hidewidth = prev_all - $(cur).prev().outerWidth(true)
                }
            }
        }
        $(".tab-nav-content").animate({
                marginLeft: 0 - hidewidth + "px"
            },
            "fast")
    }

	/*获取宽度*/
    function tabWidth(tabarr) {
        var allwidth = 0;
        $(tabarr).each(function() {
            allwidth += $(this).outerWidth(true)
        });
        return allwidth;
    }

	/*左按钮事件*/
    $(".btn-left").on("click", leftBtnFun);
	/*右按钮事件*/
    $(".btn-right").on("click", rightBtnFun);
	/*选项卡切换事件*/
    $(".tab-nav-content").on("click", ".content-tab", navChange);
	/*选项卡关闭事件*/
    $(".tab-nav-content").on("click", ".content-tab i", closePage);
	/*选项卡双击关闭事件*/
    $(".tab-nav-content").on("dblclick", ".content-tab", closePage);


	/*左按钮方法*/
    function leftBtnFun() {
        var ml = Math.abs(parseInt($(".tab-nav-content").css("margin-left")));
        var other_width = tabWidth($(".layout-main-tab").children().not(".tab-nav"));
        var navWidth = $(".layout-main-tab").outerWidth(true)-other_width;//可视宽度
        var hidewidth = 0;
        if ($(".tab-nav-content").width() < navWidth) {
            return false
        } else {
            var tabIndex = $(".content-tab:first");
            var n = 0;
            while ((n + $(tabIndex).outerWidth(true)) <= ml) {
                n += $(tabIndex).outerWidth(true);
                tabIndex = $(tabIndex).next();
            }
            n = 0;
            if (tabWidth($(tabIndex).prevAll()) > navWidth) {
                while ((n + $(tabIndex).outerWidth(true)) < (navWidth) && tabIndex.length > 0) {
                    n += $(tabIndex).outerWidth(true);
                    tabIndex = $(tabIndex).prev();
                }
                hidewidth = tabWidth($(tabIndex).prevAll());
            }
        }
        $(".tab-nav-content").animate({
                marginLeft: 0 - hidewidth + "px"
            },
            "fast");
    }

	/*右按钮方法*/
    function rightBtnFun() {
        var ml = Math.abs(parseInt($(".tab-nav-content").css("margin-left")));
        var other_width = tabWidth($(".layout-main-tab").children().not(".tab-nav"));
        var navWidth = $(".layout-main-tab").outerWidth(true)-other_width;//可视宽度
        var hidewidth = 0;
        if ($(".tab-nav-content").width() < navWidth) {
            return false
        } else {
            var tabIndex = $(".content-tab:first");
            var n = 0;
            while ((n + $(tabIndex).outerWidth(true)) <= ml) {
                n += $(tabIndex).outerWidth(true);
                tabIndex = $(tabIndex).next();
            }
            n = 0;
            while ((n + $(tabIndex).outerWidth(true)) < (navWidth) && tabIndex.length > 0) {
                n += $(tabIndex).outerWidth(true);
                tabIndex = $(tabIndex).next()
            }
            hidewidth = tabWidth($(tabIndex).prevAll());
            if (hidewidth > 0) {
                $(".tab-nav-content").animate({
                        marginLeft: 0 - hidewidth + "px"
                    },
                    "fast");
            }
        }
    }

	/*选项卡切换方法*/
    function navChange() {
        if (!$(this).hasClass("active")) {
            var k = $(this).data("id");
            $(".body-iframe").each(function() {
                if ($(this).data("id") == k) {
                    $(this).show().siblings(".body-iframe").hide();
                    return false
                }
            });
            $(this).addClass("active").siblings(".content-tab").removeClass("active");
            addTab(this);
        }
    }

	/*选项卡关闭方法*/
    function closePage() {
        var url = $(this).parents(".content-tab").data("id");
        var cur_width = $(this).parents(".content-tab").width();
        if ($(this).parents(".content-tab").hasClass("active")) {
            if ($(this).parents(".content-tab").next(".content-tab").size()) {
                var next_url = $(this).parents(".content-tab").next(".content-tab:eq(0)").data("id");
                $(this).parents(".content-tab").next(".content-tab:eq(0)").addClass("active");
                $(".body-iframe").each(function() {
                    if ($(this).data("id") == next_url) {
                        $(this).show().siblings(".body-iframe").hide();
                        return false
                    }
                });
                var n = parseInt($(".tab-nav-content").css("margin-left"));
                if (n < 0) {
                    $(".tab-nav-content").animate({
                            marginLeft: (n + cur_width) + "px"
                        },
                        "fast")
                }
                $(this).parents(".content-tab").remove();
                $(".body-iframe").each(function() {
                    if ($(this).data("id") == url) {
                        $(this).remove();
                        return false
                    }
                })
            }
            if ($(this).parents(".content-tab").prev(".content-tab").size()) {
                var prev_url = $(this).parents(".content-tab").prev(".content-tab:last").data("id");
                $(this).parents(".content-tab").prev(".content-tab:last").addClass("active");
                $(".body-iframe").each(function() {
                    if ($(this).data("id") == prev_url) {
                        $(this).show().siblings(".body-iframe").hide();
                        return false
                    }
                });
                $(this).parents(".content-tab").remove();
                $(".body-iframe").each(function() {
                    if ($(this).data("id") == url) {
                        $(this).remove();
                        return false
                    }
                })
            }
        } else {
            $(this).parents(".content-tab").remove();
            $(".body-iframe").each(function() {
                if ($(this).data("id") == url) {
                    $(this).remove();
                    return false
                }
            });
            addTab($(".content-tab.active"))
        }
        return false
    }

	/*头部下拉框移入移出*/
    $(document).on("mouseenter",".header-bar-nav",function(){
        $(this).addClass("open");
    });
    $(document).on("mouseleave",".header-bar-nav",function(){
        $(this).removeClass("open");
    });

	/*左侧菜单展开和关闭按钮事件*/
    $(document).on("click",".layout-side-arrow",function(){
        if($(".layout-side").hasClass("close")){
            $(".layout-side").removeClass("close");
            $(".layout-main").removeClass("full-page");
            $(".layout-footer").removeClass("full-page");
            $(this).removeClass("close");
            $(".layout-side-arrow-icon").removeClass("close");
        }else{
            $(".layout-side").addClass("close");
            $(".layout-main").addClass("full-page");
            $(".layout-footer").addClass("full-page");
            $(this).addClass("close");
            $(".layout-side-arrow-icon").addClass("close");
        }
    });

	/*头部菜单按钮点击事件*/
    $(".header-menu-btn").click(function(){
        $(".layout-side").removeClass("close");
        $(".layout-main").removeClass("full-page");
        $(".layout-footer").removeClass("full-page");
        $(".layout-side-arrow").removeClass("close");
        $(".layout-side-arrow-icon").removeClass("close");

        $(".layout-side").slideToggle();
    });

	/*左侧菜单响应式*/
    $(window).resize(function() {
        var width = $(this).width();
        if(width >= 750){
            $(".layout-side").show();
        }else{
            $(".layout-side").hide();
        }
    });

	/*皮肤选择*/
    $(".dropdown-skin li a").click(function(){
        var v = $(this).attr("data-val");
        var hrefStr=$("#layout-skin").attr("href");
        var hrefRes=hrefStr.substring(0,hrefStr.lastIndexOf('skin/'))+'skin/'+v+'/skin.css';
        $(window.frames.document).contents().find("#layout-skin").attr("href",hrefRes);

        setCookie("scclui-skin", v);
    });

	/*获取cookie中的皮肤*/
    function getSkinByCookie(){
        var v = getCookie("scclui-skin");
        var hrefStr=$("#layout-skin").attr("href");
        if(v == null || v == ""){
            v="qingxin";
        }
        if(hrefStr != undefined){
            var hrefRes=hrefStr.substring(0,hrefStr.lastIndexOf('skin/'))+'skin/'+v+'/skin.css';
            $("#skin").attr("href",hrefRes);
        }
    }

	/*随机颜色*/
    function getMathColor(){
        var arr = new Array();
        arr[0] = "#ffac13";
        arr[1] = "#83c44e";
        arr[2] = "#2196f3";
        arr[3] = "#e53935";
        arr[4] = "#00c0a5";
        arr[5] = "#16A085";
        arr[6] = "#ee3768";

        var le = $(".menu-item > a").length;
        for(var i=0;i<le;i++){
            var num = Math.round(Math.random()*5+1);
            var color = arr[num-1];
            $(".menu-item > a").eq(i).find("i:first").css("color",color);
        }
    }


	/*循环菜单*/
    function initMenu(menu,parent){
        for(var i=0; i<menu.length; i++){
            var item = menu[i];
            var str = "";
            try{
                item.menuIcon == "" ? item.menuIcon = "&#xe610" : item.menuIcon = item.menuIcon;
                if(item.subMenu == ""){
                    str = "<li><a href='"+item.menuUrl+"'><i class='icon-font'>"+item.menuIcon+"</i><span>"+item.menuName+"</span></a></li>";
                    $(parent).append(str);
                }else{
                    str = "<li><a href='"+item.menuUrl+"'><i class='icon-font '>"+item.menuIcon+"</i><span>"+item.menuName+"</span><i class='icon-font icon-right'>&#xe60b;</i></a>";
                    str +="<ul class='menu-item-child' id='menu-child-"+item.menuId+"'></ul></li>";
                    $(parent).append(str);
                    var childParent = $("#menu-child-"+item.menuId);
                    initMenu(item.subMenu,childParent);
                }
            }catch(e){

            }
        }
    }

    function toUpdatePwdDialog(){
        var diag = new Dialog();
        diag.Title = "修改密码";
        diag.Width = 400;
        diag.Height = 300;
        diag.URL = '<%=basePath %>user/toUpdatePwd/${username}';
        diag.show();
	}


	/*
	 初始化加载
	 */
    $(function(){
		/*获取皮肤*/
        //getSkinByCookie();

        //*菜单json*/
		var json = ${menus};
        var menu = eval(json);
        initMenu(menu,$(".side-menu"));
        $(".side-menu > li").addClass("menu-item");

		/*获取菜单icon随机色*/
        //getMathColor();
    });
</script>

</body>
</html>