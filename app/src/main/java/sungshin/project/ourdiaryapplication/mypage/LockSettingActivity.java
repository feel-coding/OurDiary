package sungshin.project.ourdiaryapplication.mypage;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import sungshin.project.ourdiaryapplication.R;

public class LockSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setting);

        //뒤로가기 버튼 만들기
        Toolbar toolbar = findViewById(R.id.lock_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
    }

    public void passwordCheck(View v) {
        RadioButton rb;
        TextView tv = findViewById(R.id.changePwTv);
        switch (v.getId()) {
            case R.id.noPwTv:
                rb = findViewById(R.id.noPassword);
                rb.setChecked(true);
                tv = findViewById(R.id.changePwTv);
                tv.setTextColor(getResources().getColor(R.color.gray));
                tv.setClickable(false);
                break;
            case R.id.yesPwTv:
                rb = findViewById(R.id.password);
                rb.setChecked(true);
                tv.setTextColor(getResources().getColor(R.color.black));
                tv.setClickable(true);
        }
    }
}
