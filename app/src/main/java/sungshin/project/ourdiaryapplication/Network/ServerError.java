package sungshin.project.ourdiaryapplication.Network;

public class ServerError {
    private String message;
    private String error;

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(String error) {
        this.error = error;
    }
}
