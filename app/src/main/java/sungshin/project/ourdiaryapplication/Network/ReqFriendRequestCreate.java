package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

public class ReqFriendRequestCreate {
    @SerializedName("user_seq")
    Integer userSeq;

    public Integer getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
    }
}
