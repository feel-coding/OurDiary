package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.Date;

public class Diary {
    private BigInteger seq;
    private Integer userSeq;
    private String title;
    private Integer shareState;
    private Date wroteOn;
    private Date createdAt;
    private Date updatedAt;
    //Json content

    public BigInteger getSeq() {
        return seq;
    }

    public Integer getUserSeq() {
        return userSeq;
    }

    public String getTitle() {
        return title;
    }

    public Integer getShareState() {
        return shareState;
    }

    public Date getWroteOn() {
        return wroteOn;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShareState(Integer shareState) {
        this.shareState = shareState;
    }

    public void setWroteOn(Date wroteOn) {
        this.wroteOn = wroteOn;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
