package sungshin.project.ourdiaryapplication.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import sungshin.project.ourdiaryapplication.home.HomeFragment;
import sungshin.project.ourdiaryapplication.home.fragments.HomeAllFragment;
import sungshin.project.ourdiaryapplication.home.fragments.HomeFriendFragment;
import sungshin.project.ourdiaryapplication.home.fragments.HomeGroupFragment;
import sungshin.project.ourdiaryapplication.home.fragments.HomeMyFragment;


public class HomeViewPagerAdapter extends FragmentStateAdapter {

    Fragment homeAllFragment, homeMyFragment, homeFriendFragment, homeGroupFragment;
    private int mPageCount;

    public HomeViewPagerAdapter(Fragment fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position){
                case 0:
                    homeAllFragment = new HomeAllFragment();
                    return homeAllFragment;
                case 1:
                    homeMyFragment = new HomeMyFragment();
                    return homeMyFragment;
                case 2:
                    homeFriendFragment = new HomeFriendFragment();
                    return homeFriendFragment;
                case 3:
                    homeGroupFragment = new HomeGroupFragment();
                    return homeGroupFragment;
                default:
                    return null;
        }

    }

    @Override
    public int getItemCount() {
        return mPageCount;
    }
}
