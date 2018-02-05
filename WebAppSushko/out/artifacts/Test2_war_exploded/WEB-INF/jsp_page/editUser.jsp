
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>

<form action="/adminServlet" method="post">
    <br>
    <br>
    <br>


    <h1 align="center">Edit User</h1>

    <p align="center">
       <strong>${login2}: </strong><br><input type="text" name="login"  value="<c:out value="${login}"></c:out>" required>
    </p>
    <p align="center">
    <strong>${password2}: </strong><br><input type="text" name="pass"  value="<c:out value="${pass}"></c:out>"  required>
    </p>
    <p align="center">
        <strong>${fio2}: </strong><br><input type="text" name="fio"  value="<c:out value="${fio}"></c:out> " required>
    </p>
    <p align="center">
    <strong>${balance2}</strong><br><input type="number" name="balance"  value="<c:out value="${balance}"></c:out>"  required>
    </p>
    <p align="center">
    <strong>${tell2}: </strong><br><input type="text" name="tell"  value="<c:out value="${tell}"></c:out>"  required>
    </p>
    <p align="center">
    <strong>${adress2}: </strong><br><input type="text" name="adress"  value="<c:out value="${adress}"></c:out>" required>
    </p>
    <p align="center">
        <strong>Status: </strong><br>
        <select name="block" size="1"  value="<c:out value="${nameTariff}"></c:out>">
            <option name="no" >Unblocked</option>
            <option name="yes" >Blocked</option>
        </select>
    </p>
    <p align="center"><br>
        <input type="submit" name="save" value="${save2}">
        <input type="button" name="cancel" value="${back2}" onclick="history.back();">
    </p><br>
</form>

</body>
</html>
