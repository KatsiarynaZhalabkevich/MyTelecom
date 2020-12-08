<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="by.epam.zhalabkevich.my_telecom.bean.dto.UserAccount" %>
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
    <style>
        body {
            padding: 1%;
        }
    </style>
    <script>
        function valid(form) {
            var fail = false;
            var balance = form.balance.value;
            if (balance === null && balance === '') {
                fail = "Field is empty!";
            } else if (balance.toString().includes(',')) {
                fail = "Please, use '.' instead of ','!";
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
    <fmt:message bundle="${loc}" key="local.tarif" var="tarif"/>
    <fmt:message bundle="${loc}" key="local.logout" var="logout"/>
    <fmt:message bundle="${loc}" key="local.adminpage" var="admin"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.showuser" var="title"/>
    <fmt:message bundle="${loc}" key="local.message1admin" var="hello"/>
    <fmt:message bundle="${loc}" key="local.message1showusers" var="mess1"/>
    <fmt:message bundle="${loc}" key="local.message3admin" var="mess2"/>
    <fmt:message bundle="${loc}" key="local.message4admin" var="mes4admin"/>
    <fmt:message bundle="${loc}" key="local.payment" var="payment"/>
    <fmt:message bundle="${loc}" key="local.block" var="block"/>
    <fmt:message bundle="${loc}" key="local.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.address" var="address"/>
    <fmt:message bundle="${loc}" key="local.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.balancename" var="balance"/>
    <fmt:message bundle="${loc}" key="local.activ" var="active"/>
    <fmt:message bundle="${loc}" key="local.blockstatus" var="blockstat"/>
    <fmt:message bundle="${loc}" key="local.status" var="status"/>
    <fmt:message bundle="${loc}" key="local.updstatus" var="updstatus"/>
    <fmt:message bundle="${loc}" key="local.buttupdbalance" var="updbalance"/>
    <fmt:message bundle="${loc}" key="local.userstariffs" var="usertariff"/>
    <fmt:message bundle="${loc}" key="local.deleteuser" var="deleteuser"/>
    <fmt:message bundle="${loc}" key="local.prevpage" var="prev"/>
    <fmt:message bundle="${loc}" key="local.nextpage" var="next"/>
    <fmt:message bundle="${loc}" key="local.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.find" var="find"/>
    <fmt:message bundle="${loc}" key="local.back" var="back"/>

    <title>${title}</title>
</head>
<body>

<c:import url="/header"/>

<div class="jumbotron">
    <div class="container">
        <h1>${hello}, ${user.name}!</h1>
        <p>${mess1} </p>

    </div>
</div>

<div class="container">

    <div class="row">
        <div class="col-md-12">
            <h2>${mess2}</h2>
            <p>${mes4admin}</p>
            <br>
        </div>
    </div>

    <div>
        <p style="color: #4cae4c">${requestScope.paymentMessage}</p>
        <p style="color: #4cae4c">${requestScope.blockMessage}</p>
        <p style="color: #4cae4c">${requestScope.updBalanceMessage}</p>
        <p style="color: #4cae4c">${requestScope.updStatusMessage}</p>
        <p style="color: #4cae4c">${requestScope.deleteUserMessage}</p>
        <p style="color: #4cae4c">${requestScope.errorMessage}</p>
    </div>

    <div class="row">
        <div class="col-md-3">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="payment">
                <input type="submit" class=" btn btn-danger" value="${payment}">
            </form>
        </div>
        <div class="col-md-3">
            <form action="controller" method="post">
                <input type="hidden" name="command" value="block">
                <input type="submit" class="btn btn-danger" value="${block}">
            </form>
        </div>
    </div>
</div>
<div>
    <table class="table-responsive ">
        <tr>
            <td>

                <c:if test="${requestScope.userNumPage>1}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_users"/>
                        <input type="hidden" name="userNumPage" value="${requestScope.userNumPage-1}">
                        <input type="submit" class="btn-link" style="color: black" value="${prev}"/>
                    </form>
                </c:if>
            </td>
            <td>
                <form action="#">
                    <input type="submit" class="btn-link" style="color: black"
                           value="${requestScope.userNumPage}">
                </form>
            </td>
            <td>
                <c:if test="${!requestScope.isLastPageUser}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_users"/>
                        <input type="hidden" name="userNumPage" value="${requestScope.userNumPage+1}">
                        <input type="submit" class="btn-link" style="color: black" value="${next}"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>


    <table class="table-responsive table-striped table-bordered ">
        <tr class="active">
            <th>№</th>
            <th>${name}</th>
            <th>${surname}</th>
            <th>${phone}</th>
            <th>${address}</th>
            <th>${email}</th>
            <th>${balance}, $</th>
            <th>${status}</th>
            <th>${tarif}</th>
            <th></th>
            <th></th>
        </tr>

        <c:if test="${usersAccounts!= null}">
            <c:forEach var="users" items="${usersAccounts}" varStatus="counter">
                <tr>
                    <td> ${users.user.id}</td>
                    <td>${users.user.name}</td>
                    <td>${users.user.surname}</td>
                    <td>${users.user.phone}</td>
                    <td>${users.user.address}</td>
                    <td>${users.user.email}</td>

                    <td>
                        <form action="controller" method="post" id="bal"
                              onsubmit="valid(document.getElementById('bal'))">
                                <br/>
                            <input type="hidden" name="command" value="change_balance">
                            <input type="hidden" name="userNumPage" value="${requestScope.userNumPage}">
                            <input type="hidden" name="user_id" value="${users.user.id}">
                            <input type="hidden" name="old_balance" value="${users.account.balance}">
                            <input type="text" name="balance" value="${users.account.balance}"><br>
                            <input type="submit" class="btn btn-info btn-md" name="upd"
                                   value="${updbalance}">
                        </form>
                    </td>
                    <td>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="change_status">
                            <input type="hidden" name="user_id" value="${users.user.id}">
                            <input type="hidden" name="userNumPage" value="${requestScope.userNumPage}">

                            <c:choose>
                                <c:when test="${users.account.status eq 'ACTIVE'}">
                                    <input type="radio" class="radio-button" name="active" value="ACTIVE"
                                           checked>${active}
                                    <br>
                                    <input type="radio" сlass="radio-button active" name="active"
                                           value="BLOCKED">${blockstat}
                                </c:when>
                                <c:otherwise>
                                    <input type="radio" class="radio-button" name="active"
                                           value="ACTIVE">${active}
                                    <br>
                                    <input type="radio" сlass="radio-button active" name="active"
                                           value="BLOCKED"
                                           checked>${blockstat}
                                </c:otherwise>
                            </c:choose>
                            <input type="submit" class="btn btn-info btn-md" name="" value="${updstatus}">
                        </form>
                    </td>
                    <td>
                        <form action="controller" method="get">
                            <input type="hidden" name="command" value="show_user_tariffs">
                            <input type="hidden" name="userNumPage" value="${requestScope.userNumPage}">
                            <input type="hidden" name="account_id" value="${users.account.id}">
                            <input type="hidden" name="user_id" value="${users.user.id}">
                            <input type="submit" class="btn btn-info btn-md" value="${usertariff}">
                        </form>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="delete_user">
                            <input type="hidden" name="userNumPage" value="${requestScope.userNumPage}">
                            <input type="hidden" name="user_id" value="${users.user.id}">
                            <input type="submit" class="btn btn-danger btn-md" name=""
                                   value="${deleteuser}">
                        </form>
                    </td>

                    <td>


                    </td>

                </tr>
            </c:forEach>
        </c:if>
        <tr>
            <form action="controller" method="get">
                <td></td>
                <input type="hidden" name="command" value="find_user">
                <td><input type="text" name="name" placeholder="${name}" value=""></td>
                <td><input type="text" name="surname" placeholder="${surname}" value=""></td>
                <td><input type="text" name="phone" placeholder="${phone}" value=""></td>
                <td><input type="text" name="address" placeholder="${address}" value=""></td>
                <td><input type="text" name="email" placeholder="${email}" value=""></td>
                <td><input type="submit" class="btn btn-success btn-md" value="${find}"></td>
                <td></td>
                <td></td>
            </form>
        </tr>
    </table>


    <br>
    <div class="container">
        <form action="controller" method="get">
            <input type="hidden" name="command" value="go_to_page"/>
            <input type="hidden" name="go_to_page" value="users/admin"/>
            <input type="submit" class="btn btn-success" value="${back}"/>
        </form>
    </div>
</div>
<br>
<br><br>

<c:import url="/footer"/>
</body>
</html>