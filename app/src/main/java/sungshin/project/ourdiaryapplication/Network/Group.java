package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.Date;

public class Group {
    private BigInteger seq;
    private String name;
    private String nick;
    private Integer userCount;
    private Date createdAt;
    private Date updatedAt;

    public BigInteger getSeq() {
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

    public void setSeq(BigInteger seq) {
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
