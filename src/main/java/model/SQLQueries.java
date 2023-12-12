package model;

public class SQLQueries {

    //Select queries
    public static final String SELECT_FROM_USERS = "select * from users";
    public static final String SELECT_FROM_NAME_QUERY = "select * from users where name = ?";
    public static final String SELECT_FROM_PHONE_AND_EMAIL_QUERY = "select * from users where phone=? and email=?";
    public static final String INSERT_QUERY = "insert into users (name, surname, phone, email, age) " +
                                              "values (?, ?, ?, ?, ?)";
    public static final String CHECK_PHONE_QUERY = "select count(*) from users where phone = ?";
    public static final String CHECK_EMAIL_QUERY = "select count(*) from users where email = ?";
    public static final String INSERT_PASSWORD_FOR_USER = "insert into passwords (user_id, user_email, password) " +
                                                            "values (?, ?, ?)";
}
