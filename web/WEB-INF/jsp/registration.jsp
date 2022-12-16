<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
</head>
<body>
    <form action="/registration" method="post">
      <label for="name">Name:
        <input type="text" name="name" id="name">
      </label><br>
      <label for="birthday">Birthday:
        <input type="date" name="birthday" id="birthday">
      </label><br>Email:
      <label for="email">
        <input type="text" name="email" id="email">
      </label><br>Password:
      <label for="password">
        <input type="password" name="password" id="password">
      </label><br>
        <select name="role" id="role">
            <c:forEach var="role" items="${requestScope.roles}">
                <option value="${role}">${role}</option>
            </c:forEach>
        </select><br>
        <c:forEach var="gender" items="${requestScope.genders}">
            <input type="radio" name="gender" value="${gender}"> ${gender}
            <br>
        </c:forEach>
    <button type="submit">Send</button>
    </form>
</body>
</html>





























