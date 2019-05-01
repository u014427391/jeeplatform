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
    <!-- jquery.pagination所需CSS -->
    <link type="text/css" rel="stylesheet" href="<%=basePath%>plugins/page/css/pagination.css" />
    <script type="text/javascript" src="<%=basePath%>plugins/page/js/jquery.min.js"></script>
    <!-- jquery.pagination所需JS 注意必须放在jquery.js后面 -->
    <script type="text/javascript" src="<%=basePath%>plugins/page/js/jquery.pagination.js"></script>
    <script type="text/javascript" src="<%=basePath%>plugins/zDialog/zDialog.js"></script>
    <script type="text/javascript" src="<%=basePath%>plugins/zDialog/zDrag.js"></script>
    <script type="text/javascript" src="<%=basePath%>plugins/zDialog/zProgress.js"></script>
    <script language="javascript" type="text/javascript" src="<%=basePath%>plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">

        var pageIndex = Number(${pageIndex});

        var totalCount = Number(${totalCount});

        $(document).ready(function () {
            //加入分页的绑定
            $("#Pagination").pagination(totalCount, {
                callback : pageselectCallback,
                prev_text : '< 上一页',
                next_text: '下一页 >',
                items_per_page : 6,
                num_display_entries : 6,
                current_page : pageIndex,
                num_edge_entries : 1,
                link_to: "user/queryAll?pageIndex=__id__"  //分页的js中会自动把"__id__"替换为当前的数。0
            });

            var html = "";
            var data = ${users};
            $.each(data,function(idx,obj){
                var id = obj.id;
                var username = obj.username;
                var mark = obj.mark;
                var phone = obj.phone;
                var email = obj.email;
                var lastLogin = obj.lastLogin;
                var loginIp = obj.loginIp;

                html += "<tr><td><input type='checkbox' name='id' id='id' value=" + id + " /></td>" +
                    "<td>"+id+"</td>"+
                    "<td>"+username+"</td>"+
                    "<td>"+mark+"</td>"+
                    "<td>"+phone+"</td>"+
                    "<td><input type='text' id='toEmail' disabled='disabled' style= 'border:0px;background:rgba(0, 0, 0, 0);' name='toEmail' value="+email+" />" +"</td>"+
                    "<td>"+lastLogin+"</td>"+
                    "<td>"+loginIp+"</td>"+
                    "<td><a href='javascript:openAuthDialog("+id+");' class='bounceIn'>分配角色</a>"+
                    "</tr>";
            });
            $("#content").append(html);

            //显示弹框
            $('.bounceIn').click(function(){
                className = $(this).attr('class');
                $('#dialogBg').fadeIn(300);
                $('#dialog').removeAttr('class').addClass('animated '+className+'').fadeIn();
            });
        });

        //这个事件是在翻页时候用的
        function pageselectCallback(index, jq) {

        }

        //checkbox的全选/反选
        var isCheckAll = false;
        function doCheck(){
            if(isCheckAll){
                $("input[type='checkbox']").each(function(){
                    this.checked = false;
                });
                isCheckAll = false;
            }else{
                $("input[type='checkbox']").each(function(){
                    this.checked = true;
                });
                isCheckAll = true;
            }
        }

        //checkbox的全选/反选
        function exportExcel(){
            var str = '';
            for(var i=0;i < document.getElementsByName('id').length;i++)
            {
                if(document.getElementsByName('id')[i].checked){
                    if(str=='') str += document.getElementsByName('id')[i].value;
                    else str += ',' + document.getElementsByName('id')[i].value;
                }
            }
            if(str==''){
                alert('请选择你要导出的数据!');
                return;
            }else{
                window.location.href = "user/exportExcel.do?ids="+str;
            }
        }

        function sendEmail(){
            var str = '';
            for(var i=0;i<document.getElementsByName('id').length;i++){
                if(document.getElementsByName('id')[i].checked){
                    if(str=='') str += document.getElementsByName('toEmail')[i].value;
                    else str += ';' + document.getElementsByName('toEmail')[i].value;
                }
            }
            if(str==''){
                alert('请选择你要发送的!');
                return;
            }else{
                var diag = new Dialog();
                diag.Title = "发送邮件";
                diag.Width = 800;
                diag.Height = 800;
                diag.URL = "goSendEmail.do?toEmails="+str;
                diag.show();
            }
        }

        function doSearch() {
            var keyword = $("#keywork").val();
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            alert('test');
            window.location.href="user/searchU.do?pageIndex="+0+"&keywork="+keyword+"&startDate="+startDate+"&endDate="+endDate;
        }

        function closdlg()
        {
            Dialog.close();
        }

        function openAddDialog(){
            var diag = new Dialog();
            diag.Title = "新增管理员";
            diag.Width = 400;
            diag.Height = 300;
            diag.URL = "goAddU.do";
            diag.show();
        }

        function openEditDialog(id){
            var diag = new Dialog();
            diag.Title = "编辑管理员信息";
            diag.Width = 400;
            diag.Height = 300;
            diag.URL = "goEditU.do?userId="+id;
            diag.show();
        }

        function openAuthDialog(id){
            var diag = new Dialog();
            diag.Title = "用户角色设置";
            diag.Width = 400;
            diag.Height = 300;
            diag.URL = "goAuthU.do?userId="+id;
            diag.show();
        }

    </script>
</head>
<body>
<br>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form class="form-inline">
                        <input type="button"  class="btn btn-default" value="添加管理员" onclick="openAddDialog();" />
                        <input type="button" class="btn btn-default" value="发送邮件" onclick="sendEmail();" />
                        <input type="button" class="btn btn-default" value="导出Excel表" onclick="exportExcel();" />
                        <br><br>
                        <!--<input type="text" class="form-control" id="keywork" placeholder="请输入关键词">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        日期从<input class="form-control" id="startDate" placeholder="请选择开始日期" type="text" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                        到<input class="form-control" id="endDate" placeholder="请选择结束日期" type="text" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})">&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" class="btn btn-default" value="Search" onclick="doSearch()"/>-->
                    </form>
                    <table class="table" id="mTable">
                        <thead>
                        <tr>
                            <th><input type="checkbox" onclick="doCheck();" /></th>
                            <th>序号</th>
                            <th>用户名</th>
                            <th>描述</th>
                            <th>手机</th>
                            <th>邮箱</th>
                            <th>最近登录</th>
                            <th>上次登录IP</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="content">

                        </tbody>
                    </table>
                    <div id="Pagination" class="pagination"></div>
                    <!-- demo  -->
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>