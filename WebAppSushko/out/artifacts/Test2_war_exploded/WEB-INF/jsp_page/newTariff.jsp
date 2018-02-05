<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" charset="UTF-8">
    <title>New tariff</title>
</head>
<body>
<form action="/adminServlet" method="post">
    <br>
    <br>
    <br>


    <h1 align="center">${neweS}</h1>

    <p align="center">
        <input type="text" name="nameTariff" placeholder="${name}" required>
    </p>
    <%--<p align="center">--%>
        <%--<input type="text" name="price" placeholder="Price" required>--%>
    <%--</p>--%>
    <p align="center">
        <input type="text" name="comment" placeholder="${descript}" required>
    </p>

    <p align="center">
        <input type="submit" name="addTariff" value="${save}">
        <input type="button" name="cancel" value="${back}" onclick="history.back();">

    </p><br>
</form>

</body>
</html>
