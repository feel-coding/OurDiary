package sungshin.project.ourdiaryapplication.main.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import sungshin.project.ourdiaryapplication.calendar.CalendarFragment;
import sungshin.project.ourdiaryapplication.friendlist.FriendListFragment;
import sungshin.project.ourdiaryapplication.home.HomeFragment;
import sungshin.project.ourdiaryapplication.mypage.MyPageFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    Fragment homeFrag, calendarFrag, friendListFrag, myPageFrag;
    private int mPageCount;

    public MainViewPagerAdapter(FragmentActivity fa, int pageCount) {
        super(fa);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                homeFrag = new HomeFragment();
                return homeFrag;
            case 1:
                calendarFrag = new CalendarFragment();
                return calendarFrag;
            case 2:
                friendListFrag = new FriendListFragment();
                return friendListFrag;
            case 3:
                myPageFrag = new MyPageFragment();
                return myPageFrag;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return mPageCount;
    }
}
