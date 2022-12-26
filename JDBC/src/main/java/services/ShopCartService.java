package services;

import DAO.CardDAO;
import DAO.ShopCartDAO;
import DAO.ShopClientDAO;
import Entitites.Card;
import Entitites.ShopCart;
import Responses.Response;
import Responses.Responses;
import com.google.gson.Gson;

public class ShopCartService implements EntityService<ShopCart>{
    private Response response;
    private ShopCartDAO shopCartDAO = new ShopCartDAO();

//    public Response addingOrderClient(ShopCart shopCart){
//        response = new Response();
//        if(shopCartDAO.insertOrder(shopCart) > 0) {
//            response.setResponseType(Responses.ACCEPTED);
//        }else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

    public Response showingOrdersToClient(String id){
        response = new Response();
        try {
            response.setServerData(new Gson().toJson(shopCartDAO.fetchOrdersToClient(Integer.parseInt(id))));
        }catch(NullPointerException e){
            response.setResponseType(Responses.BAD_REQUEST);
            e.printStackTrace();
            return response;
        }
        response.setResponseType(Responses.ACCEPTED);
        return response;
    }

    public Response payForOrder(ShopCart shopCart){
        response = new Response();
        if(shopCartDAO.payForOrder(shopCart) > 0) {
            response.setResponseType(Responses.ACCEPTED);

        }else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    public Response removeFromCart(ShopCart shopCart){
        response = new Response();
        if(shopCartDAO.removeProductFromCart(shopCart) > 0) {
            response.setResponseType(Responses.ACCEPTED);

        }else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

//    public Response showAllOrders(){
//        response = new Response();
//        try {
//            response.setServerData(new Gson().toJson(shopCartDAO.fetchAll()));
//        }catch(NullPointerException e){
//            response.setResponseType(Responses.BAD_REQUEST);
//            e.printStackTrace();
//            return response;
//        }
//        response.setResponseType(Responses.ACCEPTED);
//        return response;
//    }

    public Response setNewOrderStatus(ShopCart shopCart){
        response = new Response();
        if(shopCartDAO.setNewOrderStatus(shopCart) > 0) {
            response.setResponseType(Responses.ACCEPTED);

        }else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response addingEntity(ShopCart entity) {
        response = new Response();
        if(shopCartDAO.insertOrder(entity) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response showAll() {
        response = new Response();
        try {
            response.setServerData(new Gson().toJson(shopCartDAO.fetchAll()));
        }catch(NullPointerException e){
            response.setResponseType(Responses.BAD_REQUEST);
            e.printStackTrace();
            return response;
        }
        response.setResponseType(Responses.ACCEPTED);
        return response;
    }

    @Override
    public Response findEntity(String data) {
        return null;
    }

    @Override
    public Response deleteEntity(String data) {
        return null;
    }
}
