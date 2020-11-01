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

    /*public int getChat(int[] users) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("select id from " + table + " where users_id=" + users + ";");
            if (rs.next()) {
                return rs.getInt(1);
            }
            UsersProfilesJDBC.here.getUsersNames(users);
            rs = statement.executeQuery("insert into " + table + " (name, users_id) " +
                    "values ('', '" + users + "') returning id;");
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(C#getChat) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }*/

    public ArrayList<Chat> getUserChats(int userProfileID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            Integer[] chatsIDs = UsersProfilesJDBC.here.getUserChats(userProfileID);
            ArrayList<Chat> chats = new ArrayList<>();
            if (chatsIDs != null)
                for (Integer chatID : chatsIDs) {
                    ResultSet rs = statement.executeQuery("select * from " + table + " where id=" + chatID + ";");
                    rs.next();
                    chats.add(new Chat(rs.getInt(1), rs.getString(2), (Integer[]) rs.getArray(3).getArray()));
                }
            return chats;
            /*ResultSet rs = statement.executeQuery("select * from " + table + " where id=" + userID + ";");
            // TODO get chats[] from userProfiles by ID, get all chats and send back
            ArrayList<Chat> chats = new ArrayList<>();
            while (rs.next()) {
                chats.add(new Chat(rs.getInt(1), rs.getString(2)));
            }
            return chats;*/
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

    public int getChatUsersCount(int chatID){
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            ResultSet rs = statement.executeQuery("select users_id from " + table + " where id=" + chatID + ";");
            rs.next();
            return ((Object[])rs.getArray(1).getArray()).length;
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
