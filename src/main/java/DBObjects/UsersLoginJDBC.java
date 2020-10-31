package DBObjects;

import java.sql.*;
import java.util.ArrayList;

public class UsersLoginJDBC {
    public static UsersLoginJDBC here;

    static {
        here = new UsersLoginJDBC();
    }

    private ArrayList<User> users;
    private String host, loginDB, passwordDB, tableLogin;
    private String lastWhere;
    private int lastLimit;

    private UsersLoginJDBC() {
        users = new ArrayList<>();

        host = "jdbc:postgresql://localhost:5432/CosmodromDB";
        loginDB = "postgres";
        passwordDB = "12345678";
        tableLogin = "usersLogin";

        lastWhere = "";
        lastLimit = 0;

    }

    public int validate(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + tableLogin + " where email='" + email + "';");
            rs.next();
            //TODO hash pass
            if (rs.getString("password").equals(password))
                return rs.getInt("id");
            else return -1;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public boolean contains(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + tableLogin + " where email='" + email + "';");
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public int add(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("insert into " + tableLogin + " (email, password) " +
                    "values ('" + email + "', '" + password + "') returning id;");
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UserLogin#add) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

//    private void getFromDB(String where, int limit) {
//        if (where == null) where = "";
//        if (!where.equals("")) where = " where " + where;
//        if (limit > 0) where += " limit " + limit;
//        users = new ArrayList<>();
//        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
//             Statement statement = connection.createStatement()) {
//            Class.forName("org.postgresql.Driver");
//            ResultSet rs;
//            rs = statement.executeQuery("select * from " + tableLogin + where + ";");
//            while (rs.next()) {
////                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), (Integer[]) rs.getArray(6).getArray()));
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println(e.getMessage() + " : " + e.getCause());
//        }
//    }

    private class User {
        int id;
        String email;
        String password;
    }
}
