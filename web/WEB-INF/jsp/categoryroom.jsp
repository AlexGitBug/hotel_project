<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Категория комнат:</title>
</head>
<body>
<h1>Категория комнат:</h1>
<ul>
    <c:forEach var="categoryroom" items="${requestScope.categoryroom}">
        <li>${categoryroom.kind}</li>
    </c:forEach>
</ul>
</body>
</html>
