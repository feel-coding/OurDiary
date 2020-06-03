package sungshin.project.ourdiaryapplication.Network;


import java.math.BigInteger;
import java.util.Date;

public class FriendReq {
    Integer seq;
    String state;
    Date createdAt;
    User user;

    public Integer getSeq() {
        return seq;
    }

    public String getState() {
        return state;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }


    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
