<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/page/error.jsp"  %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tags.tld" prefix='mytag' %>
<html>
<head>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="local" var="loc" />

    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.message1admin" var="mes1admin"/>
    <fmt:message bundle="${loc}" key="local.message2admin" var="mes2admin"/>
    <fmt:message bundle="${loc}" key="local.buttontarif" var="buttarif"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.butuser" var="butuser"/>
    <fmt:message bundle="${loc}" key="local.message3admin" var="mes3admin"/>
    <fmt:message bundle="${loc}" key="local.message4admin" var="mes4admin"/>
    <fmt:message bundle="${loc}" key="local.message5admin" var="mes5admin"/>
    <fmt:message bundle="${loc}" key="local.statistic" var="statistic"/>
    <fmt:message bundle="${loc}" key="local.learnstat" var="learnstat"/>
    <fmt:message bundle="${loc}" key="local.next" var="next"/>


    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="webjars/docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">
    <style>
        body{
            padding: 5px;
        }
    </style>

    <title>${admin}</title>
</head>
<body>

<c:import url="/header"/>
<div class="container"><h3 align="right" ><mytag:date/></h3></div>

<div class="jumbotron">
    <div class="container">
        <h1>${mes1admin}, ${user.getName()}!</h1>
        <p>${mes2admin} </p>

        <div style="color: red" align="left" >  <c:out value="${sessionScope.errorMessage}" /></div>
        <c:remove var="errorMessage" scope="session" />

    </div>
</div>

<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${mes3admin}</h2>
            <p>${mes4admin}</p>
            <p>
            <form action="controller" method="get">
                <input type="hidden" name="command" value="show_users"/>
                <input class="btn btn-info btn-md" type="submit" value="${butuser} &raquo;"/>

            </form>


        </div>
        <div class="row">
            <div class="col-md-6">
                <h2>${tarif}</h2>
                <p>${mes5admin}</p>
                <p>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="show_tariffs"/>
                    <input class="btn btn-info btn-md" type="submit" value="${buttarif} &raquo;"/>

                </form>


            </div>


        </div>

    </div>


    <div class="col-md-6">
        <h2>${statistic}</h2>
        <p>${learnstat} </p>
        <p>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="statistic"/>
            <input class="btn btn-info btn-md" role="button" type="submit" name="statistic"
                   value="${next} &raquo;"/>
        </form>

    </div>
</div>
<br>
<br>

<c:import url="/footer"/>
</body>
</html>
