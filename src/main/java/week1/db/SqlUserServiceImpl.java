package week1.db;

import week1.model.Address;
import week1.model.User;
import week1.utils.PathUtils;

import java.sql.*;

public class SqlUserServiceImpl implements SqlUserService {

    public static final String URL = PathUtils.getDbPath();
    public static final String USER = PathUtils.getDbLogin();
    public static final String PASSWORD = PathUtils.getDbPass();
    private Connection connection;

    private User user;
    private PreparedStatement preparedStatement;
    private Statement statement;
    public SqlUserServiceImpl() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public User create(String firstName, String lastName, int age, Address address) throws SQLException {

        int id = findMaxUserId() + 1;

            user = new User(id, firstName, lastName, age, address);
            preparedStatement = connection.prepareStatement
                    ("INSERT INTO users(first_name, last_name, age, adress_id) VALUES (?,?,?,?)");
            preparedStatement.setString(1, user.getFirst_name());
            preparedStatement.setString(2, user.getLast_name());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setInt(4, setAddressId());

            preparedStatement.execute();

        return user;
    }

    @Override
    public User findByFistNameAndLastName(String firstName, String lastName) throws SQLException {

        ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE first_name IS ? AND last_name IS ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            preparedStatement.execute();

            while (resultSet.next()) {
                //todo write logic how to get user from set
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
    public void delete(Integer userId) throws SQLException {

            preparedStatement = connection.prepareStatement
                    ("DELETE INTO users WHERE id IS ?");

            preparedStatement.setInt(1, userId);
            preparedStatement.execute();


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

    private void addNewAddressToDB(String city, String street, int house) throws SQLException {

        int id = findMaxAddressId() + 1;

            preparedStatement = connection.prepareStatement
                    ("INSERT INTO address(id, city, street, house) VALUES (?, ?,?,?)");

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, city);
            preparedStatement.setString(3, street);
            preparedStatement.setInt(4, house);

            preparedStatement.execute();

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
