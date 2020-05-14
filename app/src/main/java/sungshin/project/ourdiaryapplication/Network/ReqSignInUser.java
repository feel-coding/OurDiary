package sungshin.project.ourdiaryapplication.Network;

public class ReqSignInUser {
    private String id;
    private String pw;
    private String type;

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setType(String type) {
        this.type = type;
    }
}
