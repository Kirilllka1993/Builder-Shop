<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.03.2019
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Purpose</title>
</head>
<body>
<form method=post action=addPurpose>
    <p>Add purpose</p>
    <input type=text name=purpose><p>Name</p>
    <td><input type=submit value="add purpose"></td>
    <br>
</form>
<form method=get action=showPurposes>
    <p><c:out value="${purposes}"/><br></p>
    <input type=submit value="show purposes">
</form>
<form method=get action=findPurposeByName>
    <select name="purposeName">
        <c:forEach items="${purposes}" var="purposeName">
            <option value="${purposeName.purpose}">${purposeName.purpose}</option>
        </c:forEach>
    </select>
    <%--<input type="text" name="purposeName">--%>
    <input type=submit value="search purpose">
    <p><c:out value="${purpose}"/><br></p>
</form>
<form method=get action=doPurpose1>
    <input type=submit value="do Purpose ">
</form> <br>
</body>
</html>
