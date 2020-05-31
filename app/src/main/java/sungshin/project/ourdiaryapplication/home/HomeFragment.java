package sungshin.project.ourdiaryapplication.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.home.adapter.HomeViewPagerAdapter;
import sungshin.project.ourdiaryapplication.newpost.NewPostActivity;


public class HomeFragment extends Fragment {

    private HomeViewPagerAdapter homePagerAdapter;
    private ViewPager2 homeViewPager;
    private TabLayout homeTabLayout;
    private String[] homeTabTitles= {"All", "My", "Friend", "Group"};
    private ImageView newpostBtn;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = view.getContext();

        return view;
    }

    @Override
    public void onOptionsMenuClosed(@NonNull Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        homePagerAdapter = new HomeViewPagerAdapter(this, 4);
        homeViewPager = view.findViewById(R.id.home_viewpager);
        homeViewPager.setAdapter(homePagerAdapter);

        homeTabLayout = view.findViewById(R.id.home_tablayout);
        new TabLayoutMediator(homeTabLayout, homeViewPager,
                (tab, position) -> {tab.setText(homeTabTitles[position]);
        homeViewPager.setCurrentItem(tab.getPosition(), true);}).attach();

        newpostBtn = view.findViewById(R.id.btn_home_newpost);

        newpostBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewPostActivity.class);
                startActivity(intent);
            }
        });

    }
}
