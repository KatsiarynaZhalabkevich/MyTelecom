<%@ page contentType="text/html;charset=UTF-8" language="java"
errorPage="/page/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <script type="module" src="../js/loginValidation.js"></script>
    <script type="module" src="../js/userValidation.js"></script>


    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="loc"/>
    <fmt:message bundle="${loc}" key="local.mainpage" var="main"/>
    <fmt:message bundle="${loc}" key="local.registration" var="registr"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.editpage" var="edit"/>
    <fmt:message bundle="${loc}" key="local.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.updateinfo" var="update"/>
    <fmt:message bundle="${loc}" key="local.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.placeholderpas" var="password"/>
    <fmt:message bundle="${loc}" key="local.buttonsignin" var="signin"/>
    <fmt:message bundle="${loc}" key="local.tarif" var="tariffs"/>
    <fmt:message bundle="${loc}" key="local.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.address" var="address"/>
    <fmt:message bundle="${loc}" key="local.password1" var="password1"/>
    <fmt:message bundle="${loc}" key="local.password2" var="password2"/>
    <fmt:message bundle="${loc}" key="local.cancel" var="cancel"/>
    <fmt:message bundle="${loc}" key="local.message1registration" var="mes1"/>
    <fmt:message bundle="${loc}" key="local.message2registration" var="mes2"/>
    <fmt:message bundle="${loc}" key="local.message3registation" var="mes3"/>
    <fmt:message bundle="${loc}" key="local.message4registration" var="mes4"/>

    <title>${registr}</title>
</head>

<body>
<c:import url="/header"/>
<br>

<div class="jumbotron">
    <div class="container">
        <h1>${mes1}</h1>
        <p>${mes2} </p>
    </div>
</div>


<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${mes3}</h2>
            <form action="controller" method="post" id="registration"  onsubmit="valid(document.getElementById('registration'))">
                <input type="hidden" name="command" value="registration"/>
                <p style="color: red">${errorMessage}</p>
                <div class="form-group">
                    <label >${name}:</label>
                </div>
                <div class="form-group">
                    <input type="text" data-toggle="tooltip" title="${name}" name="name" value="user.name" id="name">
                </div>
                <div class="form-group"><label > ${surname}:</label></div>
                <div class="form-group"><input type="text" data-toggle="tooltip" title="${surname}" name="surname" value="user.surname" id="surname"></div>
                <div class="form-group"><label > ${phone}:</label></div>
                <div class="form-group"><input type="text" name="phone" value="user.phone" data-toggle="tooltip" title="${phone} +375..." id="phone"></div>
                <div class="form-group"><label > ${address}:</label></div>
                <div class="form-group"><input type="text" data-toggle="tooltip" title="${address}" name="address" value="user.address" id="address"></div>
                <div class="form-group"><label > ${email}:</label></div>
                <div class="form-group"><input type="text" data-toggle="tooltip" title="${email}" name="email" value="user.email" id="email"></div>

                <div class="form-group"><label >${login}:</label></div>
                <div class="form-group"><input type="text" name="login" value="" data-toggle="tooltip" title="${login} > 4" id="login"></div>
                <p style="color: red">${errorLoginMessage}</p>
                <div class="form-group"> <label >${password1}:</label></div>
                <div class="form-group"><input type="password" name="password1" value="" data-toggle="tooltip" title="${password} > 6" id="password"></div>
                <div class="form-group"><label > ${password2}:</label></div>
                <div class="form-group"><input type="password" name="password2" value="" id="password2"></div>
                <p style="color: red">${errorPasswordMessage}</p>

                <div class="form-group"><input class="btn btn-success" type="submit"
                                               value="${registr}"/></div>

            </form>
            <br>
            <br>
        </div>
        <div class="col-md-6">
            <h2>${signin}</h2>
            <p>${mes4} </p>
            <form action="controller" method="post" id="sign" onsubmit="return sign(document.getElementById('sign'))" >

                <input type="hidden" name="command" value="authorization"/>
                <div class="form-group">${login}:</div>
                <div class="form-group"><input type="text" data-toggle="tooltip" title="${login}" name="login" id ="login1" value=""/></div>
                <div class="form-group">${password}:</div>
                <div class="form-group"><input type="password" data-toggle="tooltip" title="${password}" name="password" id ="password1"  value=""/></div>
                <p style="color: red">${loginErrorMessage}</p>
                <div class="form-group"><input class="btn-success btn" type="submit" name="signup"
                                               value="${signin}">
                </div>

            </form>
        </div>
    </div>
    </form>
<%--   TODO Урл меняется и и команда не приходит куда надо--%>
    <form action="main" method="get">
        <input type="submit" class="btn btn-danger" value="${cancel}" />
    </form>
</div>


<br>
<br>
<br>
<br>

<c:import url="/footer"/>

<script>
    $(document).ready (function () {
$('[data-toggle="tooltip"]').tooltip();
    });
</script>
</body>
</html>
