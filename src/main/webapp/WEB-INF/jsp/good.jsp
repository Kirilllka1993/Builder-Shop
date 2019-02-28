<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.02.2019
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Good</title>
</head>
<body>
<p><c:out value="${goods}"/></p>
<ul>
    <c:forEach var="good" items="${goods}">
        <li><c:out value="${good}" /></li>
    </c:forEach>
    <form method=get action=backServlet>
        <input type=submit value=Back>
    </form>
</ul>
<%@include file="adminJsp.jsp"%>
<%--<jsp:forward page="client.jsp"></jsp:forward>--%>
</body>
</html>
