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

public class ModifyUserServlet extends HttpServlet{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ModifyUserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle searching for a user and displaying details for modification
        String searchName = request.getParameter("searchName");
        User foundUser = DBUtils.findUserByName(searchName);

        // Set foundUser to null when no user is found
        request.setAttribute("foundUser", foundUser);

// Forward to the Modify User JSP with or without foundUser
        RequestDispatcher dispatcher = request.getRequestDispatcher("/modifyUser.jsp");
        dispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("modifyUser".equals(action)) {
            // Handle updating the user based on the submitted form data
            String modifiedName = request.getParameter("modifiedName");
            String modifiedSurname = request.getParameter("modifiedSurname");
            String modifiedPhone = request.getParameter("modifiedPhone");
            String modifiedEmail = request.getParameter("modifiedEmail");
            String modifiedAge = request.getParameter("modifiedAge");
            User foundUser = (User) request.getAttribute("foundUser");
            Integer foundUserId = foundUser.getId();

            foundUser.setName(modifiedName);
            foundUser.setSurname(modifiedSurname);
            foundUser.setName(modifiedPhone);
            foundUser.setEmail(modifiedEmail);
            foundUser.setAge(Integer.valueOf(modifiedAge));
            User.modifyUser(foundUserId, foundUser);
            response.getWriter().println("User updated successfully");
            logger.info("User {} updated successfully.", foundUser);

        } else {
            // Default behavior for handling a POST request
            response.getWriter().println("Default doPost method");
        }
    }



}
