package DBObjects;

import java.sql.*;
import java.util.ArrayList;

public class Users {
    public static Users here;

    static {
        here = new Users();
    }

    private ArrayList<User> users;
    private String host, loginDB, passwordDB, table;
    private String lastWhere;
    private int lastLimit;

    private Users() {
        users = new ArrayList<>();

        host = "jdbc:postgresql://localhost:5432/CosmodromDB";
        loginDB = "postgres";
        passwordDB = "12345678";
        table = "users";

        lastWhere = "";
        lastLimit = 0;

        getFromDB("", 30);
    }

    public void updateProducts(String where, int limit) {
        getFromDB(where, limit);
    }

    public ArrayList<User> getUsers() {
        return (ArrayList<User>) users.clone();
    }

    public boolean validate(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select password from " + table + " where email='" + email + "';");
            rs.next();
            //TODO hash pass
            return rs.getString(1).equals(password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public boolean contains(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + table + " where email='" + email + "';");
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public void add(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            statement.execute("insert into " + table + " (email, password) values ('" + email + "', '" + password + "');");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    private void getFromDB(String where, int limit) {
        if (where == null) where = "";
        if (!where.equals("")) where = " where " + where;
        if (limit > 0) where += " limit " + limit;
        users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs;
            rs = statement.executeQuery("select * from " + table + where + ";");
            while (rs.next()) {
//                users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), (Integer[]) rs.getArray(6).getArray()));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    private class User {
        int id;
        int user_id;
        String name;
        String surname;
        String description;
        String photo;
        //TODO add chat list
        Integer[] chats;
    }
}
