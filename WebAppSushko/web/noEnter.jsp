<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 13.01.2018
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot form</title>
</head>
<style>
    h1 {
        font-family: 'Times New Roman', Times, serif; /* Гарнитура текста */
        font-size: 170%; /* Размер шрифта в процентах */
    }
    p {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: 11pt; /* Размер шрифта в пунктах */
    }
</style>
</head>

<body>
<h1>NO ENTER</h1>
<c:out value="${val}"></c:out>

<p><a href="http://localhost:8080/startform.html">Log-in page</a></p>
</body>
</html>