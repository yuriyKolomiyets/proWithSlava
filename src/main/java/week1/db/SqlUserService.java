package week1.db;

import week1.model.Address;
import week1.model.User;

import java.sql.SQLException;
import java.util.List;

public interface SqlUserService {

    User create(String firstName, String lastName, int age, Address address) throws SQLException;

    List<User> findByFistNameAndLastName(String firstName, String lastName) throws SQLException;

    User changeAddress(Integer userId, Address newAddress) throws SQLException;

    void delete(Integer userId) throws SQLException;
}