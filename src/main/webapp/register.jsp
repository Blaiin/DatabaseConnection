<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
    <!-- Add any additional styles here if needed -->
    <style>
            /* Style to make input fields have the same offset */
            body {
                 font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                 background-color: black !important;
                 color: white !important;
                 margin: 0;
                 display: flex;
                 flex-direction: column;
                 align-items: center;
                 justify-content: center;
                 height: 100vh;
            }
            label {
                display: inline-block;
                width: 100px; /* Adjust the width as needed */
                text-align: right;
                margin-right: 10px; /* Adjust the margin as needed */
                color: white;
            }

            input {
                width: 200px; /* Adjust the width as needed */
                box-sizing: border-box;
                margin-bottom: 5px; /* Adjust the margin as needed */
            }
            button {
                background-color: #4caf50;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            button.register {
                background-color: #211ca6;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 5px;
            }
            button.register:hover {
                background-color: #120f5e !important;
            }
            .register-container {
                background-color: #ff9900 !important;
                color: black;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                text-align: left !important;
            }
        </style>
</head>
<body>
    <h2>User Registration</h2>
    <div class="register-container">
        <form action="/DatabaseConnection/register" method="post">
            <!-- Name -->
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required><br>

            <!-- Surname -->
            <label for="surname">Surname:</label>
            <input type="text" id="surname" name="surname" required><br>

            <!-- Phone -->
            <label for="phone">Phone:</label>
            <input type="text" id="phone" name="phone" required><br>

            <!-- Email -->
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>

            <!-- Age -->
            <label for="age">Age:</label>
            <input type="number" id="age" name="age" required><br>

            <!-- Password -->
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required><br>

            <br>
            <c:if test="${not empty errorMessagePhone}">
                <p style="color: red;">${errorMessagePhone}</p>
            </c:if>
            <c:if test="${not empty errorMessageEmail}">
                <p style="color: red;">${errorMessageEmail}</p>
            </c:if>
            <button class="register" type="submit">Register</button>
        </form>
    </div>
</body>
</html>
