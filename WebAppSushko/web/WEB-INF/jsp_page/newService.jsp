<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 15.01.2018
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>New tariff</title>
</head>
<body>
<form action="/adminServlet" method="post">
    <br>
    <br>
    <br>


    <h1 align="center">New tariff</h1>

    <p align="center">
        <input type="text" name="nameService" placeholder="Name tariff" required>
    </p>
    <p align="center">
        <input type="text" name="price" placeholder="Price" required>
    </p>
    <p align="center">
        <input type="text" name="comment" placeholder="Description" required>
    </p>
    <p align="center">
    <select name="nameTariffForInsertService" size="1">
        <c:forEach var="p" items="${arrTariff}">
            <option name="${p.id}">${p.nameTariff}</option>
        </c:forEach>
    </select>
    </p>

    <p align="center">
        <input type="submit" name="addService" value="Add tariff">
        <input type="button" name="cancel" value="Cancel" onclick="history.back();">
    </p><br>
</form>

</body>
</html>
