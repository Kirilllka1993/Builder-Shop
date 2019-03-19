<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 01.03.2019
  Time: 14:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Subsections</title>
</head>
<body>
<form method=post action=addSubsection>
    <p>Add subsection</p>
    <input type=text name=title><p>Name</p>
    <td><input type=submit value="add subsection"></td>
    <br>
</form>
<form method=get action=showSubsections>
    <p><c:out value="${subsections}"/><br></p>
    <input type=submit value="show subsections">
</form>
<form method=get action=findSubsectionByName>
    <input type="text" name="subsectionName">
    <input type=submit value="search Subsection">
    <p><c:out value="${subsection}"/><br></p>
</form>
</body>
</html>
