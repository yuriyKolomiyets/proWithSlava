package week1.db;

import week1.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HomeWorkWeek1 {

    public static final String URL = "jdbc:mysql://localhost:3306/TEST1";
    public static final String USER = "root";
    public static final String PASSWORD = "asusual1";

    public static void main(String[] args) throws ClassNotFoundException {

        User user = new User(101, "Test1", "Test2", 100, 101);

        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = null;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            preparedStatement = connection.prepareStatement
                    ("INSERT INTO users(id, first_name, last_name, age, address_id) VALUES (?,?,?,?,?)");

            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2,user.getFirst_name());
            preparedStatement.setString(3,user.getLast_name());
            preparedStatement.setInt(4,user.getAge());
            preparedStatement.setInt(5, user.getAddress_id());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
