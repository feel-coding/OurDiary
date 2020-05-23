package sungshin.project.ourdiaryapplication.Network;


import java.math.BigInteger;
import java.util.Date;

public class Friend {
    BigInteger seq;
    Integer userSeq;
    Integer friendSeq;
    Date createdAt;
    Date updatedAt;

    public BigInteger getSeq() {
        return seq;
    }

    public Integer getUserSeq() {
        return userSeq;
    }

    public Integer getFriendSeq() {
        return friendSeq;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    public void setSeq(BigInteger seq) {
        this.seq = seq;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
    }

    public void setFriendSeq(Integer friendSeq) {
        this.friendSeq = friendSeq;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
