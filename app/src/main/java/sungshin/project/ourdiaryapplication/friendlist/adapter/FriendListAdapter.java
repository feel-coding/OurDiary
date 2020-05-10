package sungshin.project.ourdiaryapplication.friendlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.data.FriendItem;
import sungshin.project.ourdiaryapplication.friendlist.viewholder.FriendListViewHolder;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListViewHolder> {

    ArrayList<FriendItem> fData = null;

    public FriendListAdapter(ArrayList<FriendItem> list) {
        fData = list;
    }

    @NonNull
    @Override
    public FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_friend_list_item, parent, false);

        FriendListViewHolder viewHolder = new FriendListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListViewHolder viewHolder, int position) {
        viewHolder.profileImg.setImageDrawable(fData.get(position).getfProfileImg());
        viewHolder.friendNickname.setText(fData.get(position).getfNickname());
        viewHolder.friendRealname.setText(fData.get(position).getfRealname());
    }

    @Override
    public int getItemCount() {
        return (null != fData ? fData.size() : 0);
    }
}
