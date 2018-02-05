

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%--<meta charset="UTF-8">--%>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" charset="UTF-8">
    <title>Admin page</title>
</head>
<body>
<form action="/adminServlet" method="get">
    <h1>${administrator}  <c:out value="${nameAdmin}"></c:out></h1>
    <input type="submit" name="newAdmin" value="${newAdmin}">

    <h1>${allTariff}</h1>
    <TABLE BORDER>
        <TR>
            <TD>${id}</TD>
            <TD>${name}</TD>
            <TD>${price}</TD>
            <TD>${descript}</TD>
        </TR>
        <c:forEach var="p" items="${allService}">
            <TR>
                <TD>${p.id}</TD>
                <TD>${p.nameService}</TD>
                <TD>${p.price}</TD>
                <TD>${p.comment}</TD>
            </TR>
        </c:forEach>
    </TABLE>
    <br>
    <select name="resChangeService" size="1">
        <c:forEach var="p" items="${allService}">
            <option name="${p.id}">${p.nameService}</option>
        </c:forEach>
    </select><input type="submit" name="changeService" value="${changeT}">    <input type="submit" name="deleteService" value="${delTariff}"><br>
    <br>
    <input type="submit" name="newService" value="${newTariff}"><br>

    <h1>${tableService}</h1>
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
    <br>

    <select name="resChangeTariff" size="1">
        <c:forEach var="p" items="${arrTariff}">
            <option name="${p.id}">${p.nameTariff}</option>
        </c:forEach>
    </select><input type="submit" name="changeTariff" value="${changeS}">
    </select><input type="submit" name="openTariff" value="${openTforS}">
    </select><input type="submit" name="deleteTariff" value="${deleteS}">
    <br>
    <input type="submit" name="newTariff" value="${newS}"><br>

    <select name="sort" size="1">
        <option name="nameAZ" >Name A-Z</option>
        <option name="nameZA" >Name Z-A</option>
        <%--<option name="price12">Price 1-9</option>--%>
        <%--<option name="price21">Price 9-1</option>--%>
    </select><input type="submit" name="sorted" value="${sorted}">
    <br>
    <h1>${tableU}</h1>
    <TABLE BORDER>
        <TR>
            <TD>${id}</TD>
            <TD>${username}</TD>
            <TD>${fio}</TD>
            <TD>${balance}</TD>
            <TD>${tell}</TD>
            <TD>${adress}</TD>
            <TD>${block}</TD>
        </TR>
        <c:forEach var="p" items="${arrUser}">
            <TR>
                <TD>${p.id}</TD>
                <TD>${p.login}</TD>
                <TD>${p.fio}</TD>
                <TD>${p.balance}</TD>
                <TD>${p.tell}</TD>
                <TD>${p.adress}</TD>
                <TD>${p.block}</TD>
            </TR>
        </c:forEach>
    </TABLE>
    <br><input placeholder="Enter user ID" type="submit" name="changeuser" value="${editU}"><input type="number" name="numberUserChange" size="5" step="1">
    <br><input type="submit" name="newUser" value="${newU}">
    <br>
    <input type="submit" name="exit" value="${exit}"><br>

</form>
</body>
