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
<html>
<head>
    <title>Good</title>
</head>
<body>

<p><c:out value="${purposes}"/><br></p>
<%--<form method=post action=addGood>--%>
    <%--<p>Add good</p>--%>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<td>price</td>--%>
            <%--<td><input type=text name=price></td>--%>
            <%--<br>--%>
            <%--<td>subsection</td>--%>
            <%--<td><select name="subsection">--%>
                <%--<c:forEach items="${subsections}" var="subsection">--%>
                    <%--<option value="${subsection.title}">${subsection.title}</option>--%>
                <%--</c:forEach>--%>
            <%--</select></td>--%>
            <%--<br>--%>
            <%--<td>unit</td>--%>
            <%--<td><input type=text name=unit></td>--%>
            <%--<br>--%>
            <%--<td>quantity</td>--%>
            <%--<td><input type=text name=quantity></td>--%>
            <%--<br>--%>
            <%--<td>discount</td>--%>
            <%--<td><input type=text name=discount></td>--%>
            <%--<br>--%>
            <%--<td>purpose</td>--%>
            <%--<td>--%>
                <%--<select name="purpose">--%>
                    <%--<c:forEach items="${purposes}" var="purpose">--%>
                        <%--<option value="${purpose.purpose}">${purpose.purpose}</option>--%>
                    <%--</c:forEach>--%>
                <%--</select>--%>

            <%--</td>--%>
            <%--<td>name</td>--%>
            <%--<td><input type=text name=name></td>--%>
            <%--<td>amount</td>--%>
            <%--<td><input type=text name=amount></td>--%>
            <%--<br>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td><input type=reset value=reset></td>--%>
            <%--<td><input type=submit value="add good"></td>--%>
        <%--</tr>--%>
    <%--</table>--%>
<%--</form>--%>


<form:form action="addGood" method="post">
    <p>Add good</p>
    <p>price</p><form:input type="text" path="price"/>
    <p>subsection</p><form:select name="subsection" path="subsection.title">
    <c:forEach items="${subsections}" var="subsection">
        <option value="${subsection.title}">${subsection.title}</option>
    </c:forEach>
</form:select>
    <p>unit</p><form:input type="text" path="unit"/>
    <p>quantity</p><form:input type="text" path="quantity"/>
    <p>discount</p><form:input type="text" path="discount"/>
    <p>purpose</p> <form:select name="purpose" path="purpose.purpose">
    <c:forEach items="${purposes}" var="purpose">
        <option value="${purpose.purpose}">${purpose.purpose}</option>
    </c:forEach>
</form:select>
    <p>name</p><form:input type="text" path="name"/>
    <p>amount</p><form:input type="text" path="amount"/>
    <input type=reset value=reset>
    <input type=submit value="add good">
</form:form>
<form method=get action=showGoods>
    <p><c:out value="${goods}"/><br></p>
    <input type=submit value="show goods">
</form>
<form method=get action=findGoodByName>
    <input type="text" name="goodName">
    <input type=submit value="search good">
    <p><c:out value="${good}"/><br></p>
</form>
<form:form action="findBySubsection" method="get">
<p>subsection</p><form:select name="subsection" path="subsection">
    <c:forEach items="${subsections}" var="subsection">
        <option value="${subsection.title}">${subsection.title}</option>
    </c:forEach>
</form:select>
    <input type=reset value=reset>
    <p><c:out value="${goods}"/><br></p>
    <input type=submit value="findBySubsection">
</form:form>
<form method=get action=findGoodByPrice>
    <input type="text" name="minPrice">
    <input type="text" name="maxPrice">
    <input type=submit value="search good">
    <p><c:out value="${goods1}"/><br></p>
</form>
</body>
</html>
