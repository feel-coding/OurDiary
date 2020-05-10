package sungshin.project.ourdiaryapplication.friendlist.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sungshin.project.ourdiaryapplication.R;

public class GroupListViewHolder extends RecyclerView.ViewHolder {

    public ImageView groupImg;
    public TextView groupNickname;
    public TextView groupRealname;
    public TextView groupCount;

    public GroupListViewHolder(@NonNull View itemView) {
        super(itemView);

        groupImg = itemView.findViewById(R.id.img_group_profile);
        groupNickname = itemView.findViewById(R.id.tv_group_nickname);
        groupRealname = itemView.findViewById(R.id.tv_group_realname);
        groupCount = itemView.findViewById(R.id.group_count_tv);
    }
}
