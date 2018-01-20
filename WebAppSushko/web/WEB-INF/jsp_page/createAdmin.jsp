<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 15.01.2018
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>New User</title>
</head>
<body>
<form action="/newUser" method="post">
    <br>
    <br>
    <br>


        <h1 align="center">New Admin</h1>

    <p align="center">
        <input type="text" name="login" placeholder="Login" required>
    </p>
    <p align="center">
        <input type="text" name="pass" placeholder="Password" required>
    </p>
    <p align="center">
        <input type="text" name="fio" placeholder="First Name, Last Name" required>
    </p>
    <p align="center">
        <input type="text" name="tell" placeholder="Telephone number" required>
    </p>
    <p align="center">
        <input type="text" name="adress" placeholder="Adress" required>
    </p>
    <p align="center">
        <input type="submit" name="addAdmin" value="Add Admin">
        <input type="button" name="cancel" value="Cancel" onclick="history.back();">

    </p><br>
</form>

</body>
</html>
