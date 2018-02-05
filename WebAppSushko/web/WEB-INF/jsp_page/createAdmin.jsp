<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 15.01.2018
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>New Admin</title>
</head>
<body>
<form action="/newUser" method="post">
    <br>
    <br>
    <br>


        <h1 align="center">${newAdmin}</h1>

    <p align="center">
        <input type="text" name="login" placeholder="${login}" required>
    </p>
    <p align="center">
        <input type="text" name="pass" placeholder="${password}" required>
    </p>
    <p align="center">
        <input type="text" name="fio" placeholder="${fio}" required>
    </p>
    <p align="center">
        <input type="text" name="tell" placeholder="${tell}" required>
    </p>
    <p align="center">
        <input type="text" name="adress" placeholder="${adress}" required>
    </p>
    <p align="center">
        <input type="submit" name="addAdmin" value="${addAdmin}">
        <input type="button" name="cancel" value="${back}" onclick="history.back();">

    </p><br>
</form>

</body>
</html>
