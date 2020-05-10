package sungshin.project.ourdiaryapplication.friendlist.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sungshin.project.ourdiaryapplication.R;

public class FriendListViewHolder extends RecyclerView.ViewHolder {

    public ImageView profileImg;
    public TextView friendNickname;
    public TextView friendRealname;

    public FriendListViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImg = itemView.findViewById(R.id.img_friend_profile);
        friendNickname = itemView.findViewById(R.id.tv_friend_nickname);
        friendRealname = itemView.findViewById(R.id.tv_friend_realname);
    }
}
