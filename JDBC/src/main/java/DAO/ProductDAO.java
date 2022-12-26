package DAO;

import DBCreating.DBConnection;
import Entitites.Product;
import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
        ConnectionImpl con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int st;//status

        public int insertProduct(Product product) {
            con = DBConnection.connection;
            try {
                String query = "insert into product(pname,pamount,price) "
                        + "values(?,?,?)";
                ps = con.prepareStatement(query);
                ps.setString(1, product.getPname());
                ps.setInt(2, product.getPamount());
                ps.setFloat(3, product.getPrice());
                st = ps.executeUpdate();
                System.out.println("inserted products " + st);
            } catch (SQLIntegrityConstraintViolationException e) {
                st = -1;
                e.printStackTrace();
            } catch (Exception e) {
                st = -2;
                e.printStackTrace();
            }
            return st;
        }

    public int insertProductFirm(Product product) {
        con = DBConnection.connection;
        try {
            String test = "select * from firm where id_firm=?";
            ps = con.prepareStatement(test);
            ps.setInt(1, product.getFirm_id());
            rs = ps.executeQuery();
            if (rs.next() == false) return -1;
            String query = "select id_firm from firm where id_firm=?";
            ps = con.prepareStatement(query);
            ps.setInt(1, product.getFirm_id());
            rs = ps.executeQuery();
            rs.next();
            product.setFirm_id(rs.getInt("id_firm"));
            String query1 = "update product set id_firmFK=? where id_product=? ";
            ps = con.prepareStatement(query1);
            ps.setInt(1, product.getFirm_id());
            ps.setInt(2,product.getId_product());
            st = ps.executeUpdate();
            System.out.println("inserted firm in product " + st);
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public int insertProductAmountSells(Product product) {
        con = DBConnection.connection;
        try {
            String query = "insert into product(pname,pamount,price) "
                    + "values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, product.getPname());
            ps.setInt(2, product.getPamount());
            ps.setFloat(3, product.getPrice());
            st = ps.executeUpdate();
            System.out.println("inserted products " + st);
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

    public int insertProductRate(Product product) {
        con = DBConnection.connection;
        try {
            String query = "insert into product(pname,pamount,price) "
                    + "values(?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, product.getPname());
            ps.setInt(2, product.getPamount());
            ps.setFloat(3, product.getPrice());
            st = ps.executeUpdate();
            System.out.println("inserted products " + st);
        } catch (SQLIntegrityConstraintViolationException e) {
            st = -1;
            e.printStackTrace();
        } catch (Exception e) {
            st = -2;
            e.printStackTrace();
        }
        return st;
    }

        public int update(Product product) {
            con = DBConnection.connection;
            try {
                String query = "update product set pname=?,pamount=?,price=? where id_product=?";
                ps = con.prepareStatement(query);
                ps.setString(1, product.getPname());
                ps.setInt(2, product.getPamount());
                ps.setFloat(3, product.getPrice());
                ps.setInt(4,product.getId_product());
                st = ps.executeUpdate();
                System.out.println("updated product " + st);
            } catch (Exception e) {
                st = -2;
                e.printStackTrace();
            }
            return st;
        }
        public int delete(int id) {
            con = DBConnection.connection;
            try {
                String query = "delete from product where id_product=? ";
                ps = con.prepareStatement(query);
                ps.setInt(1, id);
                st = ps.executeUpdate();
                System.out.println("deleted products " + st);
            } catch (Exception e) {
                st = -2;
                e.printStackTrace();
            }
            return st;
        }
//
        public Product fetchById(int id) {
            Product product = new Product();
            con = DBConnection.connection;
            try {
                String query = "select * from product left join firm on product.id_firmFK=firm.id_firm where id_product=?";
                ps = con.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    product.setId_product(rs.getInt("id_product"));
                    product.setPname(rs.getString("pname"));
                    product.setPamount(rs.getInt("pamount"));
                    product.setPrice(rs.getFloat("price"));
                    product.setFirm_id(rs.getInt("id_firm"));
                    if(product.getFirm_id() == 0){
                        product.setFirmName("NULL");
                    } else {
                        product.setFirmName(rs.getString("fname"));
                    }
                    product.setRate(rs.getFloat("rate"));
                    product.setAmount_sells(rs.getInt("amount_sells"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return product;
        }

    public Product fetchByName(String name) {
        Product product = new Product();
        con = DBConnection.connection;
        try {
            String query = "select * from product left join firm on product.id_firmFK=firm.id_firm where pname=?";
            ps = con.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                product.setId_product(rs.getInt("id_product"));
                product.setPname(rs.getString("pname"));
                product.setPamount(rs.getInt("pamount"));
                product.setPrice(rs.getFloat("price"));
                product.setFirm_id(rs.getInt("id_firm"));
                if(product.getFirm_id() == 0){
                    product.setFirmName("NULL");
                } else {
                    product.setFirmName(rs.getString("fname"));
                }
                product.setRate(rs.getFloat("rate"));
                product.setAmount_sells(rs.getInt("amount_sells"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
//
//        public User fetchByEmail(String email) {
//            User user = new User();
//            con = DBConnection.connection;
//            try {
//                String query = "select * from users where EMail=?";
//                ps = con.prepareStatement(query);
//                ps.setString(1, email);
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    user.setId(rs.getInt("IDUs"));
//                    user.setName(rs.getString("Name"));
//                    user.setSurname(rs.getString("Surname"));
//                    user.setEMail(rs.getString("EMail"));
//                    user.setLogin(rs.getString("Login"));
//                    user.setPassword(rs.getString("Password"));
//                    user.setRole(rs.getString("Role"));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return user;
//        }
//
//        public List<User> fetchBySurname(String surname) {
//            List<User> userList = new ArrayList<User>();
//            con = DBConnection.connection;
//            try {
//                String query = "select * from users where Surname LIKE ?";
//                ps = con.prepareStatement(query);
//                ps.setString(1, surname + "%");
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    User user = new User();
//                    user.setId(rs.getInt("IDUs"));
//                    user.setName(rs.getString("Name"));
//                    user.setSurname(rs.getString("Surname"));
//                    user.setEMail(rs.getString("EMail"));
//                    user.setLogin(rs.getString("Login"));
//                    user.setPassword(rs.getString("Password"));
//                    user.setRole(rs.getString("Role"));
//                    userList.add(user);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return userList;
//        }
//
//        public List<User> searchByName(String name){
//            List<User> userList = new ArrayList<User>();
//            con = DBConnection.connection;
//            try {
//                String query = "select * from users where Name LIKE ?";
//                ps = con.prepareStatement(query);
//                ps.setString(1, name + "%");
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    User user = new User();
//                    user.setId(rs.getInt("IDUs"));
//                    user.setName(rs.getString("Name"));
//                    user.setSurname(rs.getString("Surname"));
//                    user.setEMail(rs.getString("EMail"));
//                    user.setLogin(rs.getString("Login"));
//                    user.setPassword(rs.getString("Password"));
//                    user.setRole(rs.getString("Role"));
//                    userList.add(user);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return userList;
//        }
//
//        public User fetchByLogin(User client) {
//            User user = new User();
//            con = DBConnection.connection;
//            try {
//                String query = "select * from users where Login=?";
//                ps = con.prepareStatement(query);
//                System.out.println(new Gson().toJson(client));
//                ps.setString(1, client.getLogin());
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    System.out.println("Hello!");
//                    user.setId(rs.getInt("IDUs"));
//                    user.setName(rs.getString("Name"));
//                    user.setSurname(rs.getString("Surname"));
//                    user.setEMail(rs.getString("EMail"));
//                    user.setLogin(rs.getString("Login"));
//                    user.setPassword(rs.getString("Password"));
//                    user.setRole(rs.getString("Role"));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return user;
//        }
//
//        public User fetchByLogin(String login) {
//            User user = new User();
//            con = DBConnection.connection;
//            try {
//                String query = "select * from users where Login=?";
//                ps = con.prepareStatement(query);
//                ps.setString(1, login);
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    user.setId(rs.getInt("IDUs"));
//                    user.setName(rs.getString("Name"));
//                    user.setSurname(rs.getString("Surname"));
//                    user.setEMail(rs.getString("EMail"));
//                    user.setLogin(rs.getString("Login"));
//                    user.setPassword(rs.getString("Password"));
//                    user.setRole(rs.getString("Role"));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if(user.getId() == 0) return null;
//            else return user;
//        }
//
//        public List<User> fetchByRole(String role) {
//            List<User> userList = new ArrayList<User>();
//            con = DBConnection.connection;
//            try {
//                String query = "select * from users where  Role=?";
//                ps = con.prepareStatement(query);
//                ps.setString(1, role);
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    User user = new User();
//                    user.setId(rs.getInt("IDUs"));
//                    user.setName(rs.getString("Name"));
//                    user.setSurname(rs.getString("Surname"));
//                    user.setEMail(rs.getString("EMail"));
//                    user.setLogin(rs.getString("Login"));
//                    user.setPassword(rs.getString("Password"));
//                    user.setRole(rs.getString("Role"));
//                    userList.add(user);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return userList;
//        }
//
        public synchronized List<Product> fetchAll() {
            List<Product> productList = new ArrayList<Product>();
            con = DBConnection.connection;
            try {
                String query = "select * from product left join firm on product.id_firmFK=firm.id_firm";
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next())
                {
                    Product product = new Product();
                    product.setId_product(rs.getInt("id_product"));
                    product.setPname(rs.getString("pname"));
                    product.setPamount(rs.getInt("pamount"));
                    product.setPrice(rs.getFloat("price"));
                    product.setFirm_id(rs.getInt("id_firm"));
                    if(product.getFirm_id() == 0){
                        product.setFirmName("NULL");
                    } else {
                        product.setFirmName(rs.getString("fname"));
                    }
                    product.setRate(rs.getFloat("rate"));
                    product.setAmount_sells(rs.getInt("amount_sells"));
                    System.out.println(new Gson().toJson(product));
                    productList.add(product);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return productList;
        }
}

