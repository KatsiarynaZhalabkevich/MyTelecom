<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 24.02.20
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/page/error.jsp" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
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
    <fmt:message bundle="${loc}" key="local.statistic" var="statistic"/>
    <fmt:message bundle="${loc}" key="local.learnstat" var="learnstat"/>
    <fmt:message bundle="${loc}" key="local.amount" var="amount"/>
    <fmt:message bundle="${loc}" key="local.registerusers" var="users"/>
    <fmt:message bundle="${loc}" key="local.connections" var="connections"/>
    <fmt:message bundle="${loc}" key="local.ratio" var="ratio"/>
    <fmt:message bundle="${loc}" key="local.conn" var="conn"/>
    <fmt:message bundle="${loc}" key="local.cover" var="cover"/>
    <fmt:message bundle="${loc}" key="local.prevpage" var="prev"/>
    <fmt:message bundle="${loc}" key="local.nextpage" var="next"/>
    <fmt:message bundle="${loc}" key="local.back" var="back"/>

    <title>${statistic}</title>
</head>

<body>

<c:import url="/header"/>
<br>
<br>
<div class="jumbotron">
    <div class="container">

        <p>${learnstat} </p>

    </div>
</div>
<div>


</div>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h2>${amount}</h2>
            <div class="table-responsive">

                <table class="table table-striped ">
                    <tr class="active">

                        <th>${users}</th>
                        <th>${tarif}</th>
                        <th>${connections}</th>
                    </tr>
                    <tr>
                        <td>${user_number}</td>
                        <td>${tariff_number}</td>
                        <td>${connectionsCount}</td>
                    </tr>

                </table>
            </div>
        </div>
        <div class="col-md-6">
            <h2>${ratio}</h2>
            <div class="table-responsive">
                <table>
                    <tr>
                        <td>
                            <c:if test="${requestScope.tariffNumPage>1}">
                                <form action="controller" method="get">
                                    <input type="hidden" name="command" value="statistic"/>
                                    <input type="hidden" name="tariffNumPage" value="${requestScope.tariffNumPage-1}">
                                    <input type="submit" class="btn-link" style="color: black" value="${prev}"/>
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
                                    <input type="hidden" name="command" value="statistic"/>
                                    <input type="hidden" name="tariffNumPage" value="${requestScope.tariffNumPage+1}">
                                    <input type="submit" class="btn-link" style="color: black" value="${next}"/>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </table>
                <table class="table table-striped ">
                    <tr class="active">

                        <th>${name}</th>
                        <th>${conn}</th>
                        <th>${cover}, %</th>
                    </tr>
                    <c:forEach var="tariffs" items="${tariffs}">
                        <tr>

                            <td>${tariffs.name}</td>
                            <td>${tariff_count[tariffs.id]}</td>
                            <td><fmt:formatNumber value="${(tariff_count[tariffs.id]/connectionsCount)*100}" maxFractionDigits="2"/> %</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="go_to_page"/>
        <input type="hidden" name="go_to_page" value="users/admin"/>
        <input type="submit" class="btn btn-success" value="${back}"/>
    </form>
</div>
<c:import url="/footer"/>
</body>

</html>
