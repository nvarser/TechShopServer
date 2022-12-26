package DAO;

import DBCreating.DBConnection;
import Entitites.Seller;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {
    ConnectionImpl con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status
    public List<Seller> fetchAll() {
        List<Seller> sellerList = new ArrayList<Seller>();
        con = DBConnection.connection;
        try {
            String query = "select * from seller left join users on seller.id_user=users.IDUs";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Seller seller = new Seller();
                seller.setId(rs.getInt("id_seller"));
                seller.setName(rs.getString("Name"));
                seller.setSurname(rs.getString("Surname"));
                seller.setEMail(rs.getString("EMail"));
                seller.setLogin(rs.getString("Login"));
                seller.setPassword(rs.getString("Password"));
                seller.setRole(rs.getString("Role"));
                seller.setHours(rs.getFloat("hours"));
                seller.setRatePerHour(rs.getFloat("hour_rate"));
                seller.setAmountSells(rs.getInt("amount_sells"));
                seller.setRatePerSell(rs.getFloat("rate_per_sell"));
                sellerList.add(seller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellerList;
    }
    public int update(Seller seller) {
        con = DBConnection.connection;
        try {
            String query = "update seller set hours=?,hour_rate=?,rate_per_sell=? where id_seller=?";
            ps = con.prepareStatement(query);
            ps.setFloat(1, seller.getHours());
            ps.setFloat(2, seller.getRatePerHour());
            ps.setFloat(3, seller.getRatePerSell());
            ps.setInt(4, seller.getId());
            st = ps.executeUpdate();
            System.out.println("updated seller " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }
    public Seller fetchById(int id) {
        Seller seller = null;
        con = DBConnection.connection;
        try {
            String query = "select * from seller left join users on seller.id_user=users.IDUs where id_seller=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                seller = new Seller();
                seller.setId(rs.getInt("id_seller"));
                seller.setName(rs.getString("Name"));
                seller.setSurname(rs.getString("Surname"));
                seller.setEMail(rs.getString("EMail"));
                seller.setLogin(rs.getString("Login"));
                seller.setPassword(rs.getString("Password"));
                seller.setRole(rs.getString("Role"));
                seller.setHours(rs.getFloat("hours"));
                seller.setRatePerHour(rs.getFloat("hour_rate"));
                seller.setAmountSells(rs.getInt("amount_sells"));
                seller.setRatePerSell(rs.getFloat("rate_per_sell"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seller;
    }

    public int insertSeller(Seller seller) {
        con = DBConnection.connection;
        try {
            String test = "select * from seller where id_user=?";
            ps = con.prepareStatement(test);
            ps.setInt(1, seller.getId_user());
            rs = ps.executeQuery();
            if (!rs.next() == false) return -1;
            String query = "insert into seller(id_user) "
                    + "values(?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, seller.getId_user());
            st = ps.executeUpdate();
            System.out.println("inserted sellers " + st);
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public int delete(int id) {
        con = DBConnection.connection;
        try {
            String query = "delete from seller where id_user=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted sellers " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public int editAmount(int id){
        con = DBConnection.connection;
        try {
            String query = "select amount_sells from seller where id_user=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            int amount = rs.getInt("amount_sells") + 1;
            String query1 = "update seller set amount_sells=? where id_user=? ";
            ps = con.prepareStatement(query1);
            ps.setInt(1, amount);
            ps.setInt(2, id);
            st = ps.executeUpdate();
            System.out.println("Изменено!");
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }
}
