<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <style type="text/css">
        .pageLink {
            border: 1px solid #dddddd;
            padding: 4px 12px;
            text-decoration: none;
        }

        .selectPageLink {
            border: 1px solid #0088cc;
            padding: 4px 12px;
            color: #0088cc;
            background-color: #dddddd;
            text-decoration: none;
        }
    </style>
</head>

<body>
<input type="hidden" name="pageIndex" id="pageIndex">
<!-- 分页标签 -->
<div style="text-align: center; border: 0;padding: 4px 12px;" class="pageDiv mt20">
    <pg:pager url="#" items="${page.totalCount}" maxPageItems="${page.pageSize}" maxIndexPages="1000" isOffset="true">
        总共：${page.totalCount}条,共:${page.totalPage}页
        <pg:first>
            <a id="first" href="#" class="pageLink">首页</a>
        </pg:first>
        <c:if test="${page.currentPage != 1 && page.totalPage > 0 }">
            <a id="prev" href="#" class="pageLink">上一页</a>
        </c:if>
        <pg:pages>
            <c:choose>
                <c:when test="${page.pageIndex==pageNumber}">
                    <span class="selectPageLink">${pageNumber}</span>
                </c:when>
                <c:otherwise>
                    <c:if test="${(pageNumber-page.pageIndex lt 5) and (pageNumber-page.pageIndex gt -5)}">
                        <a href="javascript:pageAction('${pageNumber}')" class="pageLink">${pageNumber}</a>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </pg:pages>
        <c:if test="${page.pageIndex != page.totalPage  && page.totalPage > 0 }">
            <a id="next" href="#" class="pageLink">下一页</a>
        </c:if>
        <pg:last>
            <a id="last" href="#" class="pageLink">尾页</a>
        </pg:last>
    </pg:pager>
</div>
<script type="text/javascript">
    $(function(){
        var currentPage = ${page.currentPage};
        var totalPage = ${page.totalPage };
        $("#first").bind("click",function(event){
            pageAction(1);
        });
        $("#prev").bind("click",function(event){
            if(currentPage>1){
                currentPage--;
            }else{
                currentPage = 1;
            }
            pageAction(currentPage);
        });
        $("#next").bind("click",function(event){
            if(totalPage>currentPage){
                currentPage++;
            }else{
                currentPage=totalPage;
            }
            pageAction(currentPage);
        });
        $("a[name='doNumberPage']").bind("click",function(event){
            pageAction(currentPage);
        });
        $("#last").bind("click",function(event){
            pageAction(totalPage);
        });
    });
    function pageAction(currentPage){
        $("#currentPage").val(currentPage);
        $("#queryForm").submit();
    }
</script>
</body>
</html>
