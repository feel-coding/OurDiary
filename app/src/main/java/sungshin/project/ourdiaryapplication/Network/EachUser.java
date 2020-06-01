package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

public class EachUser {
    @SerializedName("seq")
    private Integer seq;

    @SerializedName("name")
    private String name;

    @SerializedName("nick")
    private String nick;

    @SerializedName("is_friend")
    private Boolean isFriend;

    public Integer getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }
}
