package week1.db;

import week1.model.Address;
import week1.model.User;
import week1.utils.PathUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlUserServiceImpl implements SqlUserService {

    private Connection connection;

    private PreparedStatement preparedStatement;
    private Statement statement;

    public SqlUserServiceImpl(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
    }

    @Override
    public User create(String firstName, String lastName, int age, Address address) throws SQLException {

        int id = findMaxUserId() + 1;

        User user = new User(id, firstName, lastName, age, address);

        if (!addressPresentInDb(address.getCity(), address.getStreet(), address.getHouse())){
            addNewAddressToDB(address.getCity(), address.getStreet(), address.getHouse());
        }

        preparedStatement = connection.prepareStatement
                ("INSERT INTO users(first_name, last_name, age, address_id) VALUES (?,?,?,?)");
        preparedStatement.setString(1, user.getFirst_name());
        preparedStatement.setString(2, user.getLast_name());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setInt(4, getAddressId(address.getCity(), address.getStreet(), address.getHouse()));

        preparedStatement.execute();

        return user;
    }

    @Override
    public List<User> findByFistNameAndLastName(String firstName, String lastName) throws SQLException {

        String selectSQL = "SELECT * FROM users WHERE first_name = ? AND last_name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        ResultSet resultSet = preparedStatement.executeQuery();

        return createUserListFromSet(resultSet);
    }

    @Override
    public User changeAddress(Integer userId, Address newAddress) throws SQLException {

        Address curAddress = getAddressById(userId);

        if (newAddress.getId() == curAddress.getId()) {

            return getUserById(userId);

        } else if (addressPresentInDb(newAddress.getCity(), newAddress.getStreet(), newAddress.getHouse())) {

            changeAddressId(userId, newAddress);

            return getUserById(userId);

        } else {
            addNewAddressToDB(newAddress.getCity(), newAddress.getStreet(), newAddress.getHouse());
            changeAddressId(userId, newAddress);

            return getUserById(userId);
        }
    }

    @Override
    public void delete(Integer userId) throws SQLException {

        preparedStatement = connection.prepareStatement
                ("DELETE FROM users WHERE id = ?");

        preparedStatement.setInt(1, userId);
        preparedStatement.execute();

    }

    private void changeAddressId(Integer userId, Address newAddress) throws SQLException {

        String selectSQL = "UPDATE users SET address_id = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, newAddress.getId());
        preparedStatement.setInt(2, userId);

        preparedStatement.execute();
        
    }

    public User getUserById(Integer userId) throws SQLException {

        User user = new User();

        String selectSQL = "SELECT * FROM users WHERE id =  ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

       if (!resultSet.isBeforeFirst()){
           return null;
       } else {

           while (resultSet.next()) {

               user.setId(resultSet.getInt("id"));
               user.setFirst_name(resultSet.getString("first_name"));
               user.setLast_name(resultSet.getString("last_name"));
               user.setAge(resultSet.getInt("age"));
               user.setAddress(getAddressById(resultSet.getInt("address_id")));
           }

           return user;
       }
    }

    private List<User> createUserListFromSet(ResultSet resultSet) throws SQLException {

        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {

            userList.add(new User(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getInt("age"),
                            new Address(getAddressById(resultSet.getInt("address_id")))
                    )
            );
        }
        return userList;
    }

    private Address getAddressById(int address_id) throws SQLException {

        Address address = new Address();

        String selectSQL = "SELECT * FROM address WHERE id =  ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setInt(1, address_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            address.setId(resultSet.getInt("id"));
            address.setCity(resultSet.getString("city"));
            address.setStreet(resultSet.getString("street"));
            address.setHouse(resultSet.getInt("house"));
        }

        return address;
    }

    private int getAddressId(String city, String street, int house) throws SQLException {

        int id;

        if (addressPresentInDb(city, street, house)) {
            id = findAddressId(city, street, house);
        } else {
            id = findMaxAddressId() +  1;
        }
        return id;
    }

    private int findAddressId(String city, String street, int house) throws SQLException {

        int id = 0;

        String selectSQL = "SELECT id FROM address WHERE city = ? AND street = ? AND house =  ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, city);
        preparedStatement.setString(2, street);
        preparedStatement.setInt(3, house);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            id = resultSet.getInt("id");
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


    private boolean addressPresentInDb(String city, String street, int house) throws SQLException {

        String selectSQL = "SELECT * FROM address WHERE city = ? AND street = ? AND house =  ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
        preparedStatement.setString(1, city);
        preparedStatement.setString(2, street);
        preparedStatement.setInt(3, house);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(!resultSet.next()){
            return false;
        } else {
            return true;
        }
    }

    private int findMaxAddressId() throws SQLException {
        int maxAddressId = 0;

        ResultSet resultSet = statement.executeQuery("SELECT max (id) FROM address ");
        while (resultSet.next()) {
            maxAddressId = resultSet.getInt(1);
        }
        return maxAddressId;
    }

    private int findMaxUserId() throws SQLException {

        int maxUserId = 0;

        ResultSet resultSet = statement.executeQuery("SELECT max (id) FROM users");
        while (resultSet.next()) {
            maxUserId = resultSet.getInt(1);
        }
        return maxUserId;

    }
}
