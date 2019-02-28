<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.02.2019
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Purchase</title>
</head>
<body>
<%--<p><c:out value="${purchases}"/></p>--%>
<ul>
    <c:forEach var="purchase" items="${purchases}">
    <li><c:out value="${purchase}" /></li>
    </c:forEach>
<form method=get action=backServlet>
    <input type=submit value=Back>
</form>
</body>
</html>
