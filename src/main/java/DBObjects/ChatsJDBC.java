package DBObjects;

import java.sql.*;
import java.util.ArrayList;

public class ChatsJDBC {
    public static ChatsJDBC here;

    static {
        here = new ChatsJDBC();
    }

    private String host, loginDB, passwordDB, table;

    private ChatsJDBC() {
        host = "jdbc:postgresql://localhost:5432/CosmodromDB";
        loginDB = "postgres";
        passwordDB = "12345678";
        table = "chats";
    }

    public int getChat(int[] users) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            // get users[] like String
            String usersIDStr = DBObjectUtils.strFromIntArray(users);
            // check is chat was created
            System.out.println("select id from " + table + " where (users_id <@ '{" + usersIDStr + "}' and users_id @> '{" + usersIDStr + "}');");
            ResultSet rs = statement.executeQuery("select id from " + table + " where (users_id <@ '{" + usersIDStr + "}' and users_id @> '{" + usersIDStr + "}');");
            if (rs.next()) {
                return rs.getInt(1);
            }
            System.out.println("ChatJDBC#getChat: Chat not found. Creating...");
            // make String from names from userProfiles
            ArrayList<UsersProfilesJDBC.UserProfile> userProfiles = UsersProfilesJDBC.here.getUsersProfiles(users);
            StringBuilder namesSB = new StringBuilder(userProfiles.get(0).getName());
            for (int i = 1; i < userProfiles.size(); i++) {
                namesSB.append(", " + userProfiles.get(i).getName());
            }
            // insert new chat to table
            System.out.println("insert into " + table + " (name, users_id) " +
                    "values ('" + namesSB.toString() + "', '" + usersIDStr + "') returning id;");
            rs = statement.executeQuery("insert into " + table + " (name, users_id) " +
                    "values ('" + namesSB.toString() + "', '{" + usersIDStr + "}') returning id;");

            rs.next();
            // create Messages table for this chat
            MessagesJDBC.here.createTable(rs.getInt(1));
            UsersProfilesJDBC.here.addUsersToChat(users, rs.getInt(1));
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(C#getChat) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public ArrayList<Chat> getUserChats(int userProfileID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            ArrayList<Chat> chats = new ArrayList<>();

            String chatIDStr = DBObjectUtils.strFromIntArray(
                    UsersProfilesJDBC.here.getUserChats(userProfileID));

            System.out.println("C#getUserChats" + "select * from " + table + " where id=any(array[" + chatIDStr + "]);");
            ResultSet rs = statement.executeQuery("select * from " + table + " where id=any(array[" + chatIDStr + "]);");
            while (rs.next()) {
                chats.add(new Chat(rs.getInt(1), rs.getString(2), (Integer[]) rs.getArray(3).getArray()));
            }
            return chats;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(C#getUserChats) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }

    public ArrayList<UsersProfilesJDBC.UserProfile> getChatUsers(int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ArrayList<UsersProfilesJDBC.UserProfile> userProfiles = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select users_id from " + table + " where id=" + chatID + ";");
            rs.next();
            Integer[] usersIDs = (Integer[]) rs.getArray(1).getArray();
            for (int userID : usersIDs) {
                userProfiles.add(UsersProfilesJDBC.here.getUserProfile(userID));
            }
            return userProfiles;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(C#getChatUsers) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }

    public int getChatUsersCount(int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            ResultSet rs = statement.executeQuery("select users_id from " + table + " where id=" + chatID + ";");
            rs.next();
            return ((Object[]) rs.getArray(1).getArray()).length;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(C#getChatUsersCount) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    public class Chat {
        private int id;
        private String name;
        private Integer[] users_id;

        public Chat(int id, String name, Integer[] users_id) {
            this.id = id;
            this.name = name;
            this.users_id = users_id;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Integer[] getUsers_id() {
            return users_id;
        }
    }
}
