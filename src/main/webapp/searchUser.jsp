<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOC TYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modify User</title>
</head>
<body>
    <h1>Search User</h1>

    <form action="/DatabaseConnection/modifyUser" method="get">
        <label for="searchName">Search User by Name:</label>
        <input type="text" id="searchName" name="searchName" required>
        <input type="hidden" name="action" value="modify">
        <input type="submit" value="Modify User">
    </form>

    <form action="/DatabaseConnection/deleteUser" method="get">
        <label for="searchName">Search User by Name:</label>
        <input type="text" id="searchName" name="searchName" required>
        <input type="hidden" name="action" value="delete">
        <!-- Include the user's ID to identify which user to delete -->
            <input type="hidden" name="userIdToDelete" value="${foundUser.id}">
            <input type="submit" value="Delete User">
    </form>


    <!-- Link to go back to the welcome page -->
    <a href="/DatabaseConnection" style="display: inline-block; padding: 10px 20px;
    							  background-color: #4CAF50; color: white; text-decoration: none;
    							  border-radius: 5px;">Back to Welcome Page</a>
</body>
</html>

