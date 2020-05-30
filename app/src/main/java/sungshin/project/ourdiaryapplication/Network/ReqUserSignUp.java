package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

public class ReqUserSignUp {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

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
