<%@ page contentType="text/html;charset=UTF-8" language="java"
         errorPage="../page/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.ru"
             var="ru_button"/>
<fmt:message bundle="${loc}" key="local.locbutton.name.en"
             var="en_button"/>
<fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
<fmt:message bundle="${loc}" key="local.registration" var="registr"/>
<fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
<fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
<fmt:message bundle="${loc}" key="local.logout" var="logout"/>
<fmt:message bundle="${loc}" key="local.placeholderlog" var="login"/>
<fmt:message bundle="${loc}" key="local.placeholderpas" var="pass"/>
<fmt:message bundle="${loc}" key="local.buttonsignin" var="signin"/>
<fmt:message bundle="${loc}" key="local.message1index" var="mes1index"/>
<fmt:message bundle="${loc}" key="local.message2index" var="mes2index"/>
<fmt:message bundle="${loc}" key="local.buttontarif" var="buttarif"/>
<script type="module" src="../js/loginValidation.js"></script>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="/main" class="navbar-brand">My Telecom</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:if test="${user==null}">
                    <li><a href="users/new">${registr}</a></li>
                </c:if>
                <c:if test="${user!=null}">
                    <li><a href="users/user">${priv}</a></li>
                </c:if>
                <c:if test="${isAdmin}">
                    <li><a href="users/admin">${admin}</a></li>
                </c:if>
            </ul>
            <c:if test="${user!=null}">
                <div class="navbar-right">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="logout"/>
                        <input class="btn btn-success" type="submit" value="${logout}"/>
                    </form>
                </div>
            </c:if>

            <c:if test="${user==null}">
                <form class="navbar-form navbar-right" role="form" action="controller" method="post"
                      id="form" onsubmit="valid(document.getElementById('form'))">
                    <input type="hidden" name="command" value="authorization"/>
                    <div class="form-group">
                        <input type="text" placeholder="${login}" class="form-control" name="login" id="login"/>
                    </div>
                    <div class="form-group">
                        <input type="password" placeholder="${pass}" class="form-control" name="password"
                               id="password"/>
                    </div>

                    <div class="form-group">
                        <input type="submit" class="btn btn-success" value="${signin}"/>
                    </div>
                </form>
            </c:if>
            <div class="row navbar-right">
                <div>
                    <form action="controller" method="post">
                        <input type="hidden" name="local" value="ru"/>
                        <input type="submit" class="btn btn-xs btn-default " value="${ru_button}"/>
                    </form>
                </div>
                <div>
                    <form action="controller" method="post">
                        <input type="hidden" name="local" value="en"/>
                        <input type="submit" class="btn-default btn btn-xs" value="${en_button}"/>
                    </form>
                </div>
            </div>

        </div>
    </div>

</nav>
