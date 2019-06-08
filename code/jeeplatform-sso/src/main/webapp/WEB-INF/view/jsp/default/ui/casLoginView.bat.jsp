<jsp:directive.include file="includes/top.jsp" />

<!-- 支持Http方式登录-->
<%--<c:if test="${not pageContext.request.secure}">
    <div id="msg" class="errors">
        <h2><spring:message code="screen.nonsecure.title" /></h2>
        <p><spring:message code="screen.nonsecure.message" /></p>
    </div>
</c:if>--%>

<div class="login" id="login">
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


<jsp:directive.include file="includes/bottom.jsp" />
