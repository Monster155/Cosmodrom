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

    public boolean update(int profileID, String name, String surname, String description, InputStream photoIS) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "update usersProfiles set name=?, surname=?, description=?, photo=? where id=?;"
             )) {
            Class.forName("org.postgresql.Driver");

            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, description);
            statement.setBinaryStream(4, photoIS);
            statement.setInt(5, profileID);

            return statement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#update) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public boolean update(int profileID, String name, String surname, String description) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             PreparedStatement statement = connection.prepareStatement(
                     "update usersProfiles set name=?, surname=?, description=? where id=?;"
             )) {
            Class.forName("org.postgresql.Driver");

            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, description);
            statement.setInt(4, profileID);

            return statement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#update) " + e.getMessage() + " : " + e.getCause());
            return false;
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
            System.out.println("UP#getUserChats: " + "select chats from " + table + " where id=" + userProfileID + ";");
            ResultSet rs = statement.executeQuery("select chats from " + table + " where id=" + userProfileID + ";");
            rs.next();
//            System.out.println(rs.toString());
            if (rs.getArray(1) != null)
                return (Integer[]) rs.getArray(1).getArray();
            return new Integer[]{-1};
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

    public ArrayList<UserProfile> getUsersProfiles(int[] ids) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            StringBuilder idSB = new StringBuilder();
            idSB.append(ids[0]);
            for (int i = 1; i < ids.length; i++) {
                idSB.append(", " + ids[i]);
            }

            ResultSet rs = statement.executeQuery("select * from " + table + " where id=any(array[" + idSB.toString() + "]);");
            return generateArrayList(rs);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getUsersNames) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }

    public ArrayList<UserProfile> getUsersProfiles(String name) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            name = DBObjectUtils.shieldText(name);
            //TODO make it better (example: Damir Dav, Dav Damir, Damir, Dav)
            ResultSet rs = statement.executeQuery("select * from " + table +
                    " where surname='" + name + "' or name='" + name + "';");
            return generateArrayList(rs);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getUsersNames) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }

    public boolean addUsersToChat(int[] profileID, int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            String profileIDStr = DBObjectUtils.strFromIntArray(profileID);

            return statement.execute("update " + table +
                    " set chats = chats || '{" + chatID + "}' WHERE id = ANY (ARRAY [" + profileIDStr + "]);");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(UP#getUserProfile) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    private ArrayList<UserProfile> generateArrayList(ResultSet rs) throws SQLException {
        ArrayList<UserProfile> userProfiles = new ArrayList<>();
        while (rs.next()) {
            Integer[] chats = rs.getArray(7) != null
                    ? (Integer[]) rs.getArray(7).getArray() : new Integer[]{};
            userProfiles.add(new UserProfile(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getBytes(6),
                    chats));
        }
        return userProfiles;
    }

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

        public int getProfileID() {
            return id;
        }

        public int getLoginID() {
            return user_id;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getDescription() {
            return description;
        }

        public String getPhoto() {
            return photo;
        }

        public Integer[] getChats() {
            return chats;
        }
    }
}
