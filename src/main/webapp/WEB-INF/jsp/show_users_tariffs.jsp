<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/page/error.jsp"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
    <style>
        body {
            padding: 5px;
        }
    </style>

    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.local" var="loc" />

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.nametarif" var="name"/>
    <fmt:message bundle="${loc}" key="local.description" var="descr"/>
    <fmt:message bundle="${loc}" key="local.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.date" var="date"/>
    <fmt:message bundle="${loc}" key="local.back" var="back"/>
    <fmt:message bundle="${loc}" key="local.registrationDate" var="registrationDate"/>
    <fmt:message bundle="${loc}" key="local.accountstat" var="accountStat"/>
    <fmt:message bundle="${loc}" key="local.butteditinfo" var="edit"/>
    <fmt:message bundle="${loc}" key="local.balance" var="balance"/>
    <fmt:message bundle="${loc}" key="local.buttupdbalance" var="buttupdbalance"/>
    <fmt:message bundle="${loc}" key="local.message4user" var="mess4user"/>
    <fmt:message bundle="${loc}" key="local.nametarif" var="nametarif"/>
    <fmt:message bundle="${loc}" key="local.description" var="descrip"/>
    <fmt:message bundle="${loc}" key="local.price" var="price"/>
    <fmt:message bundle="${loc}" key="local.buttactivate" var="buttactiv"/>
    <fmt:message bundle="${loc}" key="local.message5user" var="mess5user"/>
    <fmt:message bundle="${loc}" key="local.date" var="date"/>
    <fmt:message bundle="${loc}" key="local.buttdeactiv" var="buttdeactiv"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.promo" var="promo"/>
    <fmt:message bundle="${loc}" key="local.promoEnd" var="promoEnd"/>

</head>
<body>
<c:import url="/header"/>
<br>
<div class="jumbotron">
    <div class="container">
<!-- проверить информацию -->

        <h2>  ${userName.name} ${userName.surname} tariffs list! </h2>

    </div>
</div>
<div class="container">

    <div class="col-md-8">


            <table class="table table-responsive table-striped">

                <tr class="active">
                    <div class="form-group">
                        <th><label>№</label></th>
                    </div>
                    <div class="form-group">
                        <th><label>${nametarif}</label></th>
                    </div>
                    <div class="form-group">
                        <th><label>${price}</label></th>
                    </div>
                    <div class="form-group">
                        <th><label>${promo}</label></th>
                    </div>
                    <div class="form-group">
                        <th><label>${promoEnd}</label></th>
                    </div>
                    <div class="form-group">
                        <th><label>${date}</label></th>
                    </div>
                    <th></th>
                </tr>
                <c:if test="${userTariffs!=null}">
                <c:forEach var="tariffs" items="${userTariffs}" varStatus="counter">

                    <tr>

                        <div class="form-group">
                            <td>${counter.count} </td>
                        </div>
                        <div class="form-group">
                            <td>${tariffs.tariff.name}</td>
                        </div>
                        <div class="form-group">
                            <td>${tariffs.tariff.price}</td>
                        </div>
                        <div class="form-group">
                            <td>${tariffs.promotion.description}</td>
                        </div>
                        <div class="form-group">
                            <td>${tariffs.promotion.dateEnd}</td>
                        </div>
                        <div class="form-group">
                            <td>${tariffs.note.connectionDate}</td>
                        </div>

                    </tr>

                </c:forEach>
                </c:if>
            </table>

    </div>
    <br>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="go_to_page">
        <input type="hidden" name="go_to_page" value="users">
        <input type="hidden" name="userNumPage" value="${requestScope.userNumPage}">
        <input type="submit" class="btn btn-success" value="${back}"/>
    </form>
</div>

<c:import url="/footer"/>
</body>
</html>
