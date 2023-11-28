package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static final Logger logger = LogManager.getLogger(User.class);
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private Integer age;

    public User() {
    }

    public User(String name, String surname, String phone, String email, Integer age) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.age = age;
    }

    public User(Integer id, String name, String surname, String phone, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.age = age;
    }

    public static List<User> mapResultSetToUserList(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setPhone(resultSet.getString("phone"));
            user.setEmail(resultSet.getString("email"));
            user.setAge(resultSet.getInt("age"));
            userList.add(user);
        }
        return userList;
    }

    public static User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setPhone(resultSet.getString("phone"));
        user.setEmail(resultSet.getString("email"));
        user.setAge(resultSet.getInt("age"));
        return user;
    }

    public static User findUserByName(String name) {
        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("select * from users where name = ?")) {

            preparedStatement.setString(1, name);
            System.out.println(preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println(resultSet.getString("name"));
                    logger.info("User {} {} selected.", resultSet.getString("name"), resultSet.getString("surname"));
                    return mapResultSetToUser(resultSet);
                }
            }

        } catch (SQLException e) {
            logger.error("Cannot connect to database.", e);
        }
        return null;
    }

    public static boolean modifyUser(Integer foundUserId, User modifiedUser) {
        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update users set name=?, surname=?, phone=?, email=?, age=? where id=?")) {
            preparedStatement.setString(1, modifiedUser.getName());
            preparedStatement.setString(2, modifiedUser.getSurname());
            preparedStatement.setString(3, modifiedUser.getPhone());
            preparedStatement.setString(4, modifiedUser.getEmail());
            preparedStatement.setInt(5, modifiedUser.getAge());
            preparedStatement.setInt(6, foundUserId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error connecting to database.", e);
        }
        return false;
    }
    public static boolean modifyUser(User modifiedUser) {
        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update users set name=?, surname=?, phone=?, email=?, age=? where id=?")) {
            preparedStatement.setString(1, modifiedUser.getName());
            preparedStatement.setString(2, modifiedUser.getSurname());
            preparedStatement.setString(3, modifiedUser.getPhone());
            preparedStatement.setString(4, modifiedUser.getEmail());
            preparedStatement.setInt(5, modifiedUser.getAge());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error connecting to database.", e);
        }
        return false;
    }

    public static boolean deleteUserById(int userId) {
        // Perform the deletion in the database
        try (Connection connection = DatabaseConn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete form users where id = ?")) {

            preparedStatement.setInt(1, userId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.warn("Error deleting user with id: {}.", userId, e);
        }
        return false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "\nUser -> [id=" + id + ", name=" + name + ", surname=" + surname + ", phone=" + phone + ", email="
                + email + ", age=" + age + "]";
    }

}
