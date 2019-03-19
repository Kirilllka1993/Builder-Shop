<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<%--<form method=post action=addClient>--%>
    <%--<p>Add client</p>--%>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<td>name</td>--%>
            <%--<td><input type=text name=name></td>--%>
            <%--<br>--%>
            <%--<td>surname</td>--%>
            <%--<td><input type=text name=surname></td>--%>
            <%--<br>--%>
            <%--<td>login</td>--%>
            <%--<td><input type=text name=login></td>--%>
            <%--<br>--%>
            <%--<td>password</td>--%>
            <%--<td><input type=text name=password></td>--%>
            <%--<br>--%>
            <%--<td>adress</td>--%>
            <%--<td><input type=text name=adress></td>--%>
            <%--<br>--%>
            <%--<td>phoneNumber</td>--%>
            <%--<td><input type=text name=phoneNumber></td>--%>
            <%--<br>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td><input type=reset value=reset></td>--%>
            <%--<td><input type=submit value=submit></td>--%>
        <%--</tr>--%>
    <%--</table>--%>
<%--</form>--%>

<form:form action="addClient">
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
            <td><input type=text name=address></td>
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
</form:form>
<form method=get action=back>
    <input type=submit value=Back>
</form>
<form method=post action=deleteClient>
    <input type="text" name="number">
    <input type=submit value=delete>
</form>
<form method=get action=showClient>
    <p><c:out value="${clients}"/><br></p>
    <input type=submit value="show clients">
</form>
<form method=get action=login>
    <input type="text" name="login">
    <input type=submit value="search client">
    <p><c:out value="${client}"/><br></p>
</form>
<form method=get action=findById>
    <input type="text" name="idClient">
    <input type=submit value="search client by id">
    <p><c:out value="${client1}"/><br></p>
</form>

</body>
</html>
