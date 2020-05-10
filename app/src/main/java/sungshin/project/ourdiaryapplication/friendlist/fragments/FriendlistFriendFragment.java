package sungshin.project.ourdiaryapplication.friendlist.fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FriendListAdapter;
import sungshin.project.ourdiaryapplication.friendlist.data.FriendItem;


public class FriendlistFriendFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter friendListAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<FriendItem> friendList = new ArrayList<FriendItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friendlist_friend, container, false);

        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");
        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");
        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");

        initFriendList(v, getActivity());
        return v;
    }

    private void initFriendList(View v, Context context){
        recyclerView = v.findViewById(R.id.friendlist_rv);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        friendListAdapter = new FriendListAdapter(friendList);
        recyclerView.setAdapter(friendListAdapter);

        friendListAdapter.notifyDataSetChanged();

    }

    private void addItem(Drawable profileImg, String nickname, String realname){
        FriendItem item = new FriendItem();

        item.setfProfileImg(profileImg);
        item.setfNickname(nickname);
        item.setfRealname(realname);

        friendList.add(item);
    }

}
