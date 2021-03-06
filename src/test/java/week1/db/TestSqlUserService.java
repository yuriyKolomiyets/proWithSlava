package week1.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import week1.model.Address;
import week1.model.User;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;


public class TestSqlUserService {

    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASS = "";

    private Connection conn = null;
    private Statement stmt = null;
    private SqlUserServiceImpl sqlUserService;

    public TestSqlUserService() throws SQLException {
    }

    @Before
    public void before() throws ClassNotFoundException, SQLException {

        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Class.forName("org.h2.Driver");

        sqlUserService = new SqlUserServiceImpl(conn);

        stmt = conn.createStatement();

        String sql1 =  "CREATE TABLE address(id int NOT NULL PRIMARY KEY, city VARCHAR(20), street VARCHAR(40), house int)";
        stmt.executeUpdate(sql1);

        String sql2 =  "CREATE TABLE users(id int NOT NULL PRIMARY KEY, address_id int NOT NULL, first_name VARCHAR(40), last_name VARCHAR(40), age int, CONSTRAINT FK_address FOREIGN KEY (address_id) REFERENCES address (id))";
        stmt.executeUpdate(sql2);

        String sql3 =  "ALTER TABLE users MODIFY COLUMN id INT auto_increment";
        stmt.executeUpdate(sql3);

        String sql4 =  "INSERT INTO address (id, city, street, house) VALUES (1, 'Kyiv', 'street1', 1)";
        stmt.executeUpdate(sql4);

        String sql5 =  "INSERT INTO users (id, first_name, last_name, age, address_id) VALUES (1, 'Oleg', 'Kuznetsov', 20, 1);";
        stmt.executeUpdate(sql5);

    }

    @After
    public void after() throws SQLException {
        stmt.execute("DROP TABLE users");
        stmt.execute("DROP TABLE address");

        if (stmt != null) stmt.close();
        if (conn != null) conn.close();


    }

    @Test
    public void createUser() throws SQLException {

        String firstName = "Yura";
        String lastName = "Kolomiyets";
        int age = 30;
        Address address = new Address(2, "Kyiv", "Vul", 2);

        User ret = sqlUserService.create(firstName, lastName, age, address);
        assertTrue(ret.getAge() == 30);
    }

    @Test
    public void deleteUser() throws SQLException {

        String selectSQL = "SELECT * FROM users WHERE last_name = 'Kuznetsov'";
        PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();


        int userId = 0;
        while (resultSet.next()){
            userId = resultSet.getInt("id");
        }

        sqlUserService.delete(userId);
        assertNull(sqlUserService.getUserById(userId));

    }

    @Test
    public void testChangeAddress() throws SQLException {

        String selectSQL = "SELECT * FROM users WHERE last_name = 'Kuznetsov'";
        PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        int userId = 0;
        while (resultSet.next()) {
            userId = resultSet.getInt("id");
        }

        Address newAddress = new Address(2, "Lviv", "Somestreet", 5);
        User ret = sqlUserService.changeAddress(userId, newAddress);

        assertEquals(ret.getAddress().getCity(), "Lviv");
        assertEquals(ret.getAddress().getHouse(), 5);

    }

    @Test
    public void findByFistNameAndLastName() throws SQLException {

        List<User> ret = sqlUserService.findByFistNameAndLastName("Oleg", "Kuznetsov");

        assertTrue(ret.size() == 1);
        assertTrue(ret.get(0).getFirst_name().equals("Oleg"));

    }
}

