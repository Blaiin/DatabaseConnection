<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOC TYPE html>
<html>
<head>
    <title>User List</title>
    <style>
        body {
              font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
              background-color: black !important;
              color: white !important;
              margin: 0;
              padding: 0;
              text-align: center; /* Center align text content */
              display: flex;
              flex-direction: column;
              align-items: left;
              justify-content: left;
              height: 100vh;
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
        .highlight-text {
            color: #ff9900;
            font-size: 15px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .user-text {
            font-size: 16px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        h2 {
            font-size: 22px; /* Increase font size for emphasis */
            color: #ff9900; /* Use a cool color, you can choose any color you like */
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* Add a subtle text shadow */
            letter-spacing: 1px; /* Increase letter spacing for a modern look */
            margin-bottom: 20px; /* Add some space below the heading */
            font-weight: bold; /* Make the text bold */
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; /* Choose a modern font */
        }
    </style>
</head>
<body>
	<a id="backButton" href="/DatabaseConnection/index">Back to Index</a>
    <h2>User List</h2>
    
    <c:if test="${empty userList}">
        <p>No users found.</p>
    </c:if>
    
    <c:forEach var="user" items="${userList}">
        <div>
            <p><span class="highlight-text">Name:</span> <span class="user-text">${user.name}</span></p>
            <p><span class="highlight-text">Surname:</span> <span class="user-text">${user.surname}</span></p>
            <p><span class="highlight-text">Phone:</span> <span class="user-text">${user.phone}</span></p>
            <p><span class="highlight-text">Email:</span> <span class="user-text">${user.email}</span></p>
            <p><span class="highlight-text">Age:</span> <span class="user-text">${user.age}</span></p>
            <hr/>
        </div>
    </c:forEach>
</body>
</html>
