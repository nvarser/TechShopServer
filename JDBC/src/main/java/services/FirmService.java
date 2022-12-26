package services;

import DAO.FirmDAO;
import DAO.ProductDAO;
import Entitites.Firm;
import Entitites.Product;
import Responses.Response;
import Responses.Responses;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FirmService implements EntityService<Firm> {

    private Response response;
    private FirmDAO firmDAO = new FirmDAO();

//    public Response addingFirm(Firm firm){
//        response = new Response();
//        if(firmDAO.insertFirm(firm) > 0) {
//            response.setResponseType(Responses.ACCEPTED);
//        }
//        else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

    public Response editingFirm(Firm firm){
        response = new Response();
        System.out.println(new Gson().toJson(firm));
        if(firmDAO.update(firm) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

//    public Response deleteFirmById(String id){
//        response = new Response();
//        if(firmDAO.delete(Integer.parseInt(id)) > 0) {
//            response.setResponseType(Responses.ACCEPTED);
//        }
//        else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }
//
//    public Response searchFirmById(String id){
//        response = new Response();
//        Firm firm = firmDAO.fetchById(Integer.parseInt(id));
//        if (firm != null){
//            response.setResponseType(Responses.ACCEPTED);
//            ArrayList<Firm> firms = new ArrayList<>();
//            firms.add(firm);
//            System.out.println(new Gson().toJson(firm));
//            response.setServerData(new Gson().toJson(firms));
//        } else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }
//    public Response showAllFirms(){
//        response = new Response();
//        try {
//            response.setServerData(new Gson().toJson(firmDAO.fetchAll()));
//        }catch(NullPointerException e){
//            response.setResponseType(Responses.BAD_REQUEST);
//            e.printStackTrace();
//            return response;
//        }
//        response.setResponseType(Responses.ACCEPTED);
//        return response;
//    }

    @Override
    public Response addingEntity(Firm entity) {
        response = new Response();
        if(firmDAO.insertFirm(entity) > 0) {
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
            response.setServerData(new Gson().toJson(firmDAO.fetchAll()));
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
        Firm firm = firmDAO.fetchById(Integer.parseInt(data));
        if (firm != null){
            response.setResponseType(Responses.ACCEPTED);
            ArrayList<Firm> firms = new ArrayList<>();
            firms.add(firm);
            System.out.println(new Gson().toJson(firm));
            response.setServerData(new Gson().toJson(firms));
        } else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response deleteEntity(String data) {
        response = new Response();
        if(firmDAO.delete(Integer.parseInt(data)) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }
}
