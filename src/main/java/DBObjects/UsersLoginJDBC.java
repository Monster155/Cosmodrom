package DBObjects;

import java.sql.*;

public class UsersLoginJDBC {
    public static UsersLoginJDBC here;

    static {
        here = new UsersLoginJDBC();
    }

    private final String host, loginDB, passwordDB, table;

    private UsersLoginJDBC() {
        host = "jdbc:postgresql://localhost:5432/CosmodromDB";
        loginDB = "postgres";
        passwordDB = "12345678";
        table = "usersLogin";
    }

    public int validate(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + table + " where email='" + email + "';");
            rs.next();
            //TODO hash pass
            if (rs.getString("password").equals(password))
                return rs.getInt("id");
            else return -1;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#validate) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public boolean contains(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + table + " where email='" + email + "';");
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#contains) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public int add(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("insert into " + table + " (email, password) " +
                    "values ('" + email + "', '" + password + "') returning id;");
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#add) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    private class UserLogin {
        int id;
        String email;
        String password;
    }
}
