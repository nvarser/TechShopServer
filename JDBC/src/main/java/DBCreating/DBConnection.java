package DBCreating;

import com.mysql.cj.jdbc.ConnectionImpl;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static ConnectionImpl connection = null;

    public static void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");// reflection API !!!!
            System.out.println("MySQL JDBC Driver Registered\n");
        }
        catch (ClassNotFoundException e) {
            System.out.println(" Where is your MySQL JDBC Driver \n");
            e.printStackTrace();
            throw new SQLException();
        }
        connection = (ConnectionImpl) DriverManager.getConnection("jdbc:mysql://localhost/courseworkdb?useUnicode=true,serverTimezone=UTC","root","ArtyomNovikov2003");
        if (connection == null) {
            throw new SQLException();
        }
        else
        {
            System.out.println("Successfully connected");
        }
    }

    public static void close() {
        try {
            if(connection != null) {
                connection.close();
                System.out.println("Closing connection");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}