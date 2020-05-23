package sungshin.project.ourdiaryapplication.Network;

import java.util.Date;

public class User {
    private Integer seq;
    private String email;
    private String name;
    private String nick;
    private Date createAt;

    public Integer getSeq() {
        return seq;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
