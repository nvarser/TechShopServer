package DAO;

import DBCreating.DBConnection;
import Entitites.Card;
import Entitites.Product;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class CardDAO {
    ConnectionImpl con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status
    public Card fetchCard(Card card) {
        Card card1 = new Card();
        con = DBConnection.connection;
        try {
            String query = "select * from card where serial_number=? and date_card=?";
            ps = con.prepareStatement(query);
            ps.setString(1, card.getSerialNumber());
            ps.setString(2, card.getDate());
            rs = ps.executeQuery();
            if (rs.next()) {
               card1.setId(rs.getInt("id_card"));
               card1.setCvv(rs.getString("cvv_card"));
               card1.setSerialNumber(rs.getString("serial_number"));
               card1.setDate(rs.getString("date_card"));
               card1.setSum(rs.getFloat("money"));
            } else{
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return card1;
    }

    public Card fetchExistCard(Card card) {
        Card card1 = new Card();
        con = DBConnection.connection;
        try {
            String query = "select * from users left join shop_client on IDUS=id_userFK where " +
                    "shop_client.id_cardFK is not null and id_userFK=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, card.getId_client());
            rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("Мы нашли клиента!");
                System.out.println("Его id" + card.getId_client());
                card1.setId_client(rs.getInt("id_shop_client"));
                card1.setId(rs.getInt("id_cardFK"));
                String query1 = "select * from card where ?=id_card";
                ps = con.prepareStatement(query1);
                ps.setInt(1, card1.getId());
                rs = ps.executeQuery();
                rs.next();
                card1.setCvv(rs.getString("cvv_card"));
                card1.setDate(rs.getString("date_card"));
                card1.setSerialNumber(rs.getString("serial_number"));
                card1.setSum(rs.getFloat("money"));
                System.out.println(new Gson().toJson(card1));
            } else{
                System.out.println("Мы не нашли карту!");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card1;
    }

    public Card insertCard(Card card) {
        con = DBConnection.connection;
        Card card1 = new Card();
        try {
            System.out.println(new Gson().toJson(card));
            String query = "insert into card(serial_number,date_card,cvv_card) "
                    + "values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, card.getSerialNumber());
            ps.setString(2, card.getDate());
            ps.setString(3, card.getCvv());
            st = ps.executeUpdate();
            card1 = fetchCard(card);
            System.out.println("inserted cards " + st);
            String query1 = "select * from users left join shop_client on IDUs=id_userFK where id_userFK=?";
            ps = con.prepareStatement(query1);
            System.out.println("Айди юзера: " + card.getId_client());
            ps.setInt(1, card.getId_client());
            rs = ps.executeQuery();
            if(rs.next()) System.out.println("Мы нашли клиента!");
            card1.setId_client(rs.getInt("id_shop_client"));
            String clientInsert = "update shop_client set id_cardFK=? where id_shop_client=?";
            ps = con.prepareStatement(clientInsert);
            ps.setInt(1,card1.getId());
            ps.setInt(2,card1.getId_client());
            st = ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return card1;
    }
    public float addBalance(Card card){
        Card card1;
        card1 = fetchCard(card);
        try {
            float totalSum = card1.getSum() + card.getAddingBalance();
            card1.setSum(totalSum);
            String clientInsert = "update card set money=? where id_card=?";
            ps = con.prepareStatement(clientInsert);
            ps.setFloat(1,card1.getSum());
            ps.setInt(2,card1.getId());
            st = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return card1.getSum();
    }
}
