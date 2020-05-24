package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.Date;

public class DiaryGroupTag {
    private BigInteger seq;
    private BigInteger diarySeq;
    private BigInteger groupSeq;
    private Date createdAt;
    private Date updatedAt;

    public BigInteger getSeq() {
        return seq;
    }

    public BigInteger getDiarySeq() {
        return diarySeq;
    }

    public BigInteger getGroupSeq() {
        return groupSeq;
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

    public void setGroupSeq(BigInteger groupSeq) {
        this.groupSeq = groupSeq;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
