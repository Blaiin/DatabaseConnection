package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    private static final Logger logger = LogManager.getLogger(DBUtils.class);

    public static void registerPasswordForUser(String password, String phone, String email) {
        User user = User.findUserByPhoneAndEmail(phone, email);
        if(user != null) {
            try (Connection connection = DatabaseConn.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_PASSWORD_FOR_USER)) {
                    preparedStatement.setInt(1, user.getId());
                    preparedStatement.setString(2, user.getEmail());
                    preparedStatement.setString(3, password);
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.printf("Password for user: %s registered successfully.", user.getEmail());
                        logger.info("Password for user: {} registered successfully.", user.getEmail());
                    } else {
                        System.out.printf("Password for user: %s failed to register.", user.getEmail());
                        logger.warn("Failed to register user password.");
                    }
                }
            } catch (SQLException e) {
                logger.error("Failed to register password for {} to database. {}", user.getEmail(), e);
                e.printStackTrace();
            }
        }
    }

    public static void registerUser(User userToRegister) {
        try (Connection connection = DatabaseConn.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_QUERY)) {
                preparedStatement.setString(1, userToRegister.getName());
                preparedStatement.setString(2, userToRegister.getSurname());
                preparedStatement.setString(3, userToRegister.getPhone());
                preparedStatement.setString(4, userToRegister.getEmail());
                preparedStatement.setInt(5, userToRegister.getAge());

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("User {} registered successfully.", userToRegister.getEmail());
                } else {
                    logger.warn("Failed to register user with email: {}.", userToRegister.getEmail());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Failed to register user to database", e);
        }
    }

    public static boolean checkIfPhoneAlreadyInUse(String phone) {
        try (Connection connection = DatabaseConn.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.CHECK_PHONE_QUERY)) {
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
    public static boolean checkIfEmailAlreadyInUse(String email) {
        try (Connection connection = DatabaseConn.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(SQLQueries.CHECK_EMAIL_QUERY)) {
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
