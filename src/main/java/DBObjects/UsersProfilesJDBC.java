package DBObjects;

import org.postgresql.util.Base64;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UsersProfilesJDBC {
    public static UsersProfilesJDBC here;

    static {
        here = new UsersProfilesJDBC();
    }

    private final ArrayList<User> users;
    private final String host;
    private final String loginDB;
    private final String passwordDB;
    private final String tableProfiles;
    private final String lastWhere;
    private final int lastLimit;

    private UsersProfilesJDBC() {
        users = new ArrayList<>();

        host = "jdbc:postgresql://localhost:5432/CosmodromDB";
        loginDB = "postgres";
        passwordDB = "12345678";
        tableProfiles = "usersProfiles";

        lastWhere = "";
        lastLimit = 0;
    }

    public boolean validate(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select password from " + tableProfiles + " where email='" + email + "';");
            rs.next();
            //TODO hash pass
            return rs.getString(1).equals(password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#validate) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public boolean contains(String email) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + tableProfiles + " where email='" + email + "';");
            return rs.next();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#contains) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public int add(int userID, String name, String surname, String description, InputStream photoIS) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "insert into " + tableProfiles + " (user_id, name, surname, description, photo)" +
                             " values (?, ?, ?, ?, ?) returning id;"
             )) {
            Class.forName("org.postgresql.Driver");

            statement.setInt(1, userID);
            statement.setString(2, name);
            statement.setString(3, surname);
            statement.setString(4, description);
            statement.setBinaryStream(5, photoIS);

            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#add) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public String getJSON(int id) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + tableProfiles + " where id='" + id + "';");
            rs.next();
            Integer[] chats = rs.getArray(7) != null
                    ? (Integer[]) rs.getArray(7).getArray() : new Integer[]{};
            User user = new User(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getBytes(6),
                    chats);
            return user.toJSON();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getJSON) " + e.getMessage() + " : " + e.getCause());
            return null;
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
        int user_id;
        String name;
        String surname;
        String description;
        String photo;
        Integer[] chats;

        public User(int id, int user_id, String name, String surname, String description, byte[] photo, Integer[] chats) {
            this.id = id;
            this.user_id = user_id;
            this.name = name;
            this.surname = surname;
            this.description = description;
            this.photo = Base64.encodeBytes(photo, Base64.DONT_BREAK_LINES);
            this.chats = chats;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", user_id=" + user_id +
                    ", name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", description='" + description + '\'' +
                    ", photo=" + photo +
                    ", chats=" + Arrays.toString(chats) +
                    '}';
        }

        public String toJSON() {
            return "{" +
                    "\"id\":" + id +
                    ", \"user_id\":" + user_id +
                    ", \"name\":\"" + name + '\"' +
                    ", \"surname\":\"" + surname + '\"' +
                    ", \"description\":\"" + description + '\"' +
                    ", \"photo\":\"" + photo + '\"' +
                    ", \"chats\":" + Arrays.toString(chats) +
                    "}";
        }
    }
}
