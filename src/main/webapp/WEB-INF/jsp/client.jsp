<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 27.02.2019
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<h2>Client Page</h2> <br>
<form:form action="signIn">
    <p>SignIn</p>
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
<form method=post action=addReview>
    <p>Add review</p>
    <table>
        <tr>
            <td>mark</td>
            <td><input type=text name=mark></td>
            <br>
            <td>comment</td>
            <td><input type=text name=comment></td>
            <td><select name="client">
                <c:forEach items="${clients}" var="client">
                    <option value="${client.login}">${client.login}</option>
                </c:forEach>
            </select></td>
            <td>good</td>
            <td>
                <select name="good">
                    <c:forEach items="${goods}" var="good">
                        <option value="${good.name}">${good.name}</option>
                    </c:forEach>
                </select>

            </td>
        </tr>
        <tr>
            <td><input type=reset value=reset></td>
            <td><input type=submit value="add Review"></td>
        </tr>
    </table>
</form>
<form method=get action=backServlet>
<input type=submit value=Back>
</form>
<p><c:out value="${clientList}"/></p>
<form method=get action=logIn>
    <input type="text" name="login">
    <input type="text" name="password">
    <input type=submit value="log in">
</form>
<p>changelogin</p>
<form method=post action=changeLogin>
    <p>id</p><input type="text" name="id">
    <p>login</p><input type="text" name="login">
    <input type=submit value="update client">
</form>
<p>changeAddreess</p>
<form method=post action=changeAddress>
    <p>id</p><input type="text" name="id">
    <p>address</p><input type="text" name="address">
    <input type=submit value="update client">
</form>
</body>
</html>
