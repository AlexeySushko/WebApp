<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 17.01.2018
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tariff</title>
</head>
<body>

<form action="/userServlet" method="post">
<h1>Tariff</h1>
<br>
<TABLE BORDER>
    <TR>
        <TD>ID</TD>
        <TD>Name</TD>
        <TD>Price</TD>
        <TD>Description</TD>
    </TR>
    <c:forEach var="p" items="${allServiceForTariff}">
        <TR>
            <TD>${p.id}</TD>
            <TD>${p.nameService}</TD>
            <TD>${p.price}</TD>
            <TD>${p.comment}</TD>
        </TR>
    </c:forEach><br>

</TABLE>
    <select name="selectService" size="1">
        <c:forEach var="p" items="${allServiceForTariff}">

            <option name="${p.id}" >${p.nameService}</option>

        </c:forEach>
    </select>
    <input type="submit" name="useService" value="Add in my tariff"><input align="center" type="button" name="cancel" value="Back" onclick="history.back();">

    <br>
</form>
</body>
</html>
