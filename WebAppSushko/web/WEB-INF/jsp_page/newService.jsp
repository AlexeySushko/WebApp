<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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


    <h1 align="center">${newS}</h1>

    <p align="center">
        <input type="text" name="nameService" placeholder="${name}" required>
    </p>
    <p align="center">
        <input type="text" name="price" placeholder="${priceT}" required>
    </p>
    <p align="center">
        <input type="text" name="comment" placeholder="${descript}" required>
    </p>
    <p align="center">
    <select name="nameTariffForInsertService" size="1">
        <c:forEach var="p" items="${arrTariff}">
            <option name="${p.id}">${p.nameTariff}</option>
        </c:forEach>
    </select>
    </p>

    <p align="center">
        <input type="submit" name="addService" value="${save}">
        <input type="button" name="cancel" value="${back}" onclick="history.back();">
    </p><br>
</form>

</body>
</html>
