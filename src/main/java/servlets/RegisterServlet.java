package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DBUtils;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(RegisterServlet.class);
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        User userToRegister = User.mapParamsToUser(request);
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        if (password.equals(confirmPassword)) {
            if (DBUtils.checkIfPhoneAlreadyInUse(userToRegister.getPhone())) {
                request.setAttribute("errorMessagePhone", "Phone number already in use.");
                logger.error("Phone already in use: {}.", userToRegister.getPhone());
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else if (DBUtils.checkIfEmailAlreadyInUse(userToRegister.getEmail())) {
                request.setAttribute("errorMessageEmail", "Email already in use.");
                logger.error("Email already in use: {}.", userToRegister.getEmail());
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else {
                DBUtils.registerUser(userToRegister);
                DBUtils.registerPasswordForUser(password, userToRegister.getPhone(), userToRegister.getEmail());
                response.sendRedirect("login.html");
            }
        } else {
            request.setAttribute("errorMessagePassword", "Password not confirmed correctly.");
            logger.error("Password incorrectly confirmed.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

}
