<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <title>Edit tariff</title>
</head>
<body>
<form action="/adminServlet" method="post">
    <br>
    <br>
    <br>


    <h1 align="center">${changeS}</h1>

    <p align="center">
        <input type="text" name="nameService" value="<c:out value="${nameService}"></c:out>" placeholder="${name}" required>
    </p>
    <p align="center">
        <input type="text" name="price" value="<c:out value="${price}"></c:out>" placeholder="${priceT}" required>
    </p>
    <p align="center">
        <input type="text" name="comment" value="<c:out value="${comment}"></c:out>" size="150" align="center" placeholder="${descript}" required>
    </p>

    <p align="center">
        <input type="submit" name="editService" value="${save}">
        <input type="button" name="cancel" value="${back}" onclick="history.back();">

    </p><br>
</form>

</body>
</html>
