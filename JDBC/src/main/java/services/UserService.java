package services;

import DAO.UserDAO;
import Entitites.User;
import Responses.Response;
import Responses.Responses;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class UserService implements EntityService<User>{

    private final UserDAO userDAO = new UserDAO();
    private Response response;

    private User client;

    public Response authorization(User user){
        response = new Response();
        client = userDAO.fetchByLogin(user);
        if (client != null && Objects.equals(client.getPassword(), user.getPassword())){
            response.setResponseType(Responses.ACCEPTED);
            response.setServerData(new Gson().toJson(client));
        } else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    public Response updatingUser(User user) throws SQLException {
        response = new Response();
        System.out.println(new Gson().toJson(user));
        if(userDAO.update(user) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response addingEntity(User entity) {
        response = new Response();
        if(userDAO.insert(entity) > 0) {
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
            response.setServerData(new Gson().toJson(userDAO.fetchAll()));
        }catch(NullPointerException e){
            response.setResponseType(Responses.BAD_REQUEST);
            e.printStackTrace();
            return response;
        }
        response.setResponseType(Responses.ACCEPTED);
        return response;
    }

    @Override
    public Response findEntity(String data) throws SQLException {
        response = new Response();
        System.out.println("Логин пользователя:" + data);
        User user = userDAO.fetchByLogin(data);
        if (user != null){
            System.out.println(new Gson().toJson(user));
            response.setResponseType(Responses.ACCEPTED);
            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            response.setServerData(new Gson().toJson(users));
        } else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Response deleteEntity(String data) throws SQLException {
        response = new Response();
        if(userDAO.delete(Integer.parseInt(data)) > 0) {
            response.setResponseType(Responses.ACCEPTED);
        }
        else{
            response.setResponseType(Responses.BAD_REQUEST);
        }
        return response;
    }
}
