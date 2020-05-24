package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.Date;

public class DiaryPlace {
    private BigInteger seq;
    private BigInteger diarySeq;
    private String path;
    private Integer turn;
    private Date createdAt;
    private Date updatedAt;

    public BigInteger getSeq() {
        return seq;
    }

    public BigInteger getDiarySeq() {
        return diarySeq;
    }

    public String getPath() {
        return path;
    }

    public Integer getTurn() {
        return turn;
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

    public void setPath(String path) {
        this.path = path;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
