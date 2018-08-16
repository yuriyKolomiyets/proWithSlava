package week1.db;

import week1.model.Address;
import week1.model.User;

public interface SqlUserService {

    User create(String firstName, String lastName, int age, Address address);

    User findByFistNameAndLastName(String firstName, String lastName);

    User changeAddress(Integer userId, Address newAddress);

    void delete(Integer userId);
}