package DAO;

import DBCreating.DBConnection;
import Entitites.Firm;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class FirmDAO {
    ConnectionImpl con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;//status

    public int insertFirm(Firm firm) {
        con = DBConnection.connection;
        try {
            String test = "select * from firm where fname=?";
            ps = con.prepareStatement(test);
            ps.setString(1,firm.getFirm_name());
            rs = ps.executeQuery();
            if (!rs.next() == false) return -1;
            String query = "insert into firm(fname) "
                    + "values(?)";
            ps = con.prepareStatement(query);
            ps.setString(1, firm.getFirm_name());
            st = ps.executeUpdate();
            System.out.println("inserted firms " + st);
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }
    public int update(Firm firm) {
        con = DBConnection.connection;
        try {
            String query = "update firm set fname=? where id_firm=?";
            ps = con.prepareStatement(query);
            ps.setString(1, firm.getFirm_name());
            ps.setInt(2, firm.getId_firm());
            st = ps.executeUpdate();
            System.out.println("updated firm " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }
    public int delete(int id) {
        con = DBConnection.connection;
        try {
            String query = "delete from firm where id_firm=? ";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            st = ps.executeUpdate();
            System.out.println("deleted firm " + st);
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }
    public Firm fetchById(int id) {
        Firm firm = new Firm();
        con = DBConnection.connection;
        try {
            String query = "select * from firm where id_firm=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                firm.setFirm_name(rs.getString("fname"));
                firm.setId_firm(rs.getInt("id_firm"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firm;
    }
    public List<Firm> fetchAll() {
        List<Firm> firmList = new ArrayList<Firm>();
        con = DBConnection.connection;
        try {
            String query = "select * from firm order by id_firm";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Firm firm = new Firm();
                firm.setFirm_name(rs.getString("fname"));
                firm.setId_firm(rs.getInt("id_firm"));
                firmList.add(firm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firmList;
    }
}
