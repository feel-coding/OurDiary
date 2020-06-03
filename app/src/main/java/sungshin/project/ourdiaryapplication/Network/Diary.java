package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class Diary {
    @SerializedName("content")
    private Content content;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("friend_list")
    private List<Friend> friendList;

    @SerializedName("group_list")
    private List<Group> groupList;

    @SerializedName("is_forbidden")
    private Boolean isForbidden;

    @SerializedName("is_like")
    private Boolean isLike;

    @SerializedName("like_count")
    private Integer likeCount;

    @SerializedName("picture_url_list")
    private List<String> pictureUrlList;

    @SerializedName("place_list")
    private List<String> placeList;

    @SerializedName("seq")
    private BigInteger seq;

    @SerializedName("share_state")
    private Integer shareState;

    @SerializedName("title")
    private String title;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("user")
    private User user;

    @SerializedName("user_seq")
    private Integer userSeq;

    @SerializedName("wanted_dt")
    private Date wantedDate;
    //Json content


    public Content getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public Boolean getForbidden() {
        return isForbidden;
    }

    public Boolean getLike() {
        return isLike;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public List<String> getPictureUrlList() {
        return pictureUrlList;
    }

    public List<String> getPlaceList() {
        return placeList;
    }

    public BigInteger getSeq() {
        return seq;
    }

    public Integer getShareState() {
        return shareState;
    }

    public String getTitle() {
        return title;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public Integer getUserSeq() {
        return userSeq;
    }

    public Date getWantedDate() {
        return wantedDate;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public void setForbidden(Boolean forbidden) {
        isForbidden = forbidden;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void setPictureUrlList(List<String> pictureUrlList) {
        this.pictureUrlList = pictureUrlList;
    }

    public void setPlaceList(List<String> placeList) {
        this.placeList = placeList;
    }

    public void setSeq(BigInteger seq) {
        this.seq = seq;
    }

    public void setShareState(Integer shareState) {
        this.shareState = shareState;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserSeq(Integer userSeq) {
        this.userSeq = userSeq;
    }

    public void setWantedDate(Date wantedDate) {
        this.wantedDate = wantedDate;
    }
}
