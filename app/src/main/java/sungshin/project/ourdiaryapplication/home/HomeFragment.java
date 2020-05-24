package sungshin.project.ourdiaryapplication.home;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import sungshin.project.ourdiaryapplication.DocwriteActivity;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.home.adapter.HomeViewPagerAdapter;


public class HomeFragment extends Fragment {

    private HomeViewPagerAdapter homePagerAdapter;
    private ViewPager2 homeViewPager;
    private TabLayout homeTabLayout;
    private String[] homeTabTitles= {"All", "My", "Friend", "Group"};
    Context mContext;
    Activity activity;
    ImageView writeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        writeButton = v.findViewById(R.id.btn_home_newpost);
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
        homePagerAdapter = new HomeViewPagerAdapter(this, 4);
        homeViewPager = view.findViewById(R.id.home_viewpager);
        homeViewPager.setAdapter(homePagerAdapter);

        homeTabLayout = view.findViewById(R.id.home_tablayout);
        new TabLayoutMediator(homeTabLayout, homeViewPager,
                (tab, position) -> {tab.setText(homeTabTitles[position]);
        homeViewPager.setCurrentItem(tab.getPosition(), true);}).attach();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }
}
