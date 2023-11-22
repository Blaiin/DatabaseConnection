package servlets;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseConn;
import model.User;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(AddUserServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the JSP page that displays the add user form
        request.getRequestDispatcher("addUser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        Integer age = Integer.parseInt(request.getParameter("age"));
        User user = new User(name, surname, phone, email, age);

        try (Connection connection = DatabaseConn.getConnection()) {
            String sqlString = "INSERT INTO users (name, surname, phone, email, age) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlString)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getSurname());
                preparedStatement.setString(3, user.getPhone());
                preparedStatement.setString(4, user.getEmail());
                preparedStatement.setInt(5, user.getAge());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    response.sendRedirect(request.getContextPath() + "/userList");
                    logger.info("Added user {} successfully.", user);
                } else {
                    response.getWriter().println("Failed to add the user.");
                    logger.warn("Failed to add user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Failed to connect to the database.");
            logger.error("Failed to connect to database", e);
        }
    }
}


