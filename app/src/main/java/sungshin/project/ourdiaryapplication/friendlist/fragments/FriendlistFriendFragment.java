package sungshin.project.ourdiaryapplication.friendlist.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.FrdlistActivity;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FriendListAdapter;
import sungshin.project.ourdiaryapplication.friendlist.data.FriendItem;


public class FriendlistFriendFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter friendListAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<FriendItem> friendList = new ArrayList<FriendItem>();
    ConstraintLayout friend_detail_container;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friendlist_friend, container, false);

        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");
        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");
        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");

        initFriendList(v, getActivity());

        //친구 신청 목록 클릭시 이벤트
        friend_detail_container = v.findViewById(R.id.friend_detail_container);
        friend_detail_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FrdlistActivity.class);
                startActivity(i);

            }
        });
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
