package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.Date;

public class GroupMember {
    private BigInteger seq;
    private BigInteger groupSeq;
    private Integer userSeq;
    private Date createdAt;
    private Date updatedAt;

    public BigInteger getSeq() {
        return seq;
    }

    public BigInteger getGroupSeq() {
        return groupSeq;
    }

    public Integer getUserSeq() {
        return userSeq;
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

    public void setGroupSeq(BigInteger groupSeq) {
        this.groupSeq = groupSeq;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
