package DAO;

import DBCreating.DBConnection;
import Entitites.Seller;
import Entitites.ShopClient;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ShopClientDAO {
    ConnectionImpl con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public List<ShopClient> fetchAll() {
        List<ShopClient> shopClients = new ArrayList<ShopClient>();
        con = DBConnection.connection;
        try {
            String query = "select * from shop_client left join users on shop_client.id_userFK=users.IDUs";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                ShopClient shopClient = new ShopClient();
                shopClient.setId(rs.getInt("id_shop_client"));
                shopClient.setName(rs.getString("Name"));
                shopClient.setSurname(rs.getString("Surname"));
                shopClients.add(shopClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shopClients;
    }
    public int insertClient(ShopClient client) {
        con = DBConnection.connection;
        try {
            String test = "select * from shop_client where id_userFK=?";
            ps = con.prepareStatement(test);
            ps.setInt(1, client.getId_user());
            rs = ps.executeQuery();
            if (!rs.next() == false) return -1;
            String query = "insert into shop_client(id_userFK) "
                    + "values(?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, client.getId_user());
            st = ps.executeUpdate();
            System.out.println("inserted clients " + st);
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
            String query = "delete from shop_client where id_userFK=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted clients " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }


}
