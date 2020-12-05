<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 22.02.20
  Time: 00:26
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tags.tld" prefix='mytag' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="webjars/docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">

    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.nametarif" var="name"/>
    <fmt:message bundle="${loc}" key="local.description" var="descr"/>
    <fmt:message bundle="${loc}" key="local.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.speed" var="speed"/>
    <fmt:message bundle="${loc}" key="local.discount" var="discount"/>
    <fmt:message bundle="${loc}" key="local.edit" var="edit"/>
    <fmt:message bundle="${loc}" key="local.delete" var="delete"/>
    <fmt:message bundle="${loc}" key="local.addtariff" var="addtariff"/>
    <fmt:message bundle="${loc}" key="local.message1tariffadmin" var="mess1"/>
    <fmt:message bundle="${loc}" key="local.back" var="back"/>
    <title>${tarif}</title>
</head>
<body>
<c:import url="/header"/>

<br>
<br>
<div class="container">
<div class="row">
<form action="controller" method="get">
    <input type="hidden" name="command" value="custom_tag"/>
    <c:if test="${pageNum>1}">
        <input type="submit" class="btn-link"  value="prev"/>
        <c:set scope="session" var="pageNum" value="${pageNum-1}"/>

    </c:if>
</form>

<a href="#"><c:out value="${sessionScope.pageNum}" /> </a>

<form action="controller" method="get"><c:if test="${!isLastPage}">
    <input type="hidden" name="command" value="custom_tag"/>
    <input type="submit" class="btn-link" value="next"/>
    <c:set scope="session" var="pageNum" value="${pageNum+1}"/>

</c:if>
</form>
</div>
</div>
<div class="table-responsive">
    <table class="table table-striped">
        <tr class="active">
            <th>№</th>
            <th> ${name} </th>
            <th>${descr}</th>
            <th>${speed}</th>
            <th>${price}, $</th>
            <th>${discount}</th>
            <th></th>
        </tr>

        <c:forEach var="tarifList" items="${tarifs}">
            <tr>
                <td>${tarifList.id}</td>
                <td> ${tarifList.name}</td>

                <td>${tarifList.description}</td>
                <td>${tarifList.speed}</td>
                <td>${tarifList.price}</td>
                <td>${tarifList.discount}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<c:import url="/footer"/>
</body>
</html>
