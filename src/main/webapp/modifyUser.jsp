<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOC TYPE html>
<html>
<head>
    <title>Modify User Page</title>
</head>
<body>
    <h1>Modify User</h1>
    <!-- Display user details or error message -->
    <c:choose>
        <c:when test="${not empty foundUser}">
            <h2>User Details:</h2>
            <p>Name: ${foundUser.name}</p>
            <p>Surname: ${foundUser.surname}</p>
            <p>Phone: ${foundUser.phone}</p>
            <p>Email: ${foundUser.email}</p>
            <p>Age: ${foundUser.age}</p>
            <!-- Add more fields as needed -->
            <form action="/DatabaseConnection/modifyUser" method="post">
                <input type="hidden" name="action" value="modifyUser">
                <!-- Include input fields for modification -->
                <input type="text" name="modifiedName" value="${foundUser.name}">
                <input type="text" name="modifiedSurname" value="${foundUser.surname}">
                <input type="text" name="modifiedPhone" value="${foundUser.phone}">
                <input type="text" name="modifiedEmail" value="${foundUser.email}">
                <input type="text" name="modifiedAge" value="${foundUser.age}">
                <!-- Add more input fields as needed -->
                <input type="submit" value="Modify User">
            </form>
            <!-- Link to go back to the welcome page -->
                <a href="/DatabaseConnection" style="display: inline-block; padding: 10px 20px;
                							  background-color: #4CAF50; color: white; text-decoration: none;
                							  border-radius: 5px;">Back to Welcome Page</a>
        </c:when>
        <c:otherwise>
            <p>No user found with the specified name.</p>
        </c:otherwise>
    </c:choose>
</body>
</html>
