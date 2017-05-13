<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<div style="font-family: microsoft yahei">
<table id="dg" title="菜单管理" class="easyui-datagrid" fitColumns="true"
 toolbar="#mtb">
	<thead>
		<tr>
			<th field="id" width="10" align="center">编号</th>
			<th field="name" width="30" align="center">菜单名称</th>
			<th field="icon" width="10" align="center">菜单图标</th>
			<th field="url" width="30" align="center">菜单地址</th>
			<th field="morder" width="10" align="center">菜单排序</th>
			<th field="mtype" width="10" align="center">菜单类别</th>
			<th field="cz" width="10" align="center">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
		<c:when test="${not empty menus}">
		<c:forEach items="${menus }" var="m" varStatus="vs">
		<tr id="tr${m.menuId }">
			<td>${vs.index+1}</td>
			<td>${m.name }</td>
			<td><i class="${m.menuIcon }" width="30px">&nbsp;&nbsp;&nbsp;&nbsp;</i></td>
			<td>${m.menuUrl }</td>
			<td>${m.menuOrder }</td>
			<td>${m.menuType }</td>
			<td><a onclick="javascript:doLoadMenu('${m.menuId }',this,${vs.index })">二级菜单</a></td>
		</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
		<tr>没有相关数据</tr>
		</c:otherwise>
		</c:choose>
	</tbody>
</table>
<div id="mtb"> 
	<div>
		<a href="javascript:openLinkAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		<a href="javascript:doRefresh()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>		
	</div>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		
	});
	
	function doLoadMenu(menuId,curObj,trIndex){
		var txt = $(curObj).text();
		if(txt=="二级菜单"){
			$(curObj).text("折叠");
			$("#tr"+menuId).after("<tr id='tempTr"+menuId+"'><td colspan='5'>数据载入中</td></tr>");
			if(trIndex%2==0){
				$("#tempTr"+menuId).addClass("main_table_even");
			}
			var url = "<%=basePath%>menu/sub.do?menuID="+menuId+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data.length>0){
					var html = "";
					$.each(data,function(i){
						html = "<tr style='height:24px;line-height:24px;' name='subTr"+menuId+"'>";
						html += "<td></td>";
						html += "<td><span style='width:80px;display:inline-block;'></span>";
						html += "<img src='"+this.menuIcon+"' style='vertical-align: middle;'/>";
						html += "<span style='width:100px;text-align:left;display:inline-block;'>"+this.menuName+"</span>";
						html += "</td>";
						html += "<td>"+this.menuUrl+"</td>";
						html += "<td class='center'>"+this.menuOrder+"</td>";
						html += "<td><a class='btn btn-mini btn-info' title='编辑' onclick='editmenu(\""+this.menuID+"\")'><i class='icon-edit'></i></a> <a class='btn btn-mini btn-danger' title='删除' onclick='delmenu(\""+this.MENU_ID+"\",false)'><i class='icon-trash'></i></a></td>";
						html += "</tr>";
						$("#tempTr"+menuId).before(html);
					});
					$("#tempTr"+menuId).remove();
					if(trIndex%2==0){
						$("tr[name='subTr"+menuId+"']").addClass("main_table_even");
					}
				}else{
					$("#tempTr"+menuId+" > td").html("没有相关数据");
				}
			},"json");
			$('#dg').datagrid('reload'); 
		}else{
			$("#tempTr"+menuId).remove();
			$("tr[name='subTr"+menuId+"']").remove();
			$(curObj).text("二级菜单");
			$('#dg').datagrid('reload');  	
		}
	}

	function doRefresh(){
		$('#dg').datagrid('reload'); 
	}
	
</script>