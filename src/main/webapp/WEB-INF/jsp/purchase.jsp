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
<ul>
    <c:forEach var="purchase" items="${purchases}">
    <li><c:out value="${purchase}"/></li>
    </c:forEach>
    <form method=post action=createPurchase>
        <input type="text" name="clientLogin">
        <input type=submit value="create purchase">
    </form>
    <form method=post action=addIntoGoodInPurchase>
        <p>goodId</p><input type="text" name="goodId">
        <p>amount</p><input type="text" name="amount">
        <p>purchaseId</p><input type="text" name="purchaseId">
        <input type=submit value="create purchase">
    </form>
    <form method=get action=backServlet>
        <input type=submit value=Back>
    </form>
    <form method=get action=showPurchases>
        <p><c:out value="${purchases}"/><br></p>
        <input type=submit value="show purchases">
    </form>
        <form method=post action=makeAPurchase>
            <input type="text" name="purchaseId">
            <input type=submit value="make a purchase">
        </form>
        <form method=post action=changeAmount>
            <p>goodId</p><input type="text" name="goodId">
            <p>amount</p><input type="text" name="amount">
            <p>purchaseId</p><input type="text" name="purchaseId">
            <input type=submit value="changeAmount">
        </form>

        <form method=post action=delete>
            <p>goodId</p><input type="text" name="goodId">
            <p>purchaseId</p><input type="text" name="purchaseId">
            <input type=submit value="delete">
        </form>

        <form method=post action=deleteCancelled>
            <p>purchaseId</p><input type="text" name="purchaseId">
            <input type=submit value="deleteCancelled">
        </form>

        <form method=get action=findGoods>
            <p>purchaseId</p><input type="text" name="purchaseId">
            <p>purchaseId</p><input type="text" name="goodId">
            <input type=submit value="show goods">
            <p><c:out value="${goods}"/><br></p>
            <p><c:out value="${purchases}"/><br></p>
        </form>

        <form method=get action=findCartItem>
            <p>goodInPurchaseId</p><input type="text" name="goodInPurchaseId">
            <input type=submit value="findCartItem">
            <p><c:out value="${cartItem}"/><br></p>
        </form>
        <form method=get action=findGoodInPurchaseByParametres>
            <p>goodId</p><input type="text" name="goodId">
            <p>purchaseId</p><input type="text" name="purchaseId">
            <input type=submit value="findCartItem">
            <p><c:out value="${cartItem}"/><br></p>
        </form>

        <form method=get action=deletePurchaseWithCancelled>
            <p>purchaseId</p><input type="text" name="purchaseId">
            <input type=submit value="delete cancelled">
        </form>
</body>
</html>
