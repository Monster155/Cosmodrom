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
                    chats.add(new Chat(rs.getInt(1), rs.getString(2)));
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

    public class Chat {
        private int id;
        private String name;

        public Chat(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
