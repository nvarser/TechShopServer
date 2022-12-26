package Responses;

public class Request {
    private RequestType requestType;
    private String clientData;

    public RequestType getRequestType() {
        return requestType;
    }

    public String getClientData() {
        return clientData;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }
}
