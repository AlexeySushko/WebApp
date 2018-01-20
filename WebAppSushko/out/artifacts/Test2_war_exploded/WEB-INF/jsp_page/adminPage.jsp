

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%--<meta charset="UTF-8">--%>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" charset="UTF-8">
    <title>Admin page</title>
</head>
<body>
<form action="/adminServlet" method="get">
    <h1>Administrator:  <c:out value="${nameAdmin}"></c:out></h1>
    <input type="submit" name="newAdmin" value="New Admin">

    <h1>All TARIFF</h1>
    <TABLE BORDER>
        <TR>
            <TD>ID</TD>
            <TD>Name</TD>
            <TD>Price</TD>
            <TD>Description</TD>
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
    </select><input type="submit" name="changeService" value="Change tariff">    <input type="submit" name="deleteService" value="Delete tariff"><br>
    <br>
    <input type="submit" name="newService" value="New tariff"><br>

    <h1>Table SERVICES</h1>
    <TABLE BORDER>
        <TR>
            <TD>ID</TD>
            <TD>Name</TD>
            <%--<TD>Price</TD>--%>
            <TD>Description</TD>
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
    </select><input type="submit" name="changeTariff" value="Change service">
    </select><input type="submit" name="openTariff" value="Open tariff for service">
    </select><input type="submit" name="deleteTariff" value="Delete service">
    <br>
    <input type="submit" name="newTariff" value="New service"><br>

    <select name="sort" size="1">
        <option name="nameAZ" >Name A-Z</option>
        <option name="nameZA" >Name Z-A</option>
        <%--<option name="price12">Price 1-9</option>--%>
        <%--<option name="price21">Price 9-1</option>--%>
    </select><input type="submit" name="sorted" value="Sorted">
    <br>
    <h1>Table USERS</h1>
    <TABLE BORDER>
        <TR>
            <TD>ID</TD>
            <TD>Login</TD>
            <TD>FIO</TD>
            <TD>Balance</TD>
            <TD>Telephone</TD>
            <TD>Adress</TD>
            <TD>Block</TD>
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
    <br><input placeholder="Enter user ID" type="submit" name="changeuser" value="Edit user"><input type="number" name="numberUserChange" size="5" step="1">
    <br><input type="submit" name="newUser" value="New User">
    <br>
    <input type="submit" name="exit" value="exit"><br>

</form>
</body>
