

import DAO.UserDAO;
import Entitites.*;
import enum_choosing_service.ServiceType;
import services.*;
import Responses.Request;
import Responses.Response;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientThread implements Runnable {
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ObjectInputStream inputStream;
    private Socket socket;

    private Gson gson = new Gson();
    private UserService userService = new UserService();
    private ProductService productService = new ProductService();
    private FirmService firmService = new FirmService();
    private SellerService sellerService = new SellerService();
    private ShopClientService shopClientService = new ShopClientService();
    private CardService cardService = new CardService();
    private ShopCartService shopCartService = new ShopCartService();
    private EntityServiceSelector serviceSelector = new EntityServiceSelector();
    private Request request;
    private String clientData;

    public ClientThread(Socket socket){
        this.socket = socket;
        this.bufferedReader = getReader();
        this.bufferedWriter = getWriter();
        this.inputStream = getObjectStream();
    }

    @Override
    public void run() {
        while(socket.isConnected() && !socket.isClosed()){
            try {
                System.out.println("Считываем данные");
                clientData = bufferedReader.readLine();
                System.out.println("Считали данные");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            request = (Request)gson.fromJson(clientData, Request.class);
            switch(request.getRequestType()){
                case AUTH:{
                    System.out.println("Клиент авторизуется!");
                    User user = (User) gson.fromJson(request.getClientData(), User.class);
                    Response response;
                    response = userService.authorization(user);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case REG:
                case ADD_USER: {
                    System.out.println("Добавление нового юзера");
                    EntityService entityService = serviceSelector.getService(ServiceType.USER);
                    User user = (User) gson.fromJson(request.getClientData(), User.class);
                    Response response;
                    response = entityService.addingEntity(user);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SHOW_USERS:{
                    System.out.println("Показываем всех юзеров");
                    EntityService entityService = serviceSelector.getService(ServiceType.USER);
                    Response response;
                    response = entityService.showAll();
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case DELETE_USER:{
                    System.out.println("Удаляем юзера");
                    EntityService entityService = serviceSelector.getService(ServiceType.USER);
                    Response response;
                    try {
                        response = entityService.deleteEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SEARCH_USER:{
                    System.out.println("Ищем юзера");
                    EntityService entityService = serviceSelector.getService(ServiceType.USER);
                    Response response;
                    try {
                        response = entityService.findEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(new Gson().toJson(response));
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case EDIT_USER:{
                    System.out.println("Изменяем юзера");
                    User user = (User) gson.fromJson(request.getClientData(), User.class);
                    Response response;
                    try {
                        response = userService.updatingUser(user);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_PRODUCT:{
                    System.out.println("Добавление нового товара");
                    EntityService entityService = serviceSelector.getService(ServiceType.PRODUCT);
                    Product product = (Product) gson.fromJson(request.getClientData(), Product.class);
                    Response response;
                    response = entityService.addingEntity(product);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case EDIT_PRODUCT:{
                    System.out.println("Изменяем товар");
                    Product product = (Product) gson.fromJson(request.getClientData(), Product.class);
                    Response response;
                    response = productService.updatingProduct(product);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case DELETE_PRODUCT:{
                    System.out.println("Удаляем товар");
                    EntityService entityService = serviceSelector.getService(ServiceType.PRODUCT);
                    Response response;
                    try {
                        response = entityService.deleteEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SEARCH_PRODUCT:{
                    EntityService entityService = serviceSelector.getService(ServiceType.PRODUCT);
                    System.out.println("Ищем товар");
                    Response response;
                    try {
                        response = entityService.findEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SEARCH_PRODUCT_NAME:{
                    System.out.println("Ищем товар по имени");
                    Response response;
                    response = productService.searchProductByName(request.getClientData());
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SHOW_PRODUCTS:{
                    System.out.println("Показываем все товары");
                    EntityService entityService = serviceSelector.getService(ServiceType.PRODUCT);
                    Response response;
                    response = entityService.showAll();
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_FIRM_PRODUCT:{
                    System.out.println("Добавляем фирму для продукта");
                    Product product = (Product) gson.fromJson(request.getClientData(), Product.class);
                    Response response;
                    response = productService.setFirmToProduct(product);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SHOW_FIRMS:{
                    System.out.println("Показываем все фирмы");
                    EntityService entityService = serviceSelector.getService(ServiceType.FIRM);
                    Response response;
                    response = entityService.showAll();
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_FIRM:{
                    System.out.println("Добавляем новую фирму");
                    EntityService entityService = serviceSelector.getService(ServiceType.FIRM);
                    Firm firm = (Firm) gson.fromJson(request.getClientData(), Firm.class);
                    Response response;
                    response = entityService.addingEntity(firm);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case EDIT_FIRM:{
                    System.out.println("Изменяем фирму");
                    Firm firm = (Firm) gson.fromJson(request.getClientData(), Firm.class);
                    Response response;
                    response = firmService.editingFirm(firm);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case DELETE_FIRM:{
                    System.out.println("Удаляем фирму");
                    EntityService entityService = serviceSelector.getService(ServiceType.FIRM);
                    Response response;
                    try {
                        response = entityService.deleteEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SEARCH_FIRM:{
                    System.out.println("Ищем фирму");
                    EntityService entityService = serviceSelector.getService(ServiceType.FIRM);
                    Response response;
                    try {
                        response = entityService.findEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_SELLER:{
                    System.out.println("Добавляем продавца");
                    EntityService entityService = serviceSelector.getService(ServiceType.SELLER);
                    Seller seller = (Seller) gson.fromJson(request.getClientData(), Seller.class);
                    Response response;
                    response = entityService.addingEntity(seller);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SEARCH_SELLERS:{
                    System.out.println("Ищем продавца");
                    EntityService entityService = serviceSelector.getService(ServiceType.SELLER);
                    Response response;
                    try {
                        response = entityService.findEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SHOW_SELLERS:{
                    System.out.println("Показываем продавцов");
                    EntityService entityService = serviceSelector.getService(ServiceType.SELLER);
                    Response response;
                    response = entityService.showAll();
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case EDIT_SELLERS:{
                    System.out.println("Изменяем продавца");
                    Seller seller = (Seller) gson.fromJson(request.getClientData(), Seller.class);
                    Response response;
                    response = sellerService.editingSeller(seller);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case DELETE_SELLER:{
                    System.out.println("Удаляем продавца");
                    EntityService entityService = serviceSelector.getService(ServiceType.SELLER);
                    Response response;
                    try {
                        response = entityService.deleteEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case CALCULATE_SALARY_SELLER:{
                    System.out.println("Считаем зп продавца");
                    Seller seller = (Seller) gson.fromJson(request.getClientData(), Seller.class);
                    Response response;
                    response = sellerService.calculateSellerSalary(seller);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_CLIENT:{
                    System.out.println("Добавляем клиента");
                    EntityService entityService = serviceSelector.getService(ServiceType.SHOP_CLIENT);
                    ShopClient client = (ShopClient) gson.fromJson(request.getClientData(), ShopClient.class);
                    Response response;
                    response = entityService.addingEntity(client);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_CLIENT_REGISTRATION:{
                    System.out.println("Добавляем клиента");
                    EntityService entityService = serviceSelector.getService(ServiceType.SHOP_CLIENT);
                    ShopClient client = new ShopClient();
                    client.setId_user(Integer.parseInt(request.getClientData()));
                    Response response;
                    response = entityService.addingEntity(client);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case DELETE_CLIENT:{
                    System.out.println("удаляем клиента");
                    EntityService entityService = serviceSelector.getService(ServiceType.SHOP_CLIENT);
                    Response response;
                    try {
                        response = entityService.deleteEntity(request.getClientData());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
//                case SEARCH_CARD_INSERT:{
//                    System.out.println("Ищем карту");
//                    Response response;
//                    response = cardService.findCard((Card)new Gson().fromJson(request.getClientData(),Card.class));
//                    sendingResponseToClient(gson.toJson(response));
//                    break;
//                }
                case SEARCH_CARD:{
                    System.out.println("Ищем карту клиента");
                    Response response;
                    response = cardService.findExistCard((Card)new Gson().fromJson(request.getClientData(),Card.class));
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_CARD:{
                    System.out.println("Добавляем карту клиента");
                    EntityService entityService = serviceSelector.getService(ServiceType.CARD);
                    Card card = (Card) gson.fromJson(request.getClientData(), Card.class);
                    Response response;
                    response = entityService.addingEntity(card);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_BALANCE_CARD:{
                    System.out.println("Пополняем баланс карты");
                    Card card = (Card) gson.fromJson(request.getClientData(), Card.class);
                    Response response;
                    response = cardService.addingBalance(card);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case ADD_ORDER:{
                    System.out.println("Добавляем товар в корзину");
                    EntityService entityService = serviceSelector.getService(ServiceType.SHOP_CART);
                    ShopCart shopCart = (ShopCart) gson.fromJson(request.getClientData(), ShopCart.class);
                    Response response;
                    response = entityService.addingEntity(shopCart);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SHOW_ORDERS_CLIENT:{
                    System.out.println("Показываем корзину клиенту");
                    Response response;
                    response = shopCartService.showingOrdersToClient(request.getClientData());
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case PAY_FOR_ORDER:{
                    System.out.println("Платим за товар в корзине");
                    Response response;
                    response = shopCartService.payForOrder(gson.fromJson(request.getClientData(),ShopCart.class));
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case REMOVE_PRODUCT_FROM_CART:{
                    System.out.println("Удаляем товар из корзины");
                    ShopCart shopCart = (ShopCart) gson.fromJson(request.getClientData(), ShopCart.class);
                    Response response;
                    response = shopCartService.removeFromCart(shopCart);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SHOW_CLIENTS:{
                    System.out.println("Показываем всех клиентов");
                    EntityService entityService = serviceSelector.getService(ServiceType.SHOP_CLIENT);
                    Response response;
                    response = entityService.showAll();
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SHOW_ORDERS:{
                    System.out.println("Показываем все заказы");
                    EntityService entityService = serviceSelector.getService(ServiceType.SHOP_CART);
                    Response response;
                    response = entityService.showAll();
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case DENY_ORDER:
                case ACCEPT_ORDER: {
                    System.out.println("Принимаем/отклоняем заказ");
                    ShopCart shopCart = (ShopCart) gson.fromJson(request.getClientData(), ShopCart.class);
                    Response response;
                    response = shopCartService.setNewOrderStatus(shopCart);
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case SET_AMOUNT_SELLER:{
                    System.out.println("Добавляем продажи продавцу");
                    Response response;
                    response = sellerService.set_amount_seller(request.getClientData());
                    sendingResponseToClient(gson.toJson(response));
                    break;
                }
                case STOPPED:
                    System.out.println("Закрываем всё!");
                    try {
                        closeEverything();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("Unknown request");
            }
        }
    }

    private void sendingResponseToClient(String response){
        try {
            bufferedWriter.write(response);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader getReader(){
        try {
            return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  BufferedWriter getWriter(){
        try {
            return new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectInputStream getObjectStream(){
        try {
            return new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeEverything() throws IOException {
        if(bufferedWriter != null) bufferedWriter.close();
        if(bufferedReader != null) bufferedReader.close();
        if(socket != null) socket.close();
    }
}
