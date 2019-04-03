<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 02.04.2019
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
<%--    <meta charset="utf-8">--%>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <meta name="description" content="">--%>
<%--    <meta name="author" content="">--%>

    <title>Spring Security</title>
</head>

<body>
<c:url value="/j_spring_security_check" var="loginUrl" />
    <form action="${loginUrl}" method="post">
        <h2>Please sign in</h2>
        <input type="text" name="j_username" placeholder="login">
        <input type="text" name="j_password" placeholder="password">
        <button type="submit">Войти</button>
    </form>
</body>
</html>
