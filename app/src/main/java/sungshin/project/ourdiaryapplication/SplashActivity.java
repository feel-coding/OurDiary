package sungshin.project.ourdiaryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import sungshin.project.ourdiaryapplication.Login.LoginActivity;
import sungshin.project.ourdiaryapplication.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    final static int SPLASH_TIME_OUT = 500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //로그인 한적이 있는 사람인지 검사를 하는 부분(다음화면 결정)


                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);//이렇게 해야 로그인으로 감
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
