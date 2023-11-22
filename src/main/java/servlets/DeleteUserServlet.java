package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DatabaseConn;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserServlet extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(DeleteUserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the JSP page that displays the add user form
        request.getRequestDispatcher("deleteUser.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchName = request.getParameter("searchName");
        User foundUser = User.findUserByName(searchName);
        if (foundUser != null) {
            request.setAttribute("foundUser", foundUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteUser.jsp");
            dispatcher.forward(request, response);
            response.getWriter().println("User " + foundUser.toString() + " deleted successfully.");
            logger.info("User {} deleted successfully.", foundUser.toString());
        } else {
            response.getWriter().println("User not found");
            logger.warn("User to delete not found.");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Assuming you have a database connection
        Connection connection = null;
        try {
            connection = DatabaseConn.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Get the parameter from the request (e.g., an ID to delete)
        String idToDelete = request.getParameter("id");

        // Prepare the SQL statement
        String deleteSQL = "DELETE FROM users WHERE id = ?";

        try {
            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);

            // Set the parameter in the prepared statement
            preparedStatement.setString(1, idToDelete);

            // Execute the delete operation
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Deletion successful
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().printf("Record with id: %d deleted successfully.", Integer.parseInt(idToDelete));
                logger.info("Record with id: {} deleted successfully.", idToDelete);
            } else {
                // No rows were deleted, possibly because the record doesn't exist
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().printf("Record with id: %d not found.", Integer.parseInt(idToDelete));
                logger.warn("Record with id: {} not found.", idToDelete);
            }

            // Close the resources
            preparedStatement.close();
        } catch (SQLException e) {
            // Handle exceptions appropriately (log or send an error response)
            logger.error("Error deleting record with id: " + idToDelete, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal server error: " + e.getMessage());
        } finally {
            // Close the database connection
            DatabaseConn.closeConnection(connection);
        }
    }

}
