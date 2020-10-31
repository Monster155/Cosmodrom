package DBObjects;

import java.sql.*;

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

    public int add(String email, String password) {
        try (Connection connection = DriverManager.getConnection(host, loginDB, passwordDB);
             Statement statement = connection.createStatement()) {
            Class.forName("org.postgresql.Driver");
            ResultSet rs = statement.executeQuery("insert into " + table + " (email, password) " +
                    "values ('" + email + "', '" + password + "') returning id;");
            rs.next();
            return rs.getInt(1);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("(M#add) " + e.getMessage() + " : " + e.getCause());
            return -1;
        }
    }

    //TODO update
    //TODO delete
    //TODO get

    private class Message {
        int id;
        int chat_id;
        String text;
        int sender_id;
        String timestamp;
    }
}
