<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <script type="module" src="../../js/tariffValidation.js"></script>

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
    <title>${admin}</title>
</head>
<body>
<c:import url="/header"/>
<br>

<div class="jumbotron">
    <div class="container">

        <p>${mess1} </p>

    </div>
</div>
<div>
    <p style="color: red" align="left">${requestScope.errorMessage}</p>
    <p style="color: #4cae4c" align="left">${requestScope.editMessage}</p>
    <p style="color: #4cae4c" align="left">${requestScope.deleteMessage}</p>
</div>
<div class="table-responsive">
    <table>
        <tr>
            <td>
                <c:if test="${requestScope.tariffNumPage>1}">
                    <form action="controller" method="get">
                        <input type="hidden" name="command" value="show_tariffs"/>
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
                        <input type="hidden" name="command" value="show_tariffs"/>
                        <input type="hidden" name="tariffNumPage" value="${requestScope.tariffNumPage+1}">
                        <input type="submit" class="btn-link" style="color: black" value="${next}"/>
                    </form>
                </c:if>
            </td>
        </tr>
    </table>
</div>
<div class="table-responsive">
    <table class="table table-striped ">
        <tr class="active">
            <th>#</th>
            <th>${name}</th>
            <th>${descr}</th>
            <th>${speed}</th>
            <th>${price}, $</th>
            <th>${discount}</th>
            <th></th>
            <th></th>
        </tr>


        <c:forEach var="tariffList" items="${tariffs}">
            <tr>
                <form action="controller" method="post" id="edit"
                      onsubmit="return valid(document.getElementById('edit'))">
                    <input type="hidden" name="command" value="edit_tariff">
                    <td>${tariffList.id}</td>
                    <td><input type="text" name="name" value="${tariffList.name}"/></td>
                    <td><input type="text" name="description" value="${tariffList.description}"/></td>
                    <td><input type="text" name="speed" value=" ${tariffList.speed}"/></td>
                    <td><input type="text" name="price" value="${tariffList.price}"/></td>
                        <%--                    <td><input type="text"  name="discount" value="${tariffList.discount}"/></td>--%>
                    <input type="hidden" name="tariff_id" value="${tariffList.id}">
                    <td><input type="submit" class="btn btn-md btn-info" value="${edit}"></td>
                </form>
                <td>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="delete_tariff">
                        <input type="hidden" name="tariff_id" value="${tariffList.id}">
                        <input type="submit" class="btn btn-md btn-danger" value="${delete}">
                    </form>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <form action="controller" method="post" id="add" onsubmit="return valid(document.getElementById('add')) ">
                <input type="hidden" name="command" value="add_tariff">
                <td></td>
                <td><input type="text" id="name" placeholder="${name}" class="form-control" name="name"/></td>
                <td><input type="text" id="description" placeholder="${descr}" class="form-control"
                           name="description"/></td>
                <td><input type="number" id="speed" placeholder="${speed}" class="form-control" name="speed"/></td>
                <td><input type="number" id="price" placeholder="${price}" class="form-control" name="price"/></td>
                <td><input type="number" id="discount" placeholder="${discount}" class="form-control" name="discount"/>
                </td>
                <td><input type="submit" class="btn btn-md btn-success" value="${addtariff}"></td>
            </form>
        </tr>
    </table>
</div>

<form action="admin" method="get">
    <input type="hidden" name="command" value="go_to_page"/>
    <input type="hidden" name="go_to_page" value="users/admin">
    <input type="submit" class="btn btn-success" value="${back}"/>
</form>


<c:import url="/footer"/>
</body>
</html>
