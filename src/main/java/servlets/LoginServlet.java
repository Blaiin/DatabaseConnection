package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseConn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);
    private static final String RFC_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String action = request.getParameter("action");
        System.out.println(action);
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            LOGGER.info("User with email: {} is trying to login.", email);
            System.out.printf("Email: %s. \n", email);
            if (isValidUser(email, password)){
                System.out.println("Login successful, Forwarding request");
                LOGGER.info("Forwarding login request to Index page. User with email: {} login successful.", email);
                doGet(request, response);
            } else {
                request.setAttribute("errorMessage", "Invalid email or password");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                dispatcher.forward(request, response);
            }

    }
    private boolean isValidUser(String email, String password) {

        if (!isValidEmail(email)) {
            return false;
        }

        String sql = "SELECT users.id, users.name, users.surname, " +
                     "users.phone, users.email, users.age, passwords.password " +
                     "FROM users " +
                     "JOIN passwords ON users.id = passwords.user_id " +
                     "WHERE users.email = ? AND passwords.password = ?";

        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Returns true if a matching email/password is found
                LOGGER.info("Valid user found: {}", email);
                return resultSet.next();
            }

        } catch (SQLException e) {
            LOGGER.error("Error connecting to database.", e);
            e.printStackTrace();
        } catch (Exception e) {
            LOGGER.error("Error: ", e);
            e.printStackTrace();
        }

        return false;
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(RFC_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
