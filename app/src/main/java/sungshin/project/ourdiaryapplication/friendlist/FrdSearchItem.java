package sungshin.project.ourdiaryapplication.friendlist;

public class FrdSearchItem {
    private String nickname;
    private String name;
    private Integer seq;

    public FrdSearchItem(String nickname, String name, Integer seq) {
        this.nickname = nickname;
        this.name = name;
        this.seq = seq;
    }

    public String getNickname() {
        return this.nickname;
    }
    public String getName() {
        return this.name;
    }

    public Integer getSeq() {
        return seq;
    }
}
