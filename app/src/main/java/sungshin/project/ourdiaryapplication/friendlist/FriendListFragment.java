package sungshin.project.ourdiaryapplication.friendlist;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FriendListAdapter;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FriendlistViewPagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FriendListFragment extends Fragment {

    FriendlistViewPagerAdapter friendlistPagerAdapter;
    ViewPager2 friendlistViewPager;
    TabLayout friendlistTabLayout;

    String[] friendTabTitles = {"Friend", "Group"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_friend_list, container, false);
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

}
