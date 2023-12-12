package servlets;

import java.io.*;
import java.sql.*;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.DatabaseConn;
import model.SQLQueries;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(UserServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> userList = null;
        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_FROM_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            userList = User.mapResultSetToUserList(resultSet);
            System.out.println("Displaying users list.");
            logger.info("Displaying users list.");

        } catch (SQLException e) {
            logger.error("Failed to connect to database.", e);
            System.out.printf("SQLException: %s", e);
        }
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/userList.jsp");
        dispatcher.forward(request, response);
    }

}
