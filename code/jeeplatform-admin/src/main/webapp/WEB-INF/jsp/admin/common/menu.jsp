<%@ page language="java" pageEncoding="UTF-8"%>


<div data-ui="nav" class="J_nav">
    <div class="n_logo" style="text-align: center;vertical-align: middle;display:table-cell;">
        <%-- <a href="javascript:;">
            <img src="${ctx }/static/images/logo.png" />
        </a> --%>
        <span style="color: #fff;font-size: 21px;">天梯管理系统</span>
    </div>
    <div class="n_user">
        <div class="u_left">
            <img src="${ctx }/static/images/default_face.jpg" />
        </div>
        <div class="u_right">
            <div class="r_name">
                <span>${sessionScope.session_login_user.realName }</span>
            </div>
            <div class="r_logout"><a href="${ctx }/login_out">[退出]</a></div>
        </div>
    </div>

    <div class="n_list" id="menu">

        <c:forEach items="${sessionScope.session_menu_resource }" var="resource">
            <div class="l_item">
                <div class="i_default">
                    <c:set var="url" value="javascript:;"/>
                    <c:choose>
                        <c:when test="${fn:length(resource.children) > 0 }">
                            <c:set var="url" value="${ctx }${resource.children[0].url }"/>
                        </c:when>
                        <c:when test="${not empty resource.url }">
                            <c:set var="url" value="${ctx }${resource.url }"/>
                        </c:when>
                    </c:choose>
                    <a href="${url }">
                        <i class="icon">${resource.icon }</i><span>${resource.name }</span>
                    </a>
                </div>
                <div class="i_menu">
                    <c:forEach items="${resource.children }" var="ch">
                        <div class="m_item">
                            <c:set var="url" value="javascript:;"/>
                            <c:if test="${not empty ch.url }">
                                <c:set var="url" value="${ctx }${ch.url }"/>
                            </c:if>
                            <a href="${url }">
                                <i class="icon">${ch.icon }</i><span>${ch.name }</span>
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>

    </div>

    <div class="n_visible">
        <a href="javascript:;"><i class="icon">&#xe5c4;</i></a>
    </div>

</div>

<script type="text/javascript">

    $(function(){

        var menuName = '${menu_name}';

        $('#menu div.l_item').each(function(idx, obj){

            var txt = $(obj).find('div.i_default span').text();
            if($.trim(txt) == menuName){
                $(obj).addClass('current');
                return true;
            }

            $(obj).find('div.i_menu div.m_item').each(function(idxCh, objCh){

                var txtCh = $(objCh).find('span').text();
                if($.trim(txtCh) == menuName){
                    $(objCh).addClass('current');
                    $(obj).addClass('current');
                    return true;
                }

            });

        });

    });

</script>
