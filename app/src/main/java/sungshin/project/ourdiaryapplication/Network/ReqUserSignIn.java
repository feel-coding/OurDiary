package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

public class ReqUserSignIn {
    @SerializedName("id")
    private String id;

    @SerializedName("pw")
    private String pw;

    @SerializedName("type")
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
