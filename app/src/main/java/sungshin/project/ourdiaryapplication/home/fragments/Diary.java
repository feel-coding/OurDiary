package sungshin.project.ourdiaryapplication.home.fragments;


public class Diary {
    String writer;
    String title;
    String date;
    String content;
    String withWhom;
    String location;
    public Diary(String writer, String title, String date, String content) {
        this.writer = writer;
        this.title = title;
        this.date = date;
        this.content = content;
        withWhom = "";
    }
    public Diary(String writer, String title, String date, String content, String withWhom) {
        this.writer = writer;
        this.title = title;
        this.date = date;
        this.content = content;
        this.withWhom = withWhom;
    }
}
