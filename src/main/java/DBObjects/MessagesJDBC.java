package DBObjects;

import java.sql.*;
import java.util.ArrayList;

public class MessagesJDBC {
    public static MessagesJDBC here;

    static {
        here = new MessagesJDBC();
    }

    private String host, loginDB, passwordDB, table;

    private MessagesJDBC() {
        host = "jdbc:postgresql://localhost:5432/CosmodromDB";
        loginDB = "postgres";
        passwordDB = "12345678";
        table = "messages";
    }

//    public int add() {
//        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
//             Statement statement = connection.createStatement()) {
//            Class.forName("org.postgresql.Driver");
//            ResultSet rs = statement.executeQuery("insert into " + table + " (email, password) " +
//                    "values ('" + email + "', '" + password + "') returning id;");
//            rs.next();
//            return rs.getInt(1);
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println("(M#add) " + e.getMessage() + " : " + e.getCause());
//            return -1;
//        }
//    }

    //TODO update
    //TODO delete
    //TODO get

    public ArrayList<Message> getMessages(int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ArrayList<Message> messages = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select * from " + table + " where chat_id=" + chatID + ";");
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getTimestamp(5)));
            }
            return messages;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(M#getMessages) " + e.getMessage() + " : " + e.getCause());
            return null;
        }
    }

    public class Message {
        int id;
        int chat_id;
        String text;
        int sender_id;
        Timestamp timestamp;

        public Message(int id, int chat_id, String text, int sender_id, Timestamp timestamp) {
            this.id = id;
            this.chat_id = chat_id;
            this.text = text;
            this.sender_id = sender_id;
            this.timestamp = timestamp;
        }

        public int getId() {
            return id;
        }

        public int getChat_id() {
            return chat_id;
        }

        public String getText() {
            return text;
        }

        public int getSender_id() {
            return sender_id;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }
    }
}
