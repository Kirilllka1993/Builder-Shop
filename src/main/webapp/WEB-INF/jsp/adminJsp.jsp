<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.02.2019
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>adminController</title>
</head>
<body>
<h2>Admin Page</h2> <br>
<form method=post action=adminController>
    <p>Add client</p>
    <table>
        <tr>
            <td>name</td>
            <td><input type=text name=name></td>
            <br>
            <td>surname</td>
            <td><input type=text name=surname></td>
            <br>
            <td>login</td>
            <td><input type=text name=login></td>
            <br>
            <td>password</td>
            <td><input type=text name=password></td>
            <br>
            <td>adress</td>
            <td><input type=text name=adress></td>
            <br>
            <td>phoneNumber</td>
            <td><input type=text name=phoneNumber></td>
            <br>
        </tr>
        <tr>
            <td><input type=reset value=reset></td>
            <td><input type=submit value=submit></td>
        </tr>
    </table>
</form>
<p><c:out value="${clients}"/></p>
<form method=get action=backServlet>
    <input type=submit value=Back>
</form>

</body>
</html>
