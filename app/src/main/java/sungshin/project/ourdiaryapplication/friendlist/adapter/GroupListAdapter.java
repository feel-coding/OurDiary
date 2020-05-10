package sungshin.project.ourdiaryapplication.friendlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.data.GroupItem;
import sungshin.project.ourdiaryapplication.friendlist.viewholder.GroupListViewHolder;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListViewHolder> {

    ArrayList<GroupItem> gData = null;

    public GroupListAdapter(ArrayList<GroupItem> list) {
        gData = list;
    }

    @NonNull
    @Override
    public GroupListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_group_list_item, parent, false);

        GroupListViewHolder viewHolder = new GroupListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupListViewHolder viewholder, int position) {
        viewholder.groupImg.setImageDrawable(gData.get(position).getgProfileImg());
        viewholder.groupNickname.setText(gData.get(position).getgNickname());
        viewholder.groupRealname.setText(gData.get(position).getgRealname());
        viewholder.groupCount.setText(Integer.toString(gData.get(position).getgCount()));
    }

    @Override
    public int getItemCount() {
        return (gData != null ? gData.size() : 0);
    }
}
