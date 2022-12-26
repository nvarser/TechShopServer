package services;

import DAO.SellerDAO;
import DAO.ShopClientDAO;
import Entitites.Seller;
import Entitites.ShopClient;
import Responses.Response;
import Responses.Responses;
import com.google.gson.Gson;

public class ShopClientService implements EntityService<ShopClient>{
    private Response response;
    private ShopClientDAO clientDAO = new ShopClientDAO();
//    public Response addingClient(ShopClient client){
//        response = new Response();
//        if(clientDAO.insertClient(client) > 0) {
//            response.setResponseType(Responses.ACCEPTED);
//        }
//        else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

//    public Response deleteClientByUserId(String id){
//        response = new Response();
//        if(clientDAO.delete(Integer.parseInt(id)) > 0) {
//            response.setResponseType(Responses.ACCEPTED);
//        }
//        else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

//    public Response showAllClient(){
//        response = new Response();
//        try {
//            response.setServerData(new Gson().toJson(clientDAO.fetchAll()));
//        }catch(NullPointerException e){
//            response.setResponseType(Responses.BAD_REQUEST);
//            e.printStackTrace();
//            return response;
//        }
//        response.setResponseType(Responses.ACCEPTED);
//        return response;
//    }

    @Override
    public Response addingEntity(ShopClient entity) {
        response = new Response();
        if(clientDAO.insertClient(entity) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response showAll() {
        response = new Response();
        try {
            response.setServerData(new Gson().toJson(clientDAO.fetchAll()));
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
        response = new Response();
        if(clientDAO.delete(Integer.parseInt(data)) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }
}
