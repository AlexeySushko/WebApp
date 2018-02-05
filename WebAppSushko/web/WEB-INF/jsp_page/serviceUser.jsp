<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${tariff}</title>
</head>
<body>

<form action="/userServlet" method="post">
<h1>Tariff</h1>
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
    </c:forEach><br>

</TABLE>
    <select name="selectService" size="1">
        <c:forEach var="p" items="${allServiceForTariff}">

            <option name="${p.id}" >${p.nameService}</option>

        </c:forEach>
    </select>
    <input type="submit" name="useService" value="${addInMyT}"><input align="center" type="button" name="cancel" value="${back}" onclick="history.back();">

    <br>
</form>
</body>
</html>
