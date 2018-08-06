package week1.db;

public class User {

   private int id;
   private String name;
   private Double salary;
   private String phone;

    public User(int id, String name, double salary, String phone) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", phone='" + phone + '\'' +
                '}';
    }
}
