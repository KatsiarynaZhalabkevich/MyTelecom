<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tags.tld" prefix='mytag' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
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
    <fmt:message bundle="${loc}" key="local.prevpage" var="prev"/>
    <fmt:message bundle="${loc}" key="local.nextpage" var="next"/>
    <fmt:message bundle="${loc}" key="local.back" var="back"/>
    <title>${tarif}</title>
</head>
<body>

<c:import url="../import/header.jsp"/>


<div class="jumbotron">
    <div class="container">
        <br>

        <p>${mess1} </p>

    </div>
</div>
<div class="table-responsive">
    <table>
        <tr>
            <td>
                <c:if test="${requestScope.tariffNumPage>1}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_tariffs"/>
                        <input type="hidden" name="pageNum" value="${requestScope.tariffNumPage-1}" >
                        <input type="submit" class="btn-link" style="color: black" value="${prev}"/>
                        <c:set scope="request" var="tariffNumPage" value="${requestScope.tariffNumPage-1}"/>
                    </form>
                </c:if>
            </td>
            <td>
                <form action="#">
                    <input type="submit" class="btn-link" style="color: black" value="${requestScope.tariffNumPage}">
                </form>
            </td>
            <td>
                <c:if test="${!requestScope.isLastPageTariff}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_tariffs"/>
                        <input type="hidden" name="pageNum" value="${requestScope.tariffNumPage+1}" >
                        <input type="submit" class="btn-link" style="color: black" value="${next}"/>
                        <c:set scope="request" var="tariffNumPage" value="${requestScope.tariffNumPage+1}"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>
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

        <jsp:useBean id="userbean" class="by.epam.zhalabkevich.my_telecom.tag.JSPListBean" scope="session"/>
        <mytag:tariff list="${sessionScope.userbean}" num="${sessionScope.userbean.size}"/>
    </table>
</div>
<c:import url="/../footer.jsp"/>
</body>
</html>
