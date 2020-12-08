<%@page import="by.epam.zhalabkevich.my_telecom.bean.User" %>
<%@page import="by.epam.zhalabkevich.my_telecom.bean.Account" %>
<%@page import="by.epam.zhalabkevich.my_telecom.bean.Tariff" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
        function valid(form) {
            let fail = false;
            let balance = form.balance.value;
            if (balance === null && balance === '') {
                fail = "Field is empty!";
            } else if (balance.toString().includes(',')) {
                fail = "Please, use '.' instead of ','!";
            }
            if(fail){
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
    <fmt:message bundle="${loc}" key="local.message1admin" var="mes1admin"/>
    <fmt:message bundle="${loc}" key="local.message2user" var="mes2user"/>
    <fmt:message bundle="${loc}" key="local.message3user" var="mes3user"/>
    <fmt:message bundle="${loc}" key="local.privpage" var="priv"/>
    <fmt:message bundle="${loc}" key="local.login" var="login"/>
    <fmt:message bundle="${loc}" key="local.name" var="name"/>
    <fmt:message bundle="${loc}" key="local.surname" var="surname"/>
    <fmt:message bundle="${loc}" key="local.phone" var="phone"/>
    <fmt:message bundle="${loc}" key="local.email" var="email"/>
    <fmt:message bundle="${loc}" key="local.address" var="address"/>
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


    <link rel="stylesheet" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="webjars/js/jquery-3.4.1.slim.min.js"></script>
    <script src="webjars/js/popper.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.css" rel="stylesheet">

    <title>${priv}</title>
</head>
<body>
<c:import url="/header"/>


<div class="jumbotron">
    <div class="container">
        <h1>${mes1admin}, ${user.name} ${user.surname}!</h1>
        <p>${mes2user} </p>
    </div>
</div>
<div style="color: red" align="center"><c:out value="${requestScope.errorMessage}"/></div>
<p style="color: blue" align="center">${requestScope.updateMessage}</p>


<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${mes3user}</h2>
            <form>

                <div class="form-group"><label> ${name}: </label>
                    ${user.name}</div>
                <div class="form-group"><label>${surname}: </label>
                    ${user.surname}</div>
                <div class="form-group"><label>${phone}: </label>
                    ${user.phone}</div>
                <div class="form-group"><label>${address}: </label>
                    ${user.address}</div>
                <div class="form-group"><label> ${email}: </label>
                    ${user.email} </div>
                <div class="form-group"><label>${accountStat}: </label> ${account.status}</div>
                <div class="form-group"><label>${registrationDate}: </label> ${account.registrationDate}</div>
            </form>

            <form action="controller" method="get">
                <input type="hidden" name="command" value="edit_profile">
                <input class="btn btn-success" type="submit" value="${edit}"/>
            </form>
        </div>
        <div class="col-md-6">

            <form action="controller" method="post" id="bal"
                  onsubmit="valid(document.getElementById('bal'))">
                <p style="color: blue">${requestScope.updBalanceMessage}</p>
                <h2> ${balance}:</h2>
                <input type="hidden" name="command" value="update_balance"/>
                <p>${account.balance}</p>
                <input type="text" name="balance"/>
                <input class="btn btn-success" type="submit" name="send" value="${buttupdbalance}">

            </form>
            <br>
        </div>
    </div>
</div>
<div class="container">

    <div class="row">
        <div class="col-md-6">
            <h2>${mess4user}</h2>
            <c:if test="${tariffs!=null}">
                <div class="table-responsive">
                    <table>
                        <tr>
                            <td>
                                <c:if test="${requestScope.tariffNumPage>1}">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="show_user_info"/>
                                        <input type="hidden" name="tariffNumPage"
                                               value="${requestScope.tariffNumPage+1}">
                                        <input type="submit" class="btn-link" style="color: black" value="prev"/>
                                    </form>
                                </c:if>
                            </td>
                            <td>
                                <a href="#" style="color: black"><c:out value="${requestScope.tariffNumPage}"/> </a>
                            </td>
                            <td>
                                <c:if test="${!requestScope.isLastPageTariff}">
                                    <form action="controller" method="get">
                                        <input type="hidden" name="command" value="show_user_info"/>
                                        <input type="hidden" name="tariffNumPage"
                                               value="${requestScope.tariffNumPage+1}">
                                        <input type="submit" class="btn-link" style="color: black" value="next"/>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </table>
                </div>
                <table class="table table-striped table-responsive">
                    <tr class="active">
                        <div class="form-group">
                            <th><label>№</label></th>
                        </div>
                        <div class="form-group">
                            <th>${nametarif}</th>
                        </div>
                        <div class="form-group">
                            <th>${descrip}</th>
                        </div>
                        <div class="form-group">
                            <th>${price}</th>
                        </div>
                        <div class="form-group">
                            <th></th>
                        </div>
                    </tr>

                    <c:forEach var="tariffList" items="${tariffs}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td> ${tariffList.name}</td>

                            <td>${tariffList.description}</td>

                            <td>${tariffList.price}</td>
                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="add_note">
                                    <input type="hidden" name="tariff_id" value="${tariffList.id}">
                                    <input type="hidden" name="tariffNumPage"
                                           value="${requestScope.tariffNumPage}">
                                    <input type="hidden" name="account_id" value="${account.id}">
                                    <c:if test="${account.status!='BLOCKED'}">
                                        <input type="submit" class="btn btn-info" value="${buttactiv}">
                                    </c:if>
                                    <c:if test="${account.status=='BLOCKED'}">
                                        <input type="submit" class="btn btn-info disabled" data-toggle="tooltip"
                                               title="Your account is blocked! Check your balance or contact admin"
                                               value="${buttactiv}">
                                    </c:if>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
        <div class="col-md-6">

            <h2> ${mess5user}:</h2>
            <c:if test="${userTariffs!=null}">
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

                            <td>
                                <form action="controller" method="post">
                                    <input type="hidden" name="command" value="delete_note">
                                    <input type="hidden" name="note_id" value="${tariffs.note.id}">
                                    <input type="submit" class="btn btn-info" value="${buttdeactiv}">
                                </form>
                            </td>
                        </tr>

                    </c:forEach>

                </table>
            </c:if>

            <br>

        </div>

    </div>

    <br><br>
<%--    <c:if test="${user.id>0}">--%>

<%--        <form action="controller" method="get" id="delete"--%>
<%--              onsubmit="checkBalance(document.getElementById('delete'))">--%>
<%--            <input type="hidden" name="command" value="delete_user">--%>
<%--            <input type="hidden" name="user_id" value="${user.id}">--%>
<%--            <input type="hidden" name="balance" value="${account.balance}">--%>
<%--            <input type="submit" class="btn-link" value="You can delete your account">--%>
<%--        </form>--%>
<%--    </c:if>--%>

</div>
<br>

<c:import url="/footer"/>


</body>
</html>
