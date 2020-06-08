package sungshin.project.ourdiaryapplication.home.fragments;


import java.math.BigInteger;

public class Diary {
    String writer;
    String title;
    String date;
    String content;
    String withWhom;
    BigInteger seq;
    String location;
    public Diary(String writer, String title, String date, String content, BigInteger seq) {
        this.writer = writer;
        this.title = title;
        this.date = date;
        this.content = content;
        this.seq = seq;
        withWhom = "";
    }
    public Diary(String writer, String title, String date, String content, String withWhom, BigInteger seq) {
        this.writer = writer;
        this.title = title;
        this.date = date;
        this.content = content;
        this.seq = seq;
        this.withWhom = withWhom;
    }
}
