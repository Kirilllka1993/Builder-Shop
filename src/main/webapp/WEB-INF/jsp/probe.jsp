<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 04.05.2019
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSON</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
</head>
<body>

<div ng-app="myApp" ng-controller="myCtrl">
<%--    <p>{{myGoods}}</p>--%>
    <ul>
        <li ng-repeat="x in myGoods">Название:{{x.name}}, Цена:{{x.price|currency}}</li>
    </ul>
    <p>Status: {{status}}</p>
    <p>Headers: {{headerss}}</p>


</div>

<script>
    var app=angular.module('myApp', []);
    app.controller('myCtrl', function($scope, $http) {
        $http.get("http://localhost:8080/Builder_Shop_war_exploded/shop/good/allGoods")
            .then(function(response) {
                $scope.myGoods = response.data;
                $scope.status=response.status;
                $scope.headerss=response.headers;
            });
    });
</script>
</body>
</html>
