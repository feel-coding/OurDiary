package sungshin.project.ourdiaryapplication.home.fragments;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Diary {
    String writer;
    String title;
    String date;
    String content;
    String withWhom;
    BigInteger seq;
    String location;
    Integer likeCount;
    ArrayList<String> photoList = new ArrayList<>();

    public Diary() {

    }

    public Diary(String writer, String title, String date, String content, BigInteger seq, Integer likeCount) {
        this.writer = writer;
        this.title = title;
        this.date = date;
        this.content = content;
        this.seq = seq;
        this.likeCount = likeCount;
        withWhom = "";
    }
    public Diary(String writer, String title, String date, String content, String withWhom, BigInteger seq, Integer likeCount) {
        this.writer = writer;
        this.title = title;
        this.date = date;
        this.content = content;
        this.seq = seq;
        this.likeCount = likeCount;
        this.withWhom = withWhom;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getWithWhom() {
        return withWhom;
    }

    public BigInteger getSeq() {
        return seq;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWithWhom(String withWhom) {
        this.withWhom = withWhom;
    }

    public void setSeq(BigInteger seq) {
        this.seq = seq;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhotoList(ArrayList<String> photoList) {
        this.photoList = photoList;
    }
}
