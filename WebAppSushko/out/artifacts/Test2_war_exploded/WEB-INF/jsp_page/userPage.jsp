<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 13.01.2018
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User page</title>
</head>
<body>
<form action="/userServlet" method="get">
    <p align="center"><h1>User:  <c:out value="${nameUser}"></c:out></h1></p><br>
    <input type="submit" name="downloadTAriff" value="Download My tariff"><input type="submit" name="downloadAllTAriff" value="Download All tariff"><br>
    <c:out value="Status:  ${statusUser}"></c:out><br>
    <c:out value="Balance:  ${balanceUser}"></c:out><br>
    <c:out value="Login - ${loginUser}"></c:out><br>
    <c:out value="tel.:  ${tellUser}"></c:out><br>
    <c:out value="Adress:  ${adressUser}"></c:out><br>
    <c:out value="All money to costs: ${allMoney}"></c:out><br>
    </form>
<form action="/userServlet" method="post">
    <input type="submit" name="pay" value="PAY">
</form>
<form action="/userServlet" method="get">
    <br>
    <br>
    <h1>My tariff</h1>
    <br>
    <TABLE BORDER>
        <TR>
            <TD>ID</TD>
            <TD>Name</TD>
            <TD>Price</TD>
            <TD>Description</TD>
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

    <input type="submit" name="deleteService" value="Delete tariff">
    <br>
   <select name="sort" size="1">
        <option name="nameAZ" >Name A-Z</option>
        <option name="nameZA" >Name Z-A</option>
        <option name="price12">Price 1-9</option>
        <option name="price21">Price 9-1</option>
    </select>

    <input type="submit" name="sorted" value="Sorted">
    <br>
    <br>
    <h1>My service</h1>
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
    <%--<select name="deleteTafiff" size="1">--%>
        <%--<c:forEach var="p" items="${arrTariff}">--%>

            <%--<option name="${p.id}" >${p.nameTariff}</option>--%>

        <%--</c:forEach>--%>

    <%--</select><input type="submit" name="deleteTarif" value="Delete selected My tariffs">--%>


    <br>
    <h1>All services</h1>
    <TABLE BORDER>
        <TR>
            <TD>ID</TD>
            <TD>Name</TD>
            <%--<TD>Price</TD>--%>
            <TD>Comment</TD>
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
    <input type="submit" name="openTariff" value="Open tariff">
    <br>
    <input type="submit" name="exit" value="exit"><br>
</form>
</body>

</html>
