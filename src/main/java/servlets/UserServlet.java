package servlets;

import java.io.*;
import java.sql.*;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.DatabaseConn;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(UserServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        List<User> userList = null;
        try {
            connection = DatabaseConn.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            userList = User.mapResultSetToUserList(resultSet);
            System.out.println(userList);
            logger.info("Displaying users list.");
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Failed to connect to database.", e);
        } finally {
            DatabaseConn.closeConnection(connection);
        }

        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/userList.jsp");
        dispatcher.forward(request, response);
    }
}
