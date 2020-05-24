package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.Date;

public class FriendRequest {
    private BigInteger seq;
    private Integer userSeq;
    private Integer friendSeq;
    private Integer state;
    private Date createdAt;
    private Date updatedAt;

    public BigInteger getSeq() {
        return seq;
    }

    public Integer getUserSeq() {
        return userSeq;
    }

    public Integer getFriendSeq() {
        return friendSeq;
    }

    public Integer getState() {
        return state;
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

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
