package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;

public class FriendLink {
    private BigInteger seq;
    private String friendLinkcol;

    public BigInteger getSeq() {
        return seq;
    }

    public String getFriendLinkcol() {
        return friendLinkcol;
    }

    public void setSeq(BigInteger seq) {
        this.seq = seq;
    }

    public void setFriendLinkcol(String friendLinkcol) {
        this.friendLinkcol = friendLinkcol;
    }
}
