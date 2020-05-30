package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

public class ReqUserUpdateMe {
    @SerializedName("name")
    private String name;

    @SerializedName("nick")
    private String nick;

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
