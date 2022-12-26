package services;

import Responses.Response;

import java.sql.SQLException;

public interface EntityService<T> {
    public Response addingEntity(T entity);
    public Response showAll();
    public Response findEntity(String data) throws SQLException;
    public Response deleteEntity(String data) throws SQLException;
}
