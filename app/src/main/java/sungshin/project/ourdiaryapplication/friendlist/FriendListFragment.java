package sungshin.project.ourdiaryapplication.friendlist;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import sungshin.project.ourdiaryapplication.DocwriteActivity;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FriendListAdapter;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FriendlistViewPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FriendListFragment extends Fragment {

    FriendlistViewPagerAdapter friendlistPagerAdapter;
    ViewPager2 friendlistViewPager;
    TabLayout friendlistTabLayout;

    String[] friendTabTitles = {"Friend", "Group"};
    Context mContext;
    Activity activity;
    ImageView writeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friend_list, container, false);
        writeButton = v.findViewById(R.id.btn_friendlist_newpost);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DocwriteActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        friendlistPagerAdapter = new FriendlistViewPagerAdapter(this, 2);
        friendlistViewPager = view.findViewById(R.id.friend_viewpager);
        friendlistViewPager.setAdapter(friendlistPagerAdapter);

        friendlistTabLayout = view.findViewById(R.id.friendlist_tablayout);
        new TabLayoutMediator(friendlistTabLayout, friendlistViewPager,
                (tab, position) -> { tab.setText(friendTabTitles[position]);
            friendlistViewPager.setCurrentItem(position, true);}).attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }
}
