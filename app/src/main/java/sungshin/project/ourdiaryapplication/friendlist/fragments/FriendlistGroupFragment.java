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
import sungshin.project.ourdiaryapplication.friendlist.adapter.GroupListAdapter;
import sungshin.project.ourdiaryapplication.friendlist.data.GroupItem;

public class FriendlistGroupFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter groupListAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<GroupItem> groupList = new ArrayList<GroupItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friendlist_group, container, false);

        addGroupItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null),
                "그룹 닉네임", "본명", 2);
        addGroupItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null),
                "그룹 닉네임", "본명", 3);
        addGroupItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null),
                "그룹 닉네임", "본명", 4);
        addGroupItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null),
                "그룹 닉네임", "본명", 3);

        initGroupList(v, getActivity());
        return v;
    }

    private void initGroupList(View v, Context context){

        recyclerView = v.findViewById(R.id.grouplist_rv);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        groupListAdapter = new GroupListAdapter(groupList);
        recyclerView.setAdapter(groupListAdapter);

        groupListAdapter.notifyDataSetChanged();
    }

    private void addGroupItem(Drawable gProfileImg, String gNickname, String gRealname, int gCount){
        GroupItem item = new GroupItem();

        item.setgProfileImg(gProfileImg);
        item.setgNickname(gNickname);
        item.setgRealname(gRealname);
        item.setgCount(gCount);

        groupList.add(item);
    }

}
