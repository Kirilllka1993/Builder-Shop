<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<form method=post action=clientController>
    <p>Add client</p>


    <input type=text name=name><p>Name</p>
    <br>
    <input type=text name=surname><p>Surname</p>
    <br>
    <input type=text name=login>
    <br>
    <input type=text name=password>
    <br>
    <input type=text name=adress>
    <br>
    <input type=text name=phoneNumber>
    <br>
    <input type=submit value=submit>
</form>
<form method=get action=backServlet>
<input type=submit value=Back>
</form>
<p><c:out value="${clientList}"/></p>

</body>
</html>
