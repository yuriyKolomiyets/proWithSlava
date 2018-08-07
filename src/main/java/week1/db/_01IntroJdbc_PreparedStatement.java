package week1.db;

import java.sql.*;

public class _01IntroJdbc_PreparedStatement {
    public static final String URL = "jdbc:mysql://localhost:3306/ACP11";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static final String ALL_USERS_QUERY = "SELECT id,name,amount,birth,phone FROM users";

    public static void main(String[] args) throws ClassNotFoundException {
        // load driver
        Class.forName("com.mysql.jdbc.Driver");
        User user = new User(-1, "new user", 10000, "+3343433242");


        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name,birth,phone,amount) VALUES (?,?,?,?)");){

            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2,user.getName());
            preparedStatement.setDouble(3,user.getSalary());
            preparedStatement.setString(4, user.getPhone());

            preparedStatement.execute();





        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

}
