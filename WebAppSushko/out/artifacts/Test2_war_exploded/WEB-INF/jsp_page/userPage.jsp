<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User page</title>
</head>
<body>
<form action="/userServlet" method="get">
    <p align="center"><h1>${user}:  <c:out value="${nameUser}"></c:out></h1></p><br>
    <input type="submit" name="downloadTAriff" value="${downlMyTariff}"><input type="submit" name="downloadAllTAriff" value="${downlAllTariff}"><br>
    <c:out value="${status}:  ${statusUser}"></c:out><br>
    <c:out value="${balance}:  ${balanceUser}"></c:out><br>
    <c:out value="${username} - ${loginUser}"></c:out><br>
    <c:out value="${tell}:  ${tellUser}"></c:out><br>
    <c:out value="${adress}:  ${adressUser}"></c:out><br>
    <c:out value="${allMoneyToCosts}: ${allMoney}"></c:out><br>
    </form>
<form action="/userServlet" method="post">
    <input type="submit" name="pay" value="${pay}">
</form>
<form action="/userServlet" method="get">
    <br>
    <br>
    <h1>${myTariff}</h1>
    <br>
    <TABLE BORDER>
        <TR>
            <TD>${id}</TD>
            <TD>${name}</TD>
            <TD>${price}</TD>
            <TD>${descript}</TD>
        </TR>
        <c:forEach var="p" items="${arrServiceForUser}">
            <TR>
                <TD>${p.id}</TD>
                <TD>${p.nameService}</TD>
                <TD>${p.price}</TD>
                <TD>${p.comment}</TD>
            </TR>
        </c:forEach>
    </TABLE>
    <select name="deleteMyService" size="1">
        <c:forEach var="p" items="${arrServiceForUser}">

            <option name="${p.id}" >${p.nameService}</option>

        </c:forEach>
    </select>

    <input type="submit" name="deleteService" value="${delTariff}">
    <br>
   <select name="sort" size="1">
        <option name="nameAZ" >Name A-Z</option>
        <option name="nameZA" >Name Z-A</option>
        <option name="price12">Price 1-9</option>
        <option name="price21">Price 9-1</option>
    </select>

    <input type="submit" name="sorted" value="${sorted}">
    <br>
    <br>
    <h1>${myService}</h1>
    <TABLE BORDER>
        <TR>
            <TD>${id}</TD>
            <TD>${name}</TD>
            <%--<TD>Price</TD>--%>
            <TD>${descript}</TD>
        </TR>
        <c:forEach var="p" items="${arrTariff}">
            <TR>
                <TD>${p.id}</TD>
                <TD>${p.nameTariff}</TD>
                <%--<TD>${p.price}</TD>--%>
                <TD>${p.comment}</TD>
            </TR>
        </c:forEach>
    </TABLE>
    <%--<select name="deleteTafiff" size="1">--%>
        <%--<c:forEach var="p" items="${arrTariff}">--%>

            <%--<option name="${p.id}" >${p.nameTariff}</option>--%>

        <%--</c:forEach>--%>

    <%--</select><input type="submit" name="deleteTarif" value="Delete selected My tariffs">--%>


    <br>
    <h1>${allService}</h1>
    <TABLE BORDER>
        <TR>
            <TD>${id}</TD>
            <TD>${name}</TD>
            <%--<TD>Price</TD>--%>
            <TD>${descript}</TD>
        </TR>
        <c:forEach var="p" items="${otherTariffs}">
            <TR>
                <TD>${p.id}</TD>
                <TD>${p.nameTariff}</TD>
                <%--<TD>${p.price}</TD>--%>
                <TD>${p.comment}</TD>
            </TR>
        </c:forEach>
    </TABLE>
    <br>
    <select name="selectTafiff" size="1">
        <c:forEach var="p" items="${otherTariffs}">

            <option name="${p.id}" >${p.nameTariff}</option>

        </c:forEach>
    </select>
    <input type="submit" name="openTariff" value="${openTariff}">
    <br>
    <input type="submit" name="exit" value="${exit}"><br>
</form>
</body>

</html>
