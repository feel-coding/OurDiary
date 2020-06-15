package sungshin.project.ourdiaryapplication.friendlist.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.EachUser;
import sungshin.project.ourdiaryapplication.Network.Friend;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.FrdRequestActivity;
import sungshin.project.ourdiaryapplication.friendlist.FrdlistActivity;
import sungshin.project.ourdiaryapplication.friendlist.FrdSearchActivity;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FriendListAdapter;
import sungshin.project.ourdiaryapplication.friendlist.data.FriendItem;


public class FriendlistFriendFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter friendListAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<FriendItem> friendList = new ArrayList<FriendItem>();
    ConstraintLayout friend_detail_container;
    SearchView friendSearchView;
    Context mContext;
    Activity activity;
    Button friend_req_btn;
    private ServerApi serverApi;
    Button friendRequestBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friendlist_friend, container, false);
        serverApi = RetrofitManager.getInstance().getServerApi(activity);
        friendSearchView = v.findViewById(R.id.friend_search);
        friendRequestBtn = v.findViewById(R.id.friend_req_btn);
        friendRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, FrdSearchActivity.class);
                startActivity(intent);
            }
        });
        friendSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                serverApi.getFriendList(s).enqueue(new Callback<List<Friend>>() {
                    @Override
                    public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                        friendList.clear();
                        for (Friend friend : response.body()) {
                            addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), friend.getNick(), friend.getName(), friend.getSeq());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Friend>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("onQueryTextChange", "에 들어옴");
                serverApi.getAllUsers(1, 15, s).enqueue(new Callback<List<EachUser>>() {
                    @Override
                    public void onResponse(Call<List<EachUser>> call, Response<List<EachUser>> response) {
                        if(response.isSuccessful()) {
                            Log.d("searchsuccess", "성공");
                            for (int i = 0; i < response.body().size(); i++) {
                                addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), response.body().get(i).getNick(), response.body().get(i).getName(), response.body().get(i).getSeq());
                            }
                        }
                        else {
                            Log.d("searcherror", "onResponse까지는 성공했지만 " + response.code() + " error");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EachUser>> call, Throwable t) {
                        Log.d("searcherror", t.getMessage());
                    }
                });
                return false;
            }
        });

        serverApi.getFriendList().enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.isSuccessful()) {
                    for (Friend friend : response.body()) {
                        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), friend.getNick(), friend.getName(), friend.getSeq());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });
//        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");
//        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");
//        addItem(ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.profile_image, null), "닉네임", "본명");

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

        //친구 신청하기 버튼 클릭시
        friend_req_btn = v.findViewById(R.id.friend_req_btn);
        friend_req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), FrdRequestActivity.class);
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

    private void addItem(Drawable profileImg, String nickname, String realname, Integer seq){
        FriendItem item = new FriendItem();

        item.setfProfileImg(profileImg);
        item.setfNickname(nickname);
        item.setfRealname(realname);
        item.setfSeq(seq);

        friendList.add(item);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }

}
