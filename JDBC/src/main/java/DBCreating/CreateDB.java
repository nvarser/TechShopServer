package DBCreating;

import com.mysql.cj.jdbc.ConnectionImpl;

import java.sql.*;

public class CreateDB {
    private ConnectionImpl connection = null;
    private PreparedStatement statement = null;
    private ResultSet rs;
    public CreateDB () {
        try {
            DBConnection.connect();
            this.connection = DBConnection.connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addTableUsers();
        addTableSellers();
        addTableCard();
        addTableFirm();
        addTableProduct();
        addTableClients();
        addTableShopCart();
    }

    private int addTableUsers(){
        try {
                String SQL = "CREATE TABLE IF NOT EXISTS `courseworkdb`.`users` (\n" +
                        "  `IDUs` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `Name` VARCHAR(45) NOT NULL,\n" +
                        "  `Surname` VARCHAR(45) NOT NULL,\n" +
                        "  `EMail` VARCHAR(64) NOT NULL,\n" +
                        "  `Login` VARCHAR(45) NOT NULL,\n" +
                        "  `Password` VARCHAR(45) NOT NULL,\n" +
                        "  `Role` VARCHAR(45) NOT NULL,\n" +
                        "  PRIMARY KEY (`IDUs`))\n";
                statement = connection.prepareStatement(SQL);
                statement.execute(SQL);
                String test = "select * from users where Login=? or EMail=?";
                statement = connection.prepareStatement(test);
                statement.setString(1, "login");
                statement.setString(2, "fixgurumaster@gmail.com");
                rs = statement.executeQuery();
                if (rs.next()) {
                    System.out.println("Hello!");
                    return -1;
                }
                else {
                    String query = "INSERT INTO users(Name,Surname,EMAil,Login,Password,Role) "
                            + "values(\'Artyom\',\'Novikov\',\'fixgurumaster@gmail.com\',\'login\',\'password\',\'A\')";
                    statement = connection.prepareStatement(query);
                    statement.executeUpdate();
                    return 0;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return 3;
    }

    private void addTableSellers(){
        try {
            String SQL = "CREATE TABLE IF NOT EXISTS `courseworkdb`.`seller` (\n" +
                    "  `id_seller` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `hours` FLOAT NULL DEFAULT NULL,\n" +
                    "  `hour_rate` FLOAT NULL DEFAULT NULL,\n" +
                    "  `amount_sells` INT NULL DEFAULT NULL,\n" +
                    "  `rate_per_sell` FLOAT NULL DEFAULT NULL,\n" +
                    "  `id_user` INT NOT NULL,\n" +
                    "  PRIMARY KEY (`id_seller`),\n" +
                    "  INDEX `id_user_idx` (`id_user` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `id_user`\n" +
                    "    FOREIGN KEY (`id_user`)\n" +
                    "    REFERENCES `courseworkdb`.`users` (`IDUs`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE)\n";
            statement = connection.prepareStatement(SQL);
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTableCard(){
        try {
            String SQL = "CREATE TABLE IF NOT EXISTS `courseworkdb`.`card` (\n" +
                    "  `id_card` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `serial_number` VARCHAR(16) NOT NULL,\n" +
                    "  `date_card` VARCHAR(5) NOT NULL,\n" +
                    "  `cvv_card` VARCHAR(3) NOT NULL,\n" +
                    "  `money` FLOAT NULL DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id_card`))\n";
            statement = connection.prepareStatement(SQL);
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTableFirm(){
        try {
            String SQL = "CREATE TABLE IF NOT EXISTS `courseworkdb`.`firm` (\n" +
                    "  `id_firm` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `fname` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id_firm`))\n";
            statement = connection.prepareStatement(SQL);
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTableProduct(){
        try {
            String SQL = "CREATE TABLE IF NOT EXISTS `courseworkdb`.`product` (\n" +
                    "  `id_product` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `pname` VARCHAR(45) NOT NULL,\n" +
                    "  `pamount` INT NOT NULL,\n" +
                    "  `id_firmFK` INT NULL DEFAULT NULL,\n" +
                    "  `price` FLOAT NOT NULL,\n" +
                    "  `rate` FLOAT NULL DEFAULT NULL,\n" +
                    "  `amount_sells` INT NULL DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id_product`),\n" +
                    "  INDEX `id_firm_idx` (`id_firmFK` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `id_firm`\n" +
                    "    FOREIGN KEY (`id_firmFK`)\n" +
                    "    REFERENCES `courseworkdb`.`firm` (`id_firm`)\n" +
                    "    ON DELETE SET NULL\n" +
                    "    ON UPDATE SET NULL)\n";
            statement = connection.prepareStatement(SQL);
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTableClients(){
        try {
                String SQL = "CREATE TABLE IF NOT EXISTS `courseworkdb`.`shop_client` (\n" +
                        "  `id_shop_client` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `id_cardFK` INT NULL DEFAULT NULL,\n" +
                        "  `id_userFK` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`id_shop_client`),\n" +
                        "  INDEX `id_clientFKcard_idx` (`id_cardFK` ASC) VISIBLE,\n" +
                        "  INDEX `id_clientFKuser_idx` (`id_userFK` ASC) VISIBLE,\n" +
                        "  CONSTRAINT `id_clientFKcard`\n" +
                        "    FOREIGN KEY (`id_cardFK`)\n" +
                        "    REFERENCES `courseworkdb`.`card` (`id_card`)\n" +
                        "    ON DELETE SET NULL,\n" +
                        "  CONSTRAINT `id_clientFKuser`\n" +
                        "    FOREIGN KEY (`id_userFK`)\n" +
                        "    REFERENCES `courseworkdb`.`users` (`IDUs`)\n" +
                        "    ON DELETE CASCADE\n" +
                        "    ON UPDATE CASCADE)\n";
                statement = connection.prepareStatement(SQL);
                statement.executeUpdate(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private void addTableShopCart(){
        try {
            String SQL = "CREATE TABLE IF NOT EXISTS `courseworkdb`.`shop_cart` (\n" +
                    "  `id_cart` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `id_clientFK` INT NULL DEFAULT NULL,\n" +
                    "  `id_productFK` INT NULL DEFAULT NULL,\n" +
                    "  `amount` INT NULL DEFAULT NULL,\n" +
                    "  `status` VARCHAR(20) NULL DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id_cart`),\n" +
                    "  INDEX `id_cartFKclient_idx` (`id_clientFK` ASC) VISIBLE,\n" +
                    "  INDEX `id_cartFKproduct_idx` (`id_productFK` ASC) VISIBLE,\n" +
                    "  CONSTRAINT `id_cartFKclient`\n" +
                    "    FOREIGN KEY (`id_clientFK`)\n" +
                    "    REFERENCES `courseworkdb`.`shop_client` (`id_shop_client`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE,\n" +
                    "  CONSTRAINT `id_cartFKproduct`\n" +
                    "    FOREIGN KEY (`id_productFK`)\n" +
                    "    REFERENCES `courseworkdb`.`product` (`id_product`)\n" +
                    "    ON DELETE CASCADE\n" +
                    "    ON UPDATE CASCADE)\n";
            statement = connection.prepareStatement(SQL);
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoUsers(String name, String surname, String email, String login, String password, String role) throws SQLException {
        String query = "INSERT INTO users(Name,Surname,EMAil,Login,Password,Role) "
                + "values(?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, surname);
            pstmt.setString(3, email);
            pstmt.setString(4, login);
            pstmt.setString(5, password);
            pstmt.setString(6, role);
            pstmt.executeUpdate();
    }

    public void clearTableUsers() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("TRUNCATE TABLE users");
    }
}