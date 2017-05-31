<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div data-ui="header" class="J_header">
    <div class="h_left">
        <div class="l_txt">
            <c:choose>
                <c:when test="${not empty head_name }">${head_name }</c:when>
                <c:otherwise>${menu_name }</c:otherwise>
            </c:choose>
        </div>
        <div class="l_nav">
            <c:forEach items="${childMenu }" var="m">
                <div class="n_item ${m.current }">
                    <div class="i_default"><a href="${ctx }${m.url}"><span>${m.name }</span><b></b></a></div>
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="h_right">
        <div class="r_menu">
            <div class="m_item">
                <div class="i_default"><a href="javascript:;"><i class="icon user"></i><span>${sessionScope.session_login_user.realName }</span><i class="icon down"></i></a></div>
                <div class="i_menu">
                    <div data-ui="profile" class="J_profile">
                        <div class="p_user">
                            <div class="u_face">
                                <a href="javascript:;"><img src="${ctx }/static/images/default_face.jpg"></a>
                            </div>
                            <div class="u_name">
                                <span>${sessionScope.session_login_user.realName }</span>
                            </div>
                        </div>
                        <div class="p_list">
                            <div class="l_item"><a href="${ctx }/user/update_pwd"><i class="icon"></i><span>修改密码</span></a></div>
                            <div class="l_item border"><a href="${ctx }/login_out"><i class="icon"></i><span>退出</span></a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>