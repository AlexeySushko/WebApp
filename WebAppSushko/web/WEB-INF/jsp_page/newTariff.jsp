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
    <title>New tariff</title>
</head>
<body>
<form action="/adminServlet" method="post">
    <br>
    <br>
    <br>


    <h1 align="center">New tariff</h1>

    <p align="center">
        <input type="text" name="nameTariff" placeholder="Name tarif" required>
    </p>
    <%--<p align="center">--%>
        <%--<input type="text" name="price" placeholder="Price" required>--%>
    <%--</p>--%>
    <p align="center">
        <input type="text" name="comment" placeholder="Description" required>
    </p>

    <p align="center">
        <input type="submit" name="addTariff" value="Add Tariff">
        <input type="button" name="cancel" value="Cancel" onclick="history.back();">

    </p><br>
</form>

</body>
</html>
