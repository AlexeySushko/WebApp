<%--
  Created by IntelliJ IDEA.
  User: Olga
  Date: 16.01.2018
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Error</title>
</head>
<style>
    .okno {
        width: 300px;
        height: 50px;
        text-align: center;
        padding: 15px;
        border: 3px solid #0000cc;
        border-radius: 10px;
        color: #0000cc;
    }
</style>
</head>
<center>
<body>

<div class="okno" align="center">
    <c:out value="${messageError}"></c:out>
</div>
<input align="center" type="button" name="cancel" value="Back" onclick="history.back();">

</body>
</center>
</html>
