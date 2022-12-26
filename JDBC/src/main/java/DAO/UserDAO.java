package DAO;

import DBCreating.DBConnection;
import Entitites.User;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    ConnectionImpl con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public int insert(User user) {
        con = DBConnection.connection;
        try {
            String test = "select * from users where Login=? or EMail=?";
            ps = con.prepareStatement(test);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getEMail());
            rs = ps.executeQuery();
            if (!rs.next() == false) return -1;
            String query = "insert into users(Name,Surname,EMAil,Login,Password,Role) "
                    + "values(?,?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEMail());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getRole());
            st = ps.executeUpdate();
            System.out.println("inserted users " + st);
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public int update(User user) throws SQLException {
        con = DBConnection.connection;
        try {
            String test = "select * from users where Login=? or EMail=?";
            ps = con.prepareStatement(test);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getEMail());
            rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println("Айди чэбджыка: " + rs.getInt("IDUs"));
                if (rs.getInt("IDUs") != user.getId()
                        && rs.getString("EMail") != user.getEMail()) return -1;
                System.out.println("Айди чэбджыка: " + rs.getInt("IDUs"));
            }
            String query = "update users set Name=?,Surname=?,EMail=?,Login=?,Password=?,Role=? where IDUs=?";
            ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getEMail());
            ps.setString(4, user.getLogin());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getRole());
            ps.setInt(7,user.getId());
            st = ps.executeUpdate();
            System.out.println("updated user " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
            throw new SQLException();
        }
        return st;
    }

    public int delete(int id) throws SQLException {
        con = DBConnection.connection;
        try {
            String query = "delete from users where IDUs=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted user " + st);
        } catch (Exception e) {
            st = -2;
            throw new SQLException();
        }
        return st;
    }

    public User fetchByLogin(User client) {
        User user = new User();
        con = DBConnection.connection;
        try {
            String query = "select * from users where Login=?";
            ps = con.prepareStatement(query);
            System.out.println(new Gson().toJson(client));
            ps.setString(1, client.getLogin());
            rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Hello!");
                user.setId(rs.getInt("IDUs"));
                user.setName(rs.getString("Name"));
                user.setSurname(rs.getString("Surname"));
                user.setEMail(rs.getString("EMail"));
                user.setLogin(rs.getString("Login"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User fetchByLogin(String login) throws SQLException {
        User user = new User();
        con = DBConnection.connection;
        try {
            System.out.println("Логин пользователя: " + login);
            String query = "select * from users where Login=?";
            ps = con.prepareStatement(query);
            ps.setString(1, login);
            rs = ps.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("IDUs"));
                user.setName(rs.getString("Name"));
                user.setSurname(rs.getString("Surname"));
                user.setEMail(rs.getString("EMail"));
                user.setLogin(rs.getString("Login"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }
        if(user.getId() == 0) return null;
        else return user;
    }


    public List<User> fetchAll() {
        List<User> userList = new ArrayList<User>();
        con = DBConnection.connection;
        try {
            String query = "select * from users order by IDUs";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("IDUs"));
                user.setName(rs.getString("Name"));
                user.setSurname(rs.getString("Surname"));
                user.setEMail(rs.getString("EMail"));
                user.setLogin(rs.getString("Login"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
