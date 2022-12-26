package services;

import DAO.CardDAO;
import Entitites.Card;
import Entitites.Product;
import Responses.Response;
import Responses.Responses;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CardService implements EntityService<Card>{
    private Response response;
    private CardDAO cardDAO = new CardDAO();

//    public Response findCard(Card card){
//        response = new Response();
//        Card card1 = cardDAO.fetchCard(card);
//        if (card1 != null){
//            response.setResponseType(Responses.BAD_REQUEST);
//            response.setServerData(new Gson().toJson(card1));
//        } else{
//            response.setResponseType(Responses.BAD_REQUEST);
//        }
//        return response;
//    }

    public Response findExistCard(Card card){
        response = new Response();
        Card card1 = cardDAO.fetchExistCard(card);
        if (card1 != null){
            response.setResponseType(Responses.ACCEPTED);
            response.setServerData(new Gson().toJson(card1));
        } else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

//    public Response addingCard(Card card){
//        response = new Response();
//        Card card1 = cardDAO.insertCard(card);
//        response.setResponseType(Responses.ACCEPTED);
//        response.setServerData(new Gson().toJson(card1));
//        return response;
//    }

    public Response addingBalance(Card card){
        response = new Response();
        response.setServerData(String.valueOf(cardDAO.addBalance(card)));
        response.setResponseType(Responses.ACCEPTED);
        return response;
    }

    @Override
    public Response addingEntity(Card entity) {
        response = new Response();
        Card card1 = cardDAO.insertCard(entity);
        response.setResponseType(Responses.ACCEPTED);
        response.setServerData(new Gson().toJson(card1));
        return response;
    }

    @Override
    public Response showAll() {
        return null;
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
