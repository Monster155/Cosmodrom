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
            if (!rs.next()) return -1;

            password = String.valueOf(hashPass(password));
            if (rs.getString("password").equals(password))
                return rs.getInt("id");
            else return -1;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#validate) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public int validate(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + table + " where email='" + email + "';");
            if (!rs.next()) return -1;

//            if (rs.getString("password").equals("-1"))
            return rs.getInt("id");
//            else return -1;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#validate) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public int getUser(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from " + table + " where email=?;")) {
            Class.forName("org.postgresql.Driver");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) return -1;
            return rs.getInt("id");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#validate) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public boolean contains(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "select * from " + table + " where email=?;")) {
            Class.forName("org.postgresql.Driver");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#contains) " + e.getMessage() + " : " + e.getCause());
            //TODO what type is better? True or False?
            return true;
        }
    }

    public String restore(int userID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "update " + table + " set password=? where id=?;");
             PreparedStatement getPass = connection.prepareStatement("select MD5((random() * 100)::text);")) {
            Class.forName("org.postgresql.Driver");

            ResultSet rs = getPass.executeQuery();
            rs.next();
            String newPass = rs.getString(1);
            String hashPass = String.valueOf(hashPass(newPass));
            statement.setString(1, hashPass);
            statement.setInt(2, userID);
            statement.execute();

            return newPass;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#update) " + e.getMessage() + " : " + e.getCause());
            return "";
        }
    }

    private long hashPass(String password) {
        long hash = 0;
        // pass
        for (int i = 0; i < password.length(); i++) {
            hash = hash * 2 + password.charAt(i);
        }
        // salt
        for (int i = 6; i < password.length(); i += 3) { //pass min 8 characters
            hash = hash * 2 + password.charAt(i);
        }
        return hash;
    }

    public int add(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "insert into " + table + " (email, password) values (?, ?) returning id;")) {
            Class.forName("org.postgresql.Driver");

            password = String.valueOf(hashPass(password));
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UL#add) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public int add(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "insert into " + table + " (email, password) values (?, '-1') returning id;")) {
            Class.forName("org.postgresql.Driver");
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
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
