import DAO.UserDAO;
import DBCreating.CreateDB;
import Entitites.User;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class JDBC_CRUDTest {
    private static final String ADRESS = "jdbc:mysql://localhost:3306/jdbctest";
    private static final String LOGIN="root";
    private static final String PASSWORD="root";
    private static final String TABLE_NAME="programmers";

    @Test
    public void testClass() {
        try {
            new CreateDB();
        }
        catch(RuntimeException e) {
            e.printStackTrace();
            fail("Failed to connect. SQLException.");
        }
    }


    @Test
    public void testCreate() {
        try {
            CreateDB createDB = new CreateDB();
            createDB.insertIntoUsers("Vasya","Pupkin",
                        "akonvik@mail.ru","login","password","A");
            createDB.insertIntoUsers("Глеб","Григорьев",
                    "gregor@gmail.com","gleb","glebabon","A");
            createDB.insertIntoUsers("Masha","Novikova",
                    "maria@mail.com","masha","mashka","A");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to create new user. SQLException");
        }
    }

    @Test
    public void testDelete() {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.delete(14);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to delete user. SQLException");
        }
    }

    @Test
    public void testRead() {
        try {
            UserDAO userDAO = new UserDAO();
            userDAO.fetchByLogin("login");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to read database. SQLException");
        }
    }

    @Ignore
    @Test
    public void truncateTable() {
        try {
            CreateDB createDB = new CreateDB();
            createDB.clearTableUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to truncate table users. SQLException");
        }
    }

    @Test
    public void testUpdate() {
        try {
            UserDAO userDAO = new UserDAO();
            User user = new User();
            user.setId(10);
            user.setName("Cheburek");
            user.setSurname("novikov");
            user.setRole("A");
            user.setEMail("hello@mail.ru");
            user.setPassword("parol");
            user.setLogin("Chebchik");
            userDAO.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to update database users. SQLException");
        }
    }
}
