<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" errorPage="/page/error.jsp" %>
<%@page import="by.epam.zhalabkevich.my_telecom.bean.User" %>
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
    <style>
        body {
            padding: 5%;
        }
    </style>
    <script type="text/javascript">
        function valid(form) {
            let fail = false;
            let name = form.name.value;
            let surname = form.surname.value;
            let phone = form.phone.value;
            let email = form.email.value;
            let password1 = form.password1.value;
            let password2 = form.password2.value;
            let email_pattern = /^[A-Z0-9._%+-]+@[A-Z0-9-]+.+.[A-Z]{2,4}$/i;
            let phone_pattern = /^\+?(\d{1,3})?[- .]?\(?(?:\d{2,3})\)?[- .]?\d\d\d[- .]?\d\d\d\d$/;
            let password_pattern = /[0-9a-zA-Z!@#$%^&*]{6,}/;
            let login_pattern = /[0-9a-zA-Z]{4,10}/;
            if (name === "" || name === " ") {
                fail = "Name is incorrect";
            } else if (surname === "" || surname === " ") {

                fail = "Surname is incorrect";
            } else if (phone_pattern.test(phone) === false) {

                fail = "Incorrect phone format";
            } else if (email_pattern.test(email) === false) {
                fail = "Incorrect email";

            } else if (password1 !== password2) {
                fail = "Passwords not equals";
            }
            if (fail) {
                alert(fail);
                return false;
            }

        }
    </script>

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
    <fmt:message bundle="${loc}" key="local.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.address" var="address"/>
    <fmt:message bundle="${loc}" key="local.email" var="email"/>

    <fmt:message bundle="${loc}" key="local.password" var="password"/>
    <fmt:message bundle="${loc}" key="local.password1" var="password1"/>
    <fmt:message bundle="${loc}" key="local.password2" var="password2"/>
    <fmt:message bundle="${loc}" key="local.message1edit" var="editmess1"/>
    <fmt:message bundle="${loc}" key="local.message2edit" var="editmess2"/>
    <fmt:message bundle="${loc}" key="local.message3user" var="userinfo"/>

    <title>${edit}</title>
</head>

<body>

<c:import url="/header"/>
<br>

<div class="jumbotron">
    <div class="container">
        <h1>${editmess1}</h1>
        <p>${editmess2}</p>
    </div>
</div>


<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${userinfo}</h2>
            <p style="color: red">${requestScope.updateMessage}</p>
            <p style="color: red">${requestScope.errorPasswordMessage}</p>
            <form action="controller" method="post" id="update" onsubmit="valid(document.getElementById('update'))">
                <input type="hidden" name="command" value="update_user_info">
                <div class="form-group"><label data-toggle="tooltip" title="${name}">  ${name}:</label>
                    <input type="text" data-toggle="tooltip" title="${surname}" id="name" name="name"
                           value="${user.name}"></div>
                </br>
                <div class="form-group"><label>  ${surname}:</label>
                    <input type="text" data-toggle="tooltip" title="${surname}" id="surname" name="surname"
                           value="${user.surname}"></div>
                </br>
                <div class="form-group"><label> ${phone}:</label>
                    <input type="text" data-toggle="tooltip" title="${phone}" id="phone" name="phone"
                           value="${user.phone}"></div>
                </br>
                <div class="form-group"><label>    ${address}:</label>
                    <input type="text" data-toggle="tooltip" title="${address}" id="address" name="address"
                           value="${user.address}"></div>
                </br>
                <div class="form-group"><label>    ${email}:</label>
                    <input type="text" data-toggle="tooltip" title="${email}" id="email" name="email"
                           value="${user.email}"></div>
                </br>
                </hr>
                <div class="form-group form-inline"><input class="btn btn-success" type="submit" value="${update}"/>
                </div>
                </br>
            </form>
            <form action="controller" method="post" id="update_pass"
                  onsubmit="valid(document.getElementById('update_pass'))">
                <input type="hidden" name="command" value="update_password">
                <div class="form-group"><label>   ${password1}:</label>
                    <input type="password" data-toggle="tooltip" title="${password1}>6" id="password1" name="password1"
                           value=""></div>
                </br>
                <p style="color: red">${requestScope.passwordError}</p>
                <div class="form-group"><label>  ${password2}:</label>
                    <input type="password" data-toggle="tooltip" title="${password1}>6" id="password2" name="password2"
                           value=""></div>
                </br>

                <div class="form-group form-inline"><input class="btn btn-success" type="submit"
                                                           value="${update} ${password}"/></div>
                </br>
            </form>
            <div class="form-group form-inline">
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="show_user_info">
                    <input type="submit" class="btn btn-danger" value="${cancel}"/>
                </form>
            </div>
            </form>


        </div>

    </div>
</div>
<br>
<br>
<c:import url="/footer"/>
</body>
</html>





