package services;

import DAO.SellerDAO;
import Entitites.Product;
import Entitites.Seller;
import Responses.Response;
import Responses.Responses;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SellerService implements EntityService<Seller>{
    private Response response;
    private SellerDAO sellerDAO = new SellerDAO();

    public Response editingSeller(Seller seller){
        response = new Response();
        System.out.println(new Gson().toJson(seller));
        if(sellerDAO.update(seller) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    public Response calculateSellerSalary(Seller seller){
        response = new Response();
        Float salary = seller.getHours()* seller.getRatePerHour() + seller.getAmountSells()*seller.getRatePerSell();
        response.setResponseType(Responses.ACCEPTED);
        response.setServerData(String.valueOf(salary));
        return response;
    }

    public Response set_amount_seller(String id){
        response = new Response();
        if(sellerDAO.editAmount(Integer.parseInt(id)) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response addingEntity(Seller entity) {
        response = new Response();
        if(sellerDAO.insertSeller(entity) > 0) {
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
            response.setServerData(new Gson().toJson(sellerDAO.fetchAll()));
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
        response = new Response();
        Seller seller = sellerDAO.fetchById(Integer.parseInt(data));
        if (seller != null){
            response.setResponseType(Responses.ACCEPTED);
            System.out.println(new Gson().toJson(seller));
            response.setServerData(new Gson().toJson(seller));
        } else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response deleteEntity(String data) {
        response = new Response();
        if(sellerDAO.delete(Integer.parseInt(data)) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }
}
