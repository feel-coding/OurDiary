package sungshin.project.ourdiaryapplication.friendlist.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import sungshin.project.ourdiaryapplication.friendlist.fragments.FriendlistFriendFragment;
import sungshin.project.ourdiaryapplication.friendlist.fragments.FriendlistGroupFragment;

public class FriendlistViewPagerAdapter extends FragmentStateAdapter {

    Fragment friendlistFriendFrag, friendlistGroupFrag;
    private int fPageCount;

    public FriendlistViewPagerAdapter(Fragment fm, int pageCount) {
        super(fm);
        this.fPageCount = pageCount;
    }


    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                friendlistFriendFrag = new FriendlistFriendFragment();
                return friendlistFriendFrag;
            case 1:
                friendlistGroupFrag = new FriendlistGroupFragment();
                return friendlistGroupFrag;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return fPageCount;
    }
}
