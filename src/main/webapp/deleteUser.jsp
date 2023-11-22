<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOC TYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete User</title>
</head>
<body>
    <h1>Delete User</h1>
    <!-- Display user details for deletion -->
    <c:if test="${not empty foundUser}">
        <form action="/DatabaseConnection/deleteUser" method="post">
            <!-- Input fields for updated user details -->
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${foundUser.name}" required>
            <br>

            <label for="surname">Surname:</label>
            <input type="text" id="surname" name="surname" value="${foundUser.surname}" required>
            <br>

            <label for="phone">Phone:</label>
            <input type="text" id="phone" name="phone" value="${foundUser.phone}" required>
            <br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${foundUser.email}" required>
            <br>

            <label for="age">Age:</label>
            <input type="number" id="age" name="age" value="${foundUser.age}" required>
            <br>

            <!-- Hidden input field for user ID -->
            <input type="hidden" id="userId" name="userId" value="${foundUser.id}">

            <!-- Submit button to update user -->
            <input type="submit" value="Delete User" style="display: inline-block; padding: 10px 20px; background-color: #3498db;
			   color: white; text-decoration: none; border-radius: 5px; margin-top: 10px;">
        </form>
    </c:if>

    <!-- Link to go back to the welcome page -->
    <a href="/DatabaseConnection" style="display: inline-block; padding: 10px 20px;
    							  background-color: #4CAF50; color: white; text-decoration: none;
    							  border-radius: 5px;">Back to Welcome Page</a>
</body>
</html>

