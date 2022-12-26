package DAO;

import DBCreating.DBConnection;
import Entitites.Card;
import Entitites.Product;
import Entitites.Seller;
import Entitites.ShopCart;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ShopCartDAO {
    ConnectionImpl con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public List<ShopCart> fetchAll() {
        List<ShopCart> shopCarts = new ArrayList<ShopCart>();
        con = DBConnection.connection;
        try {
            String query = "select * from shop_cart where status=\'WaitAccept\'";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                ShopCart shopCart = new ShopCart();
                shopCart.setId_client(rs.getInt("id_clientFK"));
                shopCart.setAmount(rs.getInt("amount"));
                shopCart.setId_product(rs.getInt("id_productFK"));
                shopCart.setStatus(rs.getString("status"));
                shopCart.setId(rs.getInt("id_cart"));
                shopCarts.add(shopCart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shopCarts;
    }

    public int insertOrder(ShopCart shopCart) {
        con = DBConnection.connection;
        try {
            String test = "select * from product where id_product=? and pamount>=?";
            ps = con.prepareStatement(test);
            ps.setInt(1, shopCart.getId_product());
            ps.setInt(2, shopCart.getAmount());
            System.out.println("Твой заказ!" + new Gson().toJson(shopCart));
            rs = ps.executeQuery();
            if(rs.next()){
                String query = "insert into shop_cart(id_clientFK,id_productFK,amount,status) "
                        + "values(?,?,?,?)";
                ps = con.prepareStatement(query);
                ps.setInt(1, shopCart.getId_client());
                ps.setInt(2, shopCart.getId_product());
                ps.setInt(3, shopCart.getAmount());
                ps.setString(4, shopCart.getStatus());
                st = ps.executeUpdate();
                String minusAmount = "select * from product where id_product=?";
                ps = con.prepareStatement(minusAmount);
                ps.setInt(1, shopCart.getId_product());
                rs = ps.executeQuery();
                rs.next();
                int amount = rs.getInt("pamount");
                amount -= shopCart.getAmount();
                String productAmount = "update product set pamount=? where id_product=?";
                ps = con.prepareStatement(productAmount);
                ps.setInt(1, amount);
                ps.setInt(2, shopCart.getId_product());
                st = ps.executeUpdate();
            } else{
                return -1;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public synchronized List<ShopCart> fetchOrdersToClient(int id) {
        List<ShopCart> shopCarts = new ArrayList<ShopCart>();
        con = DBConnection.connection;
        try {
            String query = "select * from shop_cart where id_clientFK=?";
            ps = con.prepareStatement(query);
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while (rs.next())
            {
                ShopCart shopCart = new ShopCart();
                shopCart.setAmount(rs.getInt("amount"));
                shopCart.setId_product(rs.getInt("id_productFK"));
                shopCart.setStatus(rs.getString("status"));
                shopCart.setId(rs.getInt("id_cart"));
                shopCarts.add(shopCart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shopCarts;
    }

    public float payForOrder(ShopCart shopCart){
        con = DBConnection.connection;
        try {
            String test = "select * from product where id_product=?";
            ps = con.prepareStatement(test);
            ps.setInt(1, shopCart.getId_product());
            rs = ps.executeQuery();
            if(rs.next()){
                float price = rs.getFloat("price");
                float total = price * shopCart.getAmount();
                System.out.println(new Gson().toJson(shopCart));
                String query = "select * from shop_client left join card on shop_client.id_cardFK=id_card where id_shop_client=?";
                ps = con.prepareStatement(query);
                ps.setInt(1,shopCart.getId_client());
                rs = ps.executeQuery();
                if(!rs.next()){
                    return -2;
                } else{
                    int id = rs.getInt("id_card");
                    float sum = rs.getFloat("money");
                    float sumka = sum - total;
                    if(sumka < 0){
                        return -5;
                    }
                    String query1 = "update card set money=? where id_card=?";
                    ps = con.prepareStatement(query1);
                    ps.setFloat(1,sumka);
                    ps.setInt(2,id);
                    st = ps.executeUpdate();
                    String query2 = "update shop_cart set status=? where id_cart=?";
                    ps = con.prepareStatement(query2);
                    ps.setString(1,"WaitAccept");
                    ps.setInt(2,shopCart.getId());
                    st = ps.executeUpdate();
                }
            } else{
                return -1;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public int removeProductFromCart(ShopCart shopCart){
        try {
            String test = "select * from product where id_product=?";
            ps = con.prepareStatement(test);
            ps.setInt(1, shopCart.getId_product());
            rs = ps.executeQuery();
            if(rs.next()){
                int amount = rs.getInt("pamount");
                int total = amount + shopCart.getAmount();
                String query = "update product set pamount=? where id_product=?";
                ps = con.prepareStatement(query);
                ps.setInt(1, total);
                ps.setInt(2, shopCart.getId_product());
                st = ps.executeUpdate();
                String query1 = "delete from shop_cart where id_cart=?";
                ps = con.prepareStatement(query1);
                ps.setInt(1, shopCart.getId());
                st = ps.executeUpdate();
                System.out.println("updated product " + st);
            } else{
                return -1;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public int setNewOrderStatus(ShopCart shopCart){
        try {
            String test = "update shop_cart set status=? where id_cart=?";
            ps = con.prepareStatement(test);
            ps.setString(1, shopCart.getStatus());
            ps.setInt(2, shopCart.getId());
            st = ps.executeUpdate();
            System.out.println("updated shop_cart's status " + st);
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }
}
