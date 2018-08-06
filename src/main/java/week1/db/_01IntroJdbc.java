package week1.db;

import java.sql.*;

public class _01IntroJdbc {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ACP11", "root", "pass1");
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INFO users (id,name,phone, salary) VALUES 1, 'Olag', '+234', 2.00");
            resultSet = statement.executeQuery("SELECT id,name,phone, salary");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                double salary = resultSet.getDouble("salary");

                System.out.println(new User(id, name, salary, phone));



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            if (resultSet != null){
                try {
                    resultSet.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } if (statement != null ){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } if ( connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
