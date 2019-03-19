<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.03.2019
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log In</title>
</head>
<body>
<p>Неверно введены имя пользователя или пароль, попробуйте еще раз!</p>
<form method=get action=logIn>
    <input type="text" name="login">
    <input type="text" name="password">
    <input type=submit value="log in">
</form>

</body>
</html>
