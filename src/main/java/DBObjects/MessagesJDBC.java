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

    public boolean add(int chatID, String text, int senderID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            if (text.equals("")) return false;

            text = DBObjectUtils.shieldText(text);
            System.out.println("insert into " + table + chatID +
                    " (text, sender_id) values ('" + text + "', " + senderID + ");");
            return statement.execute("insert into " + table + chatID +
                    " (text, sender_id) values ('" + text + "', " + senderID + ");");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(M#add) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public ArrayList<Message> getMessages(int chatID, int limit) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ArrayList<Message> messages = new ArrayList<>();
            ResultSet rs;
            if (limit <= 0)
                rs = statement.executeQuery("select * from " + table + chatID + " order by timestamp desc;");
            else
                rs = statement.executeQuery("select * from " + table + chatID + " order by timestamp desc limit " + limit + ";");
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

    public int getMessagesCount(int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ArrayList<Message> messages = new ArrayList<>();
            ResultSet rs = statement.executeQuery("select count(id) from " + table + chatID + ";");
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(M#getMessages) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    // TODO update
    public boolean update(int messageID, String newText, int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            return statement.execute("update " + table + chatID + " set text='" + newText + "' where id=" + messageID + ";");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(M#update) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }
    //TODO delete

    public boolean delete(int messageID, int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            return statement.execute("delete from " + table + chatID + " where id=" + messageID + ";");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(M#delete) " + e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public boolean createTable(int chatID) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");

            return statement.execute("" +
                    "create table messages" + chatID + "(" +
                    "            id        serial primary key," +
                    "            chat_id   int  default " + chatID + "," +
                    "            text      text not null," +
                    "            sender_id int  not null," +
                    "            timestamp timestamp default current_timestamp," +
                    "            foreign key (sender_id) references usersProfiles (id) on delete no action," +
                    "            foreign key (chat_id) references chats (id) on delete no action" +
                    "        );");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(M#createTable) " + e.getMessage() + " : " + e.getCause());
            return false;
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
