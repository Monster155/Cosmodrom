package DBObjects;

import org.postgresql.util.Base64;

import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;

public class UsersProfilesJDBC {
    public static UsersProfilesJDBC here;

    static {
        here = new UsersProfilesJDBC();
    }

    private final String host, loginDB, passwordDB, table;

    private UsersProfilesJDBC() {
        host = "jdbc:postgresql://localhost:5432/CosmodromDB";
        loginDB = "postgres";
        passwordDB = "12345678";
        table = "usersProfiles";
    }

    public int add(int userID, String name, String surname, String description, InputStream photoIS) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "insert into " + table + " (user_id, name, surname, description, photo)" +
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

    public String getJSON(int profileID) {
        return getUserProfile(profileID).toJSON();
    }

    public int getUserProfileID(int userID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select id from " + table + " where user_id=" + userID + ";");
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getUserProfileID) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public Integer[] getUserChats(int userProfileID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select chats from " + table + " where id=" + userProfileID + ";");
            rs.next();
//            System.out.println(rs.toString());
            if (rs.getArray(1) != null)
                return (Integer[]) rs.getArray(1).getArray();
            return null;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getUserChats) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }

    public UserProfile getUserProfile(int profileID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select * from " + table + " where id='" + profileID + "';");
            rs.next();
            Integer[] chats = rs.getArray(7) != null
                    ? (Integer[]) rs.getArray(7).getArray() : new Integer[]{};
            UserProfile userProfile = new UserProfile(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getBytes(6),
                    chats);
            return userProfile;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getUserProfile) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }

    /*public String getUsersNames(int[] ids) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            StringBuilder names = new StringBuilder();

            for (int id : ids) {
                names.append(
                        statement.executeQuery("select names from " + table + " where id='" + id + "';")
                                .getString(1) + ", ");
            }

            return names.toString();

            // TODO wait Azat's answer
//            ResultSet rs = statement.executeQuery("select names from " + table + " where id='" + id + "';");
//            while (rs.next()) {
//                names.append(rs.getString(1) + ", ");
//            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getUsersNames) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }*/

    public class UserProfile {
        private int id;
        private int user_id;
        private String name;
        private String surname;
        private String description;
        private String photo;
        private Integer[] chats;

        public UserProfile(int id, int user_id, String name, String surname, String description, byte[] photo, Integer[] chats) {
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
