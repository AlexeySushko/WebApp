<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>${tariff}</h1>
<br>
<TABLE BORDER>
    <TR>
        <TD>${id}</TD>
        <TD>${name}</TD>
        <TD>${price}</TD>
        <TD>${descript}</TD>
    </TR>
    <c:forEach var="p" items="${allServiceForTariff}">
        <TR>
            <TD>${p.id}</TD>
            <TD>${p.nameService}</TD>
            <TD>${p.price}</TD>
            <TD>${p.comment}</TD>
        </TR>
    </c:forEach>
</TABLE>
<br>
<input align="center" type="button" name="cancel" value="${back}" onclick="history.back();">

</body>
</html>
