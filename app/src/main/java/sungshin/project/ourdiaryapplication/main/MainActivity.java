package sungshin.project.ourdiaryapplication.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import sungshin.project.ourdiaryapplication.Login.NicknameSettingActivity;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.DeligationActivity;
import sungshin.project.ourdiaryapplication.main.adapter.MainViewPagerAdapter;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 mainViewPager;
    MainViewPagerAdapter mainViewPagerAdapter;
    final String SHARED_PREF_PASSWORD = "2000";

    private static final String TAG = "MyTag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
        String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
        Log.d("pwpwpw", p);


//        Intent i = new Intent(this, DeligationActivity.class);
//        startActivity(i);




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
