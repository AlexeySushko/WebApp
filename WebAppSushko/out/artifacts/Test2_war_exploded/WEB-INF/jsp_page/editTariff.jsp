<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 15.01.2018
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New service</title>
</head>
<body>
<form action="/adminServlet" method="post">
    <br>
    <br>
    <br>


    <h1 align="center">Edit service</h1>

    <p align="center">
        <input type="text" name="nameTariff" value="<c:out value="${nameTariff}"></c:out>" placeholder="Name service" required>
    </p>
    <%--<p align="center">--%>
        <%--<input type="text" name="price" value="<c:out value="${price}"></c:out>" placeholder="Price" required>--%>
    <%--</p>--%>
    <p align="center">
        <input type="text" name="comment" value="<c:out value="${comment}"></c:out>" size="150" align="center" placeholder="Description" required>
    </p>

    <p align="center">
        <input type="submit" name="editTariff" value="Edit service">
        <input type="button" name="cancel" value="Cancel" onclick="history.back();">

    </p><br>
</form>

</body>
</html>
