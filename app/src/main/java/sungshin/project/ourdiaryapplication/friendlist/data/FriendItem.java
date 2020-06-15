package sungshin.project.ourdiaryapplication.friendlist.data;

import android.graphics.drawable.Drawable;

public class FriendItem {
    private Drawable fProfileImg;
    private String fNickname;
    private String fRealname;
    private Integer fSeq;

    public Drawable getfProfileImg() {
        return fProfileImg;
    }

    public void setfProfileImg(Drawable fProfileImg) {
        this.fProfileImg = fProfileImg;
    }

    public String getfNickname() {
        return fNickname;
    }

    public void setfNickname(String fNickname) {
        this.fNickname = fNickname;
    }

    public String getfRealname() {
        return fRealname;
    }

    public void setfRealname(String fRealname) {
        this.fRealname = fRealname;
    }

    public Integer getfSeq() {
        return fSeq;
    }

    public void setfSeq(Integer fSeq) {
        this.fSeq = fSeq;
    }
}
