package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Friend {
    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("email")
    private String email;

    @SerializedName("is_friend")
    private Boolean isFriend;

    @SerializedName("name")
    private String name;

    @SerializedName("nick")
    private String nick;

    @SerializedName("seq")
    private Integer seq;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
