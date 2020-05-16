package sungshin.project.ourdiaryapplication.Network;

public class ReqSignInUser {
    private String id;
    private String pw;
    private String type;
    private String name;

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }
}
