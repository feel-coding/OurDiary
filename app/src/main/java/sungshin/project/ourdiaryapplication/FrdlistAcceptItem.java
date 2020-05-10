package sungshin.project.ourdiaryapplication;

public class FrdlistAcceptItem {

    private String nickname;
    private String name;

    public FrdlistAcceptItem(String nickname, String name)
    {
        this.nickname = nickname;
        this.name = name;
    }

    public String getNickname() {
        return this.nickname;
    }
    public String getName() {
        return this.name;
    }
}