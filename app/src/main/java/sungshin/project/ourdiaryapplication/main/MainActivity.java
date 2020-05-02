package sungshin.project.ourdiaryapplication.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import sungshin.project.ourdiaryapplication.PasswordSettingActivity;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.main.adapter.MainViewPagerAdapter;
import sungshin.project.ourdiaryapplication.mypage.LockActivity;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 mainViewPager;
    MainViewPagerAdapter mainViewPagerAdapter;

    private static final String TAG = "MyTag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPagerAdapter = new MainViewPagerAdapter(this, 4);
        mainViewPager = findViewById(R.id.main_viewpager);
        mainViewPager.setAdapter(mainViewPagerAdapter);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()) {
                    case R.id.navi_home:
                        mainViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navi_calendar:
                        mainViewPager.setCurrentItem(1);
                        return true;
                    case R.id.navi_friend:
                        mainViewPager.setCurrentItem(2);
                        return true;
                    case R.id.navi_mypage:
                        mainViewPager.setCurrentItem(3);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }
}
