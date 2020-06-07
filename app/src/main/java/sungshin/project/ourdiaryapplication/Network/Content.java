package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.annotations.SerializedName;

public class Content {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
