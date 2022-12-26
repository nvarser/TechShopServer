package Responses;

public class Response {
    private Responses responses;
    private String serverData;

    public Responses getResponseType() {
        return responses;
    }

    public String getServerData() {
        return serverData;
    }

    public void setResponseType(Responses responses) {
        this.responses = responses;
    }

    public void setServerData(String serverData) {
        this.serverData = serverData;
    }
}
