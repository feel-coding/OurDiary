package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    @SerializedName("seq")
    private Integer seq;
    @SerializedName("name")
    private String name;
    @SerializedName("nick")
    private String nick;
    @SerializedName("created_at")
    private Date createAt;

    public Integer getSeq() {
        return seq;
    }


    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public Date getCreateAt() {
        return createAt;
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

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
