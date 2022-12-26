package services;

import DAO.ProductDAO;
import Entitites.Product;
import Entitites.User;
import Responses.Response;
import Responses.Responses;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ProductService implements EntityService<Product>{

    private Response response;
    private ProductDAO productDAO = new ProductDAO();

//    public Response addingProduct(Product product){
//        response = new Response();
//        if(productDAO.insertProduct(product) > 0) {
//            response.setResponseType(Responses.ACCEPTED);
//        }
//        else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

//    public Response showAllProducts(){
//        response = new Response();
//        try {
//            response.setServerData(new Gson().toJson(productDAO.fetchAll()));
//        }catch(NullPointerException e){
//            response.setResponseType(Responses.BAD_REQUEST);
//            e.printStackTrace();
//            return response;
//        }
//        response.setResponseType(Responses.ACCEPTED);
//        return response;
//    }

    public Response updatingProduct(Product product){
        response = new Response();
        System.out.println(new Gson().toJson(product));
        if(productDAO.update(product) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    public Response setFirmToProduct(Product product){
        response = new Response();
        System.out.println(new Gson().toJson(product));
        if(productDAO.insertProductFirm(product) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

//    public Response deleteProductById(String id){
//        response = new Response();
//        if(productDAO.delete(Integer.parseInt(id)) > 0) {
//            response.setResponseType(Responses.ACCEPTED);
//        }
//        else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

//    public Response searchProductById(String id){
//        response = new Response();
//        Product product = productDAO.fetchById(Integer.parseInt(id));
//        if (product != null){
//            response.setResponseType(Responses.ACCEPTED);
//            ArrayList<Product> products = new ArrayList<>();
//            products.add(product);
//            System.out.println(new Gson().toJson(product));
//            response.setServerData(new Gson().toJson(products));
//        } else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

    public Response searchProductByName(String name){
        response = new Response();
        Product product = productDAO.fetchByName(name);
        if (product != null){
            response.setResponseType(Responses.ACCEPTED);
            ArrayList<Product> products = new ArrayList<>();
            products.add(product);
            System.out.println(new Gson().toJson(product));
            response.setServerData(new Gson().toJson(products));
        } else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response addingEntity(Product entity) {
        response = new Response();
        if(productDAO.insertProduct(entity) > 0) {
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
            response.setServerData(new Gson().toJson(productDAO.fetchAll()));
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
        Product product = productDAO.fetchById(Integer.parseInt(data));
        if (product != null){
            response.setResponseType(Responses.ACCEPTED);
            ArrayList<Product> products = new ArrayList<>();
            products.add(product);
            System.out.println(new Gson().toJson(product));
            response.setServerData(new Gson().toJson(products));
        } else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response deleteEntity(String data) {
        response = new Response();
        if(productDAO.delete(Integer.parseInt(data)) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }
}
