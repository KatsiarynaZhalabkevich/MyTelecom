<%@ page contentType="text/html;charset=UTF-8" language="java"
         errorPage="page/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags.tld" prefix="m" %>


<!DOCTYPE html>
<html lang="en">
<head>
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

    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">

    <script  type="text/javascript">
        function valid(form) {
            console.log("inside validation js");
            let fail = false;
            let login = form.login.value;
            let password = form.password.value;
            if (login === "" || login === " ") {
                fail = "Please, insert your login";
            } else if (password === "" || password === " ") {
                fail = "Please, insert your password";
            }
            if (fail) {
                alert(fail);
                return false;
            }
        }
    </script>


    <title>My Telecom </title>
</head>

<body>
<c:import url="/header"/>
<br>
<br>
<br>
<div style="color: red" align="right"><c:out value="${requestScope.loginErrorMessage}"/></div>
<%--<c:remove var="loginErrorMessage" scope="session"/>--%>
<div style="color: orangered" align="center"><c:out value="${requestScope.errorMessage}"/>
<%--    <c:remove var="errorMessage" scope="session"/>--%>
</div>
<br>
<div class="container"><h3 align="right"><m:date/></h3></div>

<div class="jumbotron">

    <div class="container">
        <h1>${mes1index}</h1>
        <p>${mes2index}</p>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="show_tariffs"/>
            <input class="btn btn-info btn-lg" role="button" type="submit" name="show tariffs"
                   value="${buttarif} &raquo;"/>
        </form>

    </div>

</div>

</div>
<c:import url="/footer"/>

</body>
</html>
