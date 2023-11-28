package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DBUtils;
import model.DatabaseConn;
import model.SQLQueries;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
        if (DBUtils.checkIfPhoneAlreadyInUse(phone)) {
            request.setAttribute("errorMessagePhone", "Phone number already in use.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else if (DBUtils.checkIfEmailAlreadyInUse(email)) {
            request.setAttribute("errorMessageEmail", "Email already in use.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } else {
                User userToRegister = User.mapParamsToUser(request);
                DBUtils.registerUser(userToRegister);
                DBUtils.registerPasswordForUser(password, phone, email);
                response.sendRedirect("login.html");
        }
    }

}
