package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseConn;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(RegisterServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            Integer age = Integer.valueOf(request.getParameter("age"));
            String password = request.getParameter("password");
        if (checkIfPhoneAlreadyInUse(phone)) {
            request.setAttribute("errorMessagePhone", "Phone number already in use.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (checkIfEmailAlreadyInUse(email)) {
            request.setAttribute("errorMessageEmail", "Email already in use.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
                User userToRegister = new User(name, surname, phone, email, age);
                try (Connection connection = DatabaseConn.getConnection()) {
                    String sqlString = "INSERT INTO users (name, surname, phone, email, age) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
                        preparedStatement.setString(1, userToRegister.getName());
                        preparedStatement.setString(2, userToRegister.getSurname());
                        preparedStatement.setString(3, userToRegister.getPhone());
                        preparedStatement.setString(4, userToRegister.getEmail());
                        preparedStatement.setInt(5, userToRegister.getAge());

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            logger.info("Registered user {} successfully.", userToRegister);
                        } else {
                            logger.warn("Failed to register user.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    logger.error("Failed to register user to database", e);
                }
                response.sendRedirect("login.html");
            }


    }

    private static boolean checkIfPhoneAlreadyInUse(String phone) {
        try (Connection connection = DatabaseConn.getConnection()) {
            String sql = "select count(*) from users where phone = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, phone);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error connecting to database", e);
            e.printStackTrace();
        }
        return true;
    }

    private static boolean checkIfEmailAlreadyInUse(String email) {
        try (Connection connection = DatabaseConn.getConnection()) {
            String sql = "select count(*) from users where email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error connecting to database", e);
            e.printStackTrace();
        }
        return true;
    }

}
