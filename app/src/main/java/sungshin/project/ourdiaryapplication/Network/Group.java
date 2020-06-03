package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.Date;

public class Group {
    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("king_user_seq")
    private Integer kingUserSeq;

    @SerializedName("name")
    private String name;

    @SerializedName("nick")
    private String nick;

    @SerializedName("seq")
    private Integer seq;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("user_count")
    private Integer userCount;

    public Integer getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
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

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
