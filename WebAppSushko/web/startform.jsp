<!DOCTYPE html>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>

<head>

  <meta charset="UTF-8">

  <title>SummaryTask4</title>

  <link rel='stylesheet' href='http://codepen.io/assets/libs/fullpage/jquery-ui.css'>

    <link rel="stylesheet" href="css/style.css" media="screen" type="text/css" />

</head>

<body>

  <div class="login-card">
  
    <br><br><br><br><br><br><h1 align="center">${LogIn}</h1>
  <form action="/start" method="post">
  <p align="center">
    <input type="text" name="user" placeholder="${username}" required pattern="[а-яА-Я\w]{4,8}">
	</p>
	<p align="center">
    <input type="password" name="pass" placeholder="${password}" required pattern="[а-яА-Я\w]{4,8}" >
	</p>
      <p align="center">"${infoString}"</p>
	<p align="center">
    <input type="submit" name="enter" class="login login-submit" value="${enter}">
	</p><br>

  </form>
    <form action="/start" method="post">
      <p align="center">
      <select name="changeLanguage" size="1">

        <option name="English">English</option>
        <option name="Русский">Русский</option>

      </select><input type="submit" name="changeLang" value="${changeLanguage}">
      </p>
    </form>

  <div class="login-help" align="center">
  
    <a href="http://localhost:8080/forgot.html">${forgot}</a>
  </div>
</div>

<!-- <div id="error"><img src="https://dl.dropboxusercontent.com/u/23299152/Delete-icon.png" /> Your caps-lock is on.</div> -->

  <script src='http://codepen.io/assets/libs/fullpage/jquery_and_jqueryui.js'></script>

</body>

</html>