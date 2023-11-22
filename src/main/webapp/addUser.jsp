<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add User</title>

    <style>
        /* Style to make input fields have the same offset */
        label {
            display: inline-block;
            width: 100px; /* Adjust the width as needed */
            text-align: right;
            margin-right: 10px; /* Adjust the margin as needed */
        }

        input {
            width: 200px; /* Adjust the width as needed */
            box-sizing: border-box;
            margin-bottom: 10px; /* Adjust the margin as needed */
        }
    </style>
</head>
<body>
    <h1>Add User</h1>

    <!-- Form to add a new user -->
    <form action="/DatabaseConnection/addUser" method="post">
        <!-- Input fields for user details -->
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
        <br>

        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" required>
        <br>

        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" required>
        <br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <br>

        <label for="age">Age:</label>
        <input type="number" id="age" name="age" required>
        <br>
		<input type="submit" value="Submit" 
			   style="display: inline-block; padding: 10px 20px; background-color: #3498db; 
			   color: white; text-decoration: none; border-radius: 5px; margin-top: 10px;">

    </form>
    <a href="/DatabaseConnection" style="display: inline-block; padding: 10px 20px; 
    							  background-color: #4CAF50; color: white; text-decoration: none; 
    							  border-radius: 5px;">Back to Welcome Page</a>
</body>
</html>

