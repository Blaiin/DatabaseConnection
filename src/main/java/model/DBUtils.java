package model;

import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtils {
    private static final Logger logger = LogManager.getLogger(DBUtils.class);

    public static void registerPasswordForUser(@NotNull String password, @NotNull String phone, @NotNull String email) {
        User user = findUserByPhoneAndEmail(phone, email);
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
                System.out.printf("SQLException: %s", e);
            }
        }
    }

    public static void registerUser(@NotNull User userToRegister) {
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
            logger.error("Failed to register user to database", e);
            System.out.printf("SQLException: %s", e);
        }
    }

    public static User findUserByPhoneAndEmail(@NotNull String phone, @NotNull String email) {
        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQLQueries.SELECT_FROM_PHONE_AND_EMAIL_QUERY)) {
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println(resultSet.getInt("id"));
                    logger.info("User {} {} selected.", resultSet.getString("name"),
                            resultSet.getString("surname"));
                    return User.mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            logger.error("Cannot connect to database.", e);
        }
        return null;
    }

    public static User findUserByName(@NotNull String name) {
        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(SQLQueries.SELECT_FROM_NAME_QUERY)) {

            preparedStatement.setString(1, name);
            System.out.println(preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println(resultSet.getString("name"));
                    logger.info("User {} {} selected.", resultSet.getString("name"), resultSet.getString("surname"));
                    return User.mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            logger.error("Cannot connect to database.", e);
        }
        return null;
    }

    public static boolean checkIfPhoneAlreadyInUse(@NotNull String phone) {
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
            System.out.printf("SQLException: %s", e);
        }
        return true;
    }
    public static boolean checkIfEmailAlreadyInUse(@NotNull String email) {
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
            System.out.printf("SQLException: %s", e);
        }
        return true;
    }

    public static String hashPassword(@NotNull String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}
