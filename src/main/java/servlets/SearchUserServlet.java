package servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DBUtils;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchUserServlet extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(SearchUserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle searching for a user and displaying details for modification
        String searchName = request.getParameter("searchName");
        User foundUser = DBUtils.findUserByName(searchName);

        // Set foundUser to null when no user is found
        request.setAttribute("foundUser", foundUser);

        // Forward to the Search User JSP with or without foundUser
        RequestDispatcher dispatcher = request.getRequestDispatcher("/searchUser.jsp");
        dispatcher.forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("modify".equals(action)) {
            // Retrieve parameters from the form
            String modifiedName = request.getParameter("name");
            String modifiedSurname = request.getParameter("surname");
            String modifiedPhone = request.getParameter("phone");
            String modifiedEmail = request.getParameter("email");
            Integer modifiedAge = Integer.valueOf(request.getParameter("age"));

            // Retrieve other parameters as needed
            User modifiedUser = new User(modifiedName, modifiedSurname, modifiedPhone, modifiedEmail, modifiedAge);

            // Perform the modification in the database
            boolean modificationSuccess = User.modifyUser(modifiedUser);

            if (modificationSuccess) {
                response.getWriter().println("User modified successfully.");
                logger.info("User modified: {}", modifiedUser.toString());
            } else {
                response.getWriter().println("Error modifying user.");
                logger.warn("Error modifying user.");
            }
        } else if ("delete".equals(action)) {
            // Retrieve parameters from the form
            int userIdToDelete = Integer.parseInt(request.getParameter("userIdToDelete"));

            // Perform the deletion in the database
            boolean deleted = User.deleteUserById(userIdToDelete);

            if (deleted) {
                response.getWriter().println("User with id: " + userIdToDelete + " deleted successfully.");
                logger.info("User with id: {} deleted successfully.", userIdToDelete);
            } else {
                response.getWriter().println("Error deleting user.");
                logger.warn("Error deleting user with id {}.", userIdToDelete);
            }
        } else {
            response.getWriter().println("Invalid action");
            logger.error("Invalid action.");
        }
    }



}
