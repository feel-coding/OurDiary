package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.Date;

public class DiaryFriendTag {
    private BigInteger seq;
    private BigInteger diarySeq;
    private Integer friendSeq;
    private Date createdAt;
    private Date updatedAt;

    public BigInteger getSeq() {
        return seq;
    }

    public BigInteger getDiarySeq() {
        return diarySeq;
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

    public void setDiarySeq(BigInteger diarySeq) {
        this.diarySeq = diarySeq;
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
