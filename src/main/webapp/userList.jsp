<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOC TYPE html>
<html>
<head>
    <title>User List</title>
    <style>
        body {
            margin: 20px;
        }

        #backButton {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            position: absolute;
            top: 10px;
            left: 10px;
        }
    </style>
</head>
<body>
	<a id="backButton" href="/DatabaseConnection">Back to Welcome Page</a>
    <h2>User List</h2>
    
    <c:if test="${empty userList}">
        <p>No users found.</p>
    </c:if>
    
    <c:forEach var="user" items="${userList}">
        <div>
            <p>ID: ${user.id}</p>
            <p>Name: ${user.name}</p>
            <p>Surname: ${user.surname}</p>
            <p>Phone: ${user.phone}</p>
            <p>Email: ${user.email}</p>
            <p>Age: ${user.age}</p>
            <hr/>
        </div>
    </c:forEach>
</body>
</html>
