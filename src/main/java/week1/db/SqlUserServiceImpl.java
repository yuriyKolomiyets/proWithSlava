package week1.db;

import week1.model.Address;
import week1.model.User;

import java.sql.*;

public class SqlUserServiceImpl implements SqlUserService {

    //todo replace it to the resources
    private static final String URL = "jdbc:mysql://localhost/TEST2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private User user;
    private PreparedStatement preparedStatement;
    private Statement statement;

    //todo ﻿no need to open connection every time, you may reuse the same one opened in constructor for example.
    //todo ﻿ try to extract connection from methods to the property (class level), otherwise it will be hard to write unit tests.

    @Override
    public User create(String firstName, String lastName, int age, Address address) {

        int id = findMaxUserId() + 1;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            user = new User(id, firstName, lastName, age, address);
            preparedStatement = connection.prepareStatement
                    ("INSERT INTO users(first_name, last_name, age, adress_id) VALUES (?,?,?,?)");
            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, setAddressId());

            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByFistNameAndLastName(String firstName, String lastName) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE first_name IS ? AND last_name IS ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            preparedStatement.execute();

            while (resultSet.next()) {
                //todo write logic how to get user from set
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User changeAddress(Integer userId, Address newAddress) {

        //todo 1. find user by Id
        //todo 2. get his addressId
        //todo 3. check if new address is in the db. if yes just change addressId for user. if no — addAddress, setid
        return null;
    }

    @Override
    public void delete(Integer userId) {

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            preparedStatement = connection.prepareStatement
                    ("DELETE INTO users WHERE id IS ?");

            preparedStatement.setInt(1, userId);
            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int setAddressId() {

        int id = 0;

        if (addressPresentInDb()){
            id = findAddressId();
        } else {
            id = findMaxAddressId() +  1;
        }
        return id;
    }

    private void addNewAddressToDB(String city, String street, int house) {

        int id = findMaxAddressId() + 1;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO address(id, city, street, house) VALUES (?, ?,?,?)");

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            preparedStatement.setInt(4, house);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int findAddressId() {
        return 0;
    }

    private boolean addressPresentInDb(){
        return false;
    }

    private int findMaxAddressId(){
        return 0;
    }

    private int findMaxUserId(){
        return 0;
    }
}
