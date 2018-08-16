package week1.db;

import week1.model.Address;
import week1.model.User;

import java.sql.*;

public class DbExecute {

    public static final String URL =
            "jdbc:mysql://localhost/TEST2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    private static int addressId;

    public static void main(String[] args) throws ClassNotFoundException {

        User user = new User(103, "Test4", "Test4", 100,
                new Address(1, "Kiev", "some street", 2));

        Class.forName("com.mysql.cj.jdbc.Driver");
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        Statement statement = null;
        ResultSet resultSet;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO address(city,street, house) VALUES (?,?,?)");

            preparedStatement.setString(1, user.getAddress().getCity());
            preparedStatement.setString(2, user.getAddress().getStreet());
            preparedStatement.setInt(3, user.getAddress().getHouse());

            System.out.println(preparedStatement.execute() + "INSERT INTO address");

            preparedStatement1 = connection.prepareStatement
                    ("INSERT INTO users(first_name, last_name, age, adress_id) VALUES (?,?,?,?)");

            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, getAddressId());

            System.out.println(preparedStatement1.execute() + "INSERT INTO users");

            resultSet = statement.executeQuery("SELECT id FROM users");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                System.out.println("user id" + " " + id);
            }

            statement.executeUpdate("UPDATE users SET first_name = 'LOL' WHERE last_name = 'Test3'");

            preparedStatement2 = connection.prepareStatement
                    ("DELETE FROM users WHERE id = 3");

            preparedStatement2.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getAddressId() throws SQLException {
        Statement statement1 = null;
        int maxId = 0;

        ResultSet resultSet1 = statement1.executeQuery("SELECT id FROM users WHERE ");

        if (resultSet1 != null){

            addressId = 0;

        } else {
            addressId=maxId++;
        }

        return addressId;
    }
}

