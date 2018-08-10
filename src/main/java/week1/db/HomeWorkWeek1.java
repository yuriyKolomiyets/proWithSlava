package week1.db;

import week1.model.User;

import java.sql.*;

public class HomeWorkWeek1 {

    public static final String URL =
            "jdbc:mysql://localhost/TEST1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "asusual1";

    public static void main(String[] args) throws ClassNotFoundException {

        User user = new User(103, "Test3", "Test3", 100, 1);

        Class.forName("com.mysql.cj.jdbc.Driver");
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        Statement statement = null;
        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO users(first_name, last_name, age, address_id) VALUES (?,?,?,?)");

            preparedStatement.setString(1,user.getFirst_name());
            preparedStatement.setString(2,user.getLast_name());
            preparedStatement.setInt(3,user.getAge());
            preparedStatement.setInt(4, user.getAddress_id());

            preparedStatement.execute();

            resultSet = statement.executeQuery("SELECT id FROM users");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                System.out.println(id);
            }

            statement.executeUpdate( "UPDATE users SET first_name = 'LOL' WHERE last_name = 'Test3'" );

            preparedStatement1 = connection.prepareStatement
                    ("DELETE FROM users WHERE id = 3");

            preparedStatement1.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
