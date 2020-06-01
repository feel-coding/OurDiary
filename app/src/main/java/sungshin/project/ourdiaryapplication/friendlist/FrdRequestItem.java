package sungshin.project.ourdiaryapplication.friendlist;

        import android.widget.Button;
        import android.widget.ImageButton;

public class FrdRequestItem {
    private String friend;

    public FrdRequestItem(String friend) {
        this.friend = friend;
    }

    public String getFriend() {
        return this.friend;
    }
}
