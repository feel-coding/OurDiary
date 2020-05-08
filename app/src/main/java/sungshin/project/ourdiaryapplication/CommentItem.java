package sungshin.project.ourdiaryapplication;

public class CommentItem {
    private String nickname;
    private String name;
    public String content;
    private String date;

    public CommentItem(String nickname, String name, String content, String date)
    {
        this.nickname = nickname;
        this.name = name;
        this.content = content;
        this.date = date;
    }

    public String getNickname() {
        return this.nickname;
    }
    public String getName() {
        return this.name;
    }
    public String getContent() {
        return this.content;
    }
    public String getDate() {
        return this.date;
    }
}
