<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Категория комнат:</title>
</head>
<body>
<h1>Категория комнат:</h1>
<ul>
  <c:forEach var="room" items="${requestScope.roomlist}">
    <li>
     <a href="/room?id=${room.id}">${room.description}</a>
    </li>
  </c:forEach>
</ul>
</body>
</html>

