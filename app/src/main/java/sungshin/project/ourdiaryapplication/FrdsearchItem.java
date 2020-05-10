package sungshin.project.ourdiaryapplication;

public class FrdsearchItem {
    private String nick;
    private String name;

    public FrdsearchItem(String nick, String name) {
        this.nick = nick;
        this.name = name;
    }

    public String getNickname() {
        return this.nick;
    }
    public String getName() {
        return this.name;
    }
}
