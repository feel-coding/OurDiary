package sungshin.project.ourdiaryapplication.friendlist.data;

import android.graphics.drawable.Drawable;

public class GroupItem {
    private Drawable gProfileImg;
    private String gNickname;
    private String gRealname;
    private int gCount;

    public Drawable getgProfileImg() {
        return gProfileImg;
    }

    public void setgProfileImg(Drawable gProfileImg) {
        this.gProfileImg = gProfileImg;
    }

    public String getgNickname() {
        return gNickname;
    }

    public void setgNickname(String gNickname) {
        this.gNickname = gNickname;
    }

    public String getgRealname() {
        return gRealname;
    }

    public void setgRealname(String gRealname) {
        this.gRealname = gRealname;
    }

    public int getgCount() {
        return gCount;
    }

    public void setgCount(int gCount) {
        this.gCount = gCount;
    }
}
